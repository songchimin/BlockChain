package com.study.blockchain05;

import java.util.ArrayList;

public class Main {

	public static ArrayList<Block> blockChain = new ArrayList<>();
	public static int difficulty = 4; 	// 해쉬값의 앞 두자리가 0이어야 한다.
	
	public static void main(String[] args) {
		// 블록 구성
		addBlock(new Block("first", "0"));
		addBlock(new Block("second", blockChain.get(blockChain.size()-1).hash));
		addBlock(new Block("third", blockChain.get(blockChain.size()-1).hash));
		addBlock(new Block("fourth", blockChain.get(blockChain.size()-1).hash));
		
		String value = BlockUtil.getJson(blockChain);
		System.out.println(value);
	}

	public static void addBlock(Block newBlock) {
		// 난이도를 지정하여 블럭을 채굴
		newBlock.mineBlock(difficulty);
		blockChain.add(newBlock);
	}
}
