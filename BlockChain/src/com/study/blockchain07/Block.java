package com.study.blockchain07;

import java.util.ArrayList;
import java.util.Date;

public class Block {
	public String hash;
	public String previousHash;
	//public String data;
	public ArrayList<Transaction> transactionList;
	public long timeStamp;
	public int nonce;
	
	//public Block(String data, String previousHash) {
	public Block(String previousHash) {
		this.transactionList = new ArrayList<>();
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}
	
    public String getTransaction() {
        String transactionInformations = "";
        
        for(int i=0;i<transactionList.size();i++) {
            transactionInformations += transactionList.get(i).getInformation();
        }
        
        return transactionInformations;
    }
    public void addTransaction(Transaction transaction) {
        transactionList.add(transaction);
    }
    
	public String calculateHash() {
		String calculatedhash = BlockUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(nonce) + 
				//data 
				getTransaction()
				);
		return calculatedhash;
	}

	// 난이도를 적용하여 채굴
	public void mineBlock(int difficulty) {
		String target = BlockUtil.getDifficultyString(difficulty); 
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
			//System.out.printf("%8d : %s\n", nonce, hash);
		}
		
		System.out.printf("Block Mined!!! : %8d : %s\n", nonce, hash);
	}	
}
