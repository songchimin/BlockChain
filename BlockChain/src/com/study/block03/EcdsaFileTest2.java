package com.study.block03;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class EcdsaFileTest2 {

	public static void main(String[] args) {

        // 바운시 캐슬의 암호화 라이브러리를 사용하도록 설정
		Security.addProvider(new BouncyCastleProvider());

		try {
	        // 비대칭키 생성 하기
	        PublicKey publicKey = null;
	        PrivateKey privateKey = null;

	        // 바운시 캐슬의 타원 곡선 표준 알고리즘(ECDSA)을 사용한다. / ("EC", "SunEC")
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			// 타원 곡선의 세부 알고리즘으로 prime192v1을 사용한다. / sect163k1
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
	
			// Initialize the key generator
			keyGen.initialize(ecSpec, random);
			// Generate a KeyPair
			// - 256 bytes provides an acceptable security level
	        KeyPair keyPair = keyGen.generateKeyPair();
	
	        // Get the public and private keys from the keyPair
	        privateKey = keyPair.getPrivate();
	        publicKey = keyPair.getPublic();
	        
            // walletA의 개인키와 공개키를 특정한 파일 이름으로 저장
            BlockUtil.writePemFile(privateKey, "ECDSA PRIVATE KEY", "privateECDSA.pem");
            BlockUtil.writePemFile(publicKey, "ECDSA PUBLIC KEY", "publicECDSA.pem");
		} catch(Exception e) {
			throw new RuntimeException(e);
		}

		// ======================================================================
		
        try {
            // 비대칭키 파일로부터 생성 하기
            PublicKey publicKey = 
            		BlockUtil.readPublicKeyFromPemFile("ECDSA", "publicECDSA.pem");
            PrivateKey privateKey = 
            		BlockUtil.readPrivateKeyFromPemFile("ECDSA", "privateECDSA.pem");

    		// Test public and private keys
    		System.out.println(privateKey);
    		System.out.println(publicKey);
            System.out.println("Private key : " + BlockUtil.getStringFromKey(privateKey));
            System.out.println(" Public key : " + BlockUtil.getStringFromKey(publicKey));
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
}
