package com.study.block04;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;

public class RsaMessageTest {

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
          
        String sPlain1 = "Welcome to RSA";
        String sPlain2 = null;

        try {
            // 비대칭키 파일로부터 생성 하기
            PublicKey publicKey = BlockUtil.readPublicKeyFromPemFile("RSA", "publicRSA.pem");
            PrivateKey privateKey = BlockUtil.readPrivateKeyFromPemFile("RSA", "privateRSA.pem");

            Cipher cipher = Cipher.getInstance("RSA");

            // 공개키 이용 암호화
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bCipher1 = cipher.doFinal(sPlain1.getBytes());
            String sCipherBase64 = Base64.getEncoder().encodeToString(bCipher1);
                
            System.out.println("원문   : " + sPlain1);
            System.out.println("암호화 : " + sCipherBase64);

            // 개인키 이용 복호화
            byte[] bCipher2 = Base64.getDecoder().decode(sCipherBase64.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bPlain2 = cipher.doFinal(bCipher2);
            sPlain2 = new String(bPlain2);

            System.out.println("복호화 : " + sPlain2);
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
