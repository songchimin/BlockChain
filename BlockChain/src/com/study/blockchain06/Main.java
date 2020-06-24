package com.study.blockchain06;

public class Main {

	public static void main(String[] args) {
		
		Transaction transaction = new Transaction("A", "B", 10.5);
        System.out.println(transaction.getInformation());
    }

}
