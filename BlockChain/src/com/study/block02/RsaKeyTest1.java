package com.study.block02;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class RsaKeyTest1 {

    public static void main(String[] args) {

        // 비대칭키 생성 하기
        PublicKey publicKey = null;
        PrivateKey privateKey = null;
         
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyGen;
        
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(512, secureRandom);
         
            KeyPair keyPair = keyGen.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
        } catch (Exception e) {
            e.printStackTrace();
        }
              
        // Test public and private keys
        System.out.println(privateKey);
        System.out.println(publicKey);
        System.out.println("Private key : " + BlockUtil.getStringFromKey(privateKey));
        System.out.println(" Public key : " + BlockUtil.getStringFromKey(publicKey));
    }

}
