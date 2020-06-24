package com.study.blockchain01;

public class Main {

	public static void main(String[] args) {
		long nonce = 0;
		
		while (true) {
			// SHA-256 해시 구하기 
			String hash = BlockUtil.applySha256("111" + nonce);
			
			// 미리 주어진 해시값에 맞는 값 구하기
			// 해시는 역계산이 안되므로 원하는 값이 나올 때까지
			// 데이터를 바꾸어 가면서 정방향으로 단순 대입 반복
			if (hash.substring(0, 5).equals("00000")) {
				System.out.printf("Block Mined!!! : %8d : %s\n", nonce, hash);
				break;
			}
			nonce ++;
			System.out.printf("%8d : %s\n", nonce, hash);
		}
		
	}
}
