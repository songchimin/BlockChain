package com.study.blockchain02;

public class Main {

	public static void main(String[] args) {
		int difficulty = 4; 	// 해쉬값의 앞 두자리가 0이어야 한다.
		int nonce = 0;
		
		while (true) {
			// SHA-256 해시 구하기 
			String hash = BlockUtil.applySha256("111" + nonce);
			// 비교대상
			String target = BlockUtil.getDifficultyString(difficulty); 
			// 미리 주어진 해시값에 맞는 값 구하기
			if (hash.substring(0, difficulty).equals(target)) {
				System.out.printf("Block Mined!!! : %8d : %s\n", nonce, hash);
				break;
			}
			nonce ++;
			//System.out.printf("%8d : %s\n", nonce, hash);
		}
		
	}

}
