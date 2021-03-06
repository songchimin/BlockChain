package com.study.block02;

import java.security.Key;
import java.security.MessageDigest;

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
}
