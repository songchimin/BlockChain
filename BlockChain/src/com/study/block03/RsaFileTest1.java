package com.study.block03;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class RsaFileTest1 {

    public static void main(String[] args) {

        try {
            // 비대칭키 생성 하기
            PublicKey publicKey = null;
            PrivateKey privateKey = null;
             
            SecureRandom secureRandom = new SecureRandom();
            KeyPairGenerator keyGen;

            keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(512, secureRandom);
         
            KeyPair keyPair = keyGen.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
            
            // 개인키와 공개키를 특정한 파일 이름으로 저장
            BlockUtil.writePemFile(privateKey, "RSA PRIVATE KEY", "privateRSA.pem");
            BlockUtil.writePemFile(publicKey, "RSA PUBLIC KEY", "publicRSA.pem");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // ============================================================
        
        try {
            // 비대칭키 파일로부터 생성 하기
            PublicKey publicKey = 
            		BlockUtil.readPublicKeyFromPemFile("RSA", "publicRSA.pem");
            PrivateKey privateKey = 
            		BlockUtil.readPrivateKeyFromPemFile("RSA", "privateRSA.pem");

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
