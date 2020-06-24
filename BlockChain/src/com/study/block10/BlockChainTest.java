package com.study.block10;

class Block {
	int val;
	Block next;
	
	Block(int data) {
		this.val = data;
	}
}

public class BlockChainTest {

	public static void main(String[] args) {
		Block block1 = new Block(1);
		Block block2 = new Block(2);
		block1.next = block2;
		block1.next.next = new Block(3);
		block1.next.next.next = new Block(4);
		
		print(block1);
	}
	
	// 모든 블럭이 체인으로 연결되어 있기 때문에
	// 첫 번째 블럭을 이용하여 모든 블럭을 탐색할 수 있다.
	public static void print(Block block) {
		while (block != null) {
			System.out.println(block.val);
			block = block.next;
		}
	}
}
