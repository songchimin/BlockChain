package com.study.block01;

public class Sha256Test {

	public static void main(String[] args) {
		
		// SHA-256 해시 테스트 
		String hash = BlockUtil.applySha256("test21");
		System.out.println("1234567890123456789012345678901234567890123456789012345678901234");
		System.out.println("         1         2         3         4         5         6");
		System.out.println(hash);
	}
}
