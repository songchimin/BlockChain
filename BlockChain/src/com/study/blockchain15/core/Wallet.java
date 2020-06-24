package com.study.blockchain15.core;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.study.blockchain15.Main;

public class Wallet {
	public PrivateKey privateKey;	// 개인키
	public PublicKey publicKey;		// 공개키
	public HashMap<String, TransactionOutput> Wallet_UTXO = new HashMap<>();
									// 트랜잭션 output을 담는 해시맵
	
	public Wallet() {
		// 지갑이 생성될 때 개인키와 공개키를 생성한다.
		generateKeyPair();
	}
	
	// 잔고 확인 기능
	public float getBalance() {
		float total = 0;
		// 아직 소비되지 않은 전체 트랜잭션 리스트에서 내 것만 찾아 온다.
		// 여러명으로부터 입금되어 있을 수 있다. --> 트랜잭션이 여러 개일 수 있다.
        for (Map.Entry<String, TransactionOutput> item: Main.UTXOs.entrySet()){
        	TransactionOutput UTXO = item.getValue();
            if (UTXO.isMine(publicKey)) {
            	// 나중에 송신할 때 편하게 UTXOs로부터 내 것만 따로 모아 둔다.
            	Wallet_UTXO.put(UTXO.transactionId, UTXO); 
            	total += UTXO.value;
            }
        }  
		return total;
	}
	
	// 송금기능 (A --> B)
	public Transaction sendFunds(PublicKey recipient, float value) {
		if (getBalance() < value) {
			System.out.println("Not enough value!");
			return null;
		}
		
		// 송금을 하기 전 나한테 입금된 내역이 여러 개 있을 수 있다.
		ArrayList<TransactionInput> inputs = new ArrayList<>();
		
		float total = 0;
		for (Map.Entry<String, TransactionOutput> item: Wallet_UTXO.entrySet()) {
			TransactionOutput UTXO = item.getValue();
			total += UTXO.value;
			inputs.add(new TransactionInput(UTXO.transactionId));
			if (total > value) break;
		}
		
		Transaction newTransaction = new Transaction(publicKey, recipient, value);
		System.out.println(inputs.size());
		newTransaction.inputs = inputs;
		newTransaction.generateSignature(privateKey);
		
		for (TransactionInput input: inputs) {
			Wallet_UTXO.remove(input.transactionOutputId);
		}
		
		return newTransaction;
	}
	
	public void generateKeyPair() {
		try {
			// 바운시 캐슬의 타원 곡선 표준 알고리즘(ECDSA)을 사용한다.
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			// 타원 곡선의 세부 알고리즘으로 prime192v1을 사용한다. / sect163k1, secp256k1
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
	
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random);
			// 256 bytes provides an acceptable security level
	        KeyPair keyPair = keyGen.generateKeyPair();
	
	        // Set the public and private keys from the keyPair
	        privateKey = keyPair.getPrivate();
	        publicKey = keyPair.getPublic();
	        
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
