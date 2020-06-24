package com.study.block03;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.util.encoders.Base64;

public class BlockUtil {
    
    public static String applySha256(String input){
        
        StringBuffer hexString = new StringBuffer(); 
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
        }
        catch(Exception e) {}
        
        return hexString.toString();
    }
    
    public static String getStringFromKey(Key key) {
        return java.util.Base64.getEncoder().encodeToString(key.getEncoded());
        //return Base64.encode(key.getEncoded()).toString();
    }
    
    // Pem 클래스를 이용해 생성된 암호키를 파일로 저장
    public static void writePemFile(Key key, String description, String filename) 
            throws FileNotFoundException, IOException
    {
        Pem pemFile = new Pem(key, description);
        pemFile.write(filename);
        //System.out.println(
        //        String.format("EC 암호키 %s을(를) %s 파일로 내보냈습니다.",
        //        description, filename) );
    }

    // 문자열 형태의 인증서에서 개인키를 추출하는 함수
    public static PrivateKey readPrivateKeyFromPemFile(String algorithm, String privateKeyName) 
            throws FileNotFoundException, IOException,
            NoSuchAlgorithmException, InvalidKeySpecException
    {
        String data = readString(privateKeyName); 
        //System.out.println("개인키를 " + privateKeyName + "로부터 불러왔습니다.");
        //System.out.println(data);
        
        // 불필요한 설명 구문 제거
        data = data.replaceAll("-----BEGIN " + algorithm + " PRIVATE KEY-----", "");
        data = data.replaceAll("-----END " + algorithm + " PRIVATE KEY-----", "");
        
        // PEM 파일은 Base64로 인코딩 되어있으므로 디코딩
        byte[] decoded = Base64.decode(data);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory factory = KeyFactory.getInstance(algorithm);
        PrivateKey privateKey = factory.generatePrivate(spec);
        return privateKey;
    }
    
    // 문자열 형태의 인증서에서 공키를 추출하는 함수
    public static PublicKey readPublicKeyFromPemFile(String algorithm, String publicKeyName) 
            throws FileNotFoundException, IOException,
            NoSuchAlgorithmException, InvalidKeySpecException
    {
        String data = readString(publicKeyName); 
        //System.out.println("공개키를 " + publicKeyName + "로부터 불러왔습니다.");
        //System.out.println(data);
        
        // 불필요한 설명 구문 제거
        data = data.replaceAll("-----BEGIN " + algorithm + " PUBLIC KEY-----", "");
        data = data.replaceAll("-----END " + algorithm + " PUBLIC KEY-----", "");
        
        // PEM 파일은 Base64로 인코딩 되어있으므로 디코딩
        byte[] decoded = Base64.decode(data);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        KeyFactory factory = KeyFactory.getInstance(algorithm);
        PublicKey publicKey = factory.generatePublic(spec);
        return publicKey;
    }
    
    // 특정한 파일에 작성되어 있는 문자열을 읽어옴.
    private static String readString(String filename) throws FileNotFoundException, IOException {
        String pem = "";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        
        String line;
        while((line = br.readLine()) != null) pem += line + "\n";
        
        br.close();
        return pem;
    }
}
