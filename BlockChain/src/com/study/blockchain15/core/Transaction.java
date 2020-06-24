package com.study.blockchain15.core;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

import com.study.blockchain15.Main;
import com.study.blockchain15.util.BlockUtil;

public class Transaction {
	
	public float minimumTransaction = 0.1f;

	public String transactionId;
	public PublicKey sender;
	public PublicKey reciepient;
	public float value;
	public byte[] signature;	// this is to prevent anybody else from spending funds in our wallet.
	
	public ArrayList<TransactionInput> inputs = new ArrayList<>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<>();
	
	public static int sequence = 0;	// a rough count of how many transactions have been generated. 
	
	// 파라미터들은 생성하자마자 generateSignature 하기 위해 필요한 정보들이었다.
	public Transaction(PublicKey from, PublicKey to, float value) {
		this.sender = from;
		this.reciepient = to;
		this.value = value;
	}
	
	public boolean processTransaction() {

		// 1.verifySignature()
		if (verifySignature() == false) {
			return false;
		}
		
		// 2.gather transaction inputs (사용되지 않은게 맞냐? 확인)
		for (TransactionInput i :inputs) {
			i.UTXO = Main.UTXOs.get(i.transactionOutputId);
			// 구해서 뭐 하고 있는 거지?
		}
		
		// 3.최소단위 0.1f 를 넘는지 체크
		if (getInputValue() < minimumTransaction) {
			return false;
		}
		
		// 4.generate transaction outputs: 40, 60
		float leftOver = getInputValue() - value;
		//System.out.println(getInputValue() + ":" + value + ":" + leftOver);
		transactionId = calculateHash();
		outputs.add(new TransactionOutput(this.reciepient, value, transactionId));	// 40코인 송신
		outputs.add(new TransactionOutput(this.sender, leftOver, transactionId));	// 나머지 60
		
		// 5.add outputs to Unspent list
		for (TransactionOutput o : outputs) {
			Main.UTXOs.put(o.transactionId, o);
		}
		
		// 6.remove transaction inputs from UTXO lists as spent
		for (TransactionInput i : inputs) {
			if (i.UTXO == null) continue;
			Main.UTXOs.remove(i.UTXO.transactionId);
		}

		return true;
	}
	
	public String calculateHash() {
		sequence++;
		return BlockUtil.applySha256(BlockUtil.getStringFromKey(sender) + 
				BlockUtil.getStringFromKey(reciepient) +
				Float.toString(value) +
				sequence);
	}
	
	public float getInputValue() {
		float total = 0;
		for (TransactionInput i : inputs) {
			if (i.UTXO == null) continue;
			total += i.UTXO.value;
		}
		return total;
	}
	
	public boolean verifySignature() {
		String data = BlockUtil.getStringFromKey(sender) +
				      BlockUtil.getStringFromKey(reciepient) + 
				      Float.toString(value);
		return BlockUtil.verifyECDSASig(sender, data, signature);
	}

	public void generateSignature(PrivateKey privateKey) {
		String data = BlockUtil.getStringFromKey(sender) + 
				      BlockUtil.getStringFromKey(reciepient) +
				      Float.toString(value);
		signature = BlockUtil.applyECDSASig(privateKey, data);
	}
}
