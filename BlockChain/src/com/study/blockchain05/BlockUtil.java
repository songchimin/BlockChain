package com.study.blockchain05;

import java.security.MessageDigest;
import com.google.gson.GsonBuilder;

public class BlockUtil {
	
	public static String applySha256(String input){
		
		StringBuffer hexString = new StringBuffer(); 
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
	        
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
		}
		catch(Exception e) {}
		
		return hexString.toString();
	}

	public static String getDifficultyString(int difficulty) {
		//return new String(new char[difficulty]).replace('\0', '0');
		String str = String.format("%0"+Integer.toString(difficulty)+"d", 0);
		return str; 
	}

	public static String getJson(Object o) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(o);
	}
}
