package Handle;

import java.util.Arrays;

import Book.loginBook;
import Security.*;
public class handleFirstConnection {
	public static String handle(String pubkey,int sessionID) {
		try {
		System.out.println("Received PublicKey"+pubkey);
		String aesKey = AES.newAESKey();
		System.out.println("RandomGenerated"+aesKey);
		
		String encryptAesKey = RSAUtil.encode(aesKey, pubkey);
		System.out.println("Bytes:"+Arrays.toString(encryptAesKey.getBytes("UTF-8")));
		loginBook.getInstance().putAesKey(sessionID, aesKey);
		return encryptAesKey;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
