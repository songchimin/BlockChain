package com.study.blockchain04;

import java.util.ArrayList;

public class Main {

	public static ArrayList<Block> blockChain = new ArrayList<>();
	public static int difficulty = 4; 	// 해쉬값의 앞 네자리가 0이어야 한다.
	
	public static void main(String[] args) {
		// 블록 구성
		Block block1 = new Block("first", "0");
		// 난이도를 지정하여 블럭을 채굴
		block1.mineBlock(difficulty);
		// 리스트에 추가
		blockChain.add(block1);
		
		// 블록 구성
		Block block2 = new Block("second", block1.hash);
		// 난이도를 지정하여 블럭을 채굴
		block2.mineBlock(difficulty);
		// 리스트에 추가
		blockChain.add(block2);
		
		String value = BlockUtil.getJson(blockChain);
		System.out.println(value);
	}
}
