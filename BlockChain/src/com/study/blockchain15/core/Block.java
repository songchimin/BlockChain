package com.study.blockchain15.core;

import java.util.ArrayList;
import java.util.Date;

import com.study.blockchain15.util.BlockUtil;

public class Block {

	public String hash; 			// 해시값
	public String previousHash ;	// 이전 블럭의 해시값
	public String data; 			// 블럭의 data
	public int nonce; 				// 해시를 얻기 위해 갱신한 횟수
	public long timeStamp;			// timestamp
	public String merkleRoot;
	
	// 개별 블록에 기록될 트랜잭션의 내역들
	public ArrayList<Transaction> transactionList = new ArrayList<>();
    
	public Block(String data, String previousHash) {
		this.data = data;
    	this.previousHash = previousHash;
    	this.timeStamp = new Date().getTime();
    	this.hash = calculateHash();
	}	
	
	public String calculateHash() {
    	String hash = BlockUtil.applySha256(
    			previousHash + 
    			Long.toString(timeStamp) + 
    			Integer.toString(nonce) + 
    			data +
    			merkleRoot
    			);
    	return hash;
    }
	
	// 난이도를 적용하여 채굴
	public void mineBlock(int difficulty) {
    	merkleRoot = BlockUtil.getMerkleRoot(transactionList);
    	String target = BlockUtil.getDifficultyString(difficulty);
    	while (!hash.substring(0, difficulty).equals(target)) {
    		nonce++;
    		hash = calculateHash();
			//System.out.printf("%8d : %s\n", nonce, hash);
    	}

    	System.out.printf("Block Mined!!! : %8d : %s\n", nonce, hash);
    }

	public boolean addTransaction(Transaction tx) {
    	if (tx == null) return false;
    	if ((!previousHash.equals("0"))) {
    		if (tx.processTransaction() != true) {
    			return false;
    		}
    	}
    	transactionList.add(tx);
    	System.out.println("===> Transaction added to Block");
    	return true;
    }
}
