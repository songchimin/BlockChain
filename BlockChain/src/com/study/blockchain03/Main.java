package com.study.blockchain03;

public class Main {

	public static int difficulty = 4; 	// 해쉬값의 앞 네자리가 0이어야 한다.
	
	public static void main(String[] args) {
		// 블록 구성
		Block myBlock = new Block("first", "0");

		// 난이도를 지정하여 블럭을 채굴
		myBlock.mineBlock(difficulty);
		
		String value = BlockUtil.getJson(myBlock);
		System.out.println(value);
	}
}
