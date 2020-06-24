package com.study.blockchain15;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.google.gson.GsonBuilder;
import com.study.blockchain15.core.Block;
import com.study.blockchain15.core.Transaction;
import com.study.blockchain15.core.TransactionOutput;
import com.study.blockchain15.core.Wallet;

public class Main {

	public static HashMap<String, TransactionOutput> UTXOs = new HashMap<>();

	// 생성되는 블록들 저장
	private static ArrayList<Block> blockChain = new ArrayList<>();
	private static int difficulty = 2;
	
	public static void main(String[] args) {

		// 바운시 캐슬의 암호화 라이브러리를 사용하도록 설정
		Security.addProvider(new BouncyCastleProvider());

		// 1.지갑 생성
		Wallet coinbase = new Wallet();
		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();
		Wallet walletC = new Wallet();
		Wallet walletD = new Wallet();

		//Test public and private keys
//		System.out.println("Private and public keys:");
//		System.out.println(BlockUtil.getStringFromKey(walletA.privateKey));
//		System.out.println(BlockUtil.getStringFromKey(walletA.publicKey));

		// 2.제너시스 트랜잭션 생성
		// -- input 거래는 없고, output 거래만 생성한다.
		Transaction genesisTransaction;
		// -- (A.계좌-sender, B.계좌-reciepient, 금액-value, null)
		genesisTransaction = new Transaction(coinbase.publicKey, walletA.publicKey, 100f);
		// -- 원시거래이므로 거래번호 직접 입력
		genesisTransaction.transactionId = "0";
//		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.value, genesisTransaction.transactionId));
		genesisTransaction.outputs.add(new TransactionOutput(walletA.publicKey, 100f, genesisTransaction.transactionId));
		// -- sender의 서명
		genesisTransaction.generateSignature(coinbase.privateKey);
		
		//서명한 Transaction을 검증합니다.
		System.out.println("Is this Transaction Verify? " + genesisTransaction.verifySignature());
		
		// 3.거래 내역(TransactionOutput) 저장
		UTXOs.put(genesisTransaction.outputs.get(0).transactionId, genesisTransaction.outputs.get(0));
		
		// 4.제너시스 블록 생성
		Block genesisBlock = new Block("Genesis block", "0"); 
		addBlock(genesisBlock);

		// 5.제너시스 블록에 트랜잭션 한 개 추가
		genesisBlock.addTransaction(genesisTransaction);
		System.out.println("walletA.getBalance(): " + walletA.getBalance());

		// 6.다음 블록(Block1) 생성
		Block block1 = new Block("", genesisBlock.hash);
		addBlock(block1);

		// 7.다음 블록(Block1)에 트랜잭션 두 개 추가
		block1.addTransaction(walletA.sendFunds(walletB.publicKey, 50f));
		block1.addTransaction(walletA.sendFunds(walletC.publicKey, 30f));

//		block1.addTransaction(walletB.sendFunds(walletD.publicKey, 50f));
//		block1.addTransaction(walletC.sendFunds(walletD.publicKey, 50f));

		System.out.println("walletA.getBalance(): " + walletA.getBalance());
		System.out.println("walletB.getBalance(): " + walletB.getBalance());
		System.out.println("walletC.getBalance(): " + walletC.getBalance());
//		System.out.println("walletD.getBalance(): " + walletD.getBalance());

		
		// 전체 blockchain이 정상인지 체크
		System.out.println("\nBlockchain is Valid : " + isChainValid());
		
		// 전체 블럭을 출력합니다.
//		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
//		System.out.println("\nBlock list : ");
//		System.out.println(blockchainJson);
	}

	public static void addBlock(Block newBlock) {
		// 난이도를 지정하여 새 블럭을 채굴
		newBlock.mineBlock(difficulty);
		blockChain.add(newBlock);
	}

	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;

		//전체 블럭을 체크합니다.
		for (int i=1; i < blockChain.size(); i++) {
			currentBlock = blockChain.get(i);
			previousBlock = blockChain.get(i-1);
			
			//현재 블럭의 hash가 맞는지 체크합니다.
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			
			//이전 블럭의 hash값과 동일한지 체크합니다.
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
		}
		return true;
	}	



}
