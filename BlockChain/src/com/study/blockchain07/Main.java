package com.study.blockchain07;

import java.util.ArrayList;

import com.study.blockchain05.BlockUtil;

public class Main {

	public static ArrayList<Block> blockChain = new ArrayList<>();
	public static int difficulty = 4; 	// 해쉬값의 앞 두자리가 0이어야 한다.
	
	public static void main(String[] args) {
		
        Block block1 = new Block("0");
        addBlock(block1);
        
        Block block2 = new Block(block1.hash);
        block2.addTransaction(new Transaction("A", "B", 1.5));
        block2.addTransaction(new Transaction("C", "B", 0.7));
        addBlock(block2);
        
        Block block3 = new Block(block2.hash);
        block3.addTransaction(new Transaction("D", "E", 8.2));
        block3.addTransaction(new Transaction("B", "A", 0.4));
        addBlock(block3);
        
        Block block4 = new Block(block3.hash);
        block4.addTransaction(new Transaction("E", "D", 0.1));
        addBlock(block4);
		
		String value = BlockUtil.getJson(blockChain);
		System.out.println(value);
    }
	
	public static void addBlock(Block newBlock) {
		// 난이도를 지정하여 블럭을 채굴
		newBlock.mineBlock(difficulty);
		blockChain.add(newBlock);
	}
}
