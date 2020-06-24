package com.study.blockchain15.core;

import java.security.PublicKey;

import com.study.blockchain15.util.BlockUtil;

public class TransactionOutput {
	public String transactionId;		// 거래 번호(Id)
	public PublicKey reciepient; 		// 받는사람
	public float value; 				// 금액
	public String parentTransactionId; 	// 부모 거래 번호(Id)
	
	// 생성자
	public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId) {
		this.reciepient = reciepient;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
		// 거래 번호는 조건에 맞춰 새로 생성
		this.transactionId = BlockUtil.applySha256(
				BlockUtil.getStringFromKey(reciepient) +
				Float.toString(value) +
				parentTransactionId );
	}
	
	// 받는 사람 주소가 그 사람의 계좌주소와 같다면 동일인
	public boolean isMine(PublicKey publicKey) {
		return (publicKey == reciepient);
	}
}
