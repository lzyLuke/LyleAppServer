package Handle;

import Book.loginBook;
import Security.*;
public class handleFirstConnection {
	public static String handle(String pubkey,int sessionID) {
		try {
		System.out.println("开始的公钥为"+pubkey);
		String aesKey = AES.newAESKey();
		System.out.println("随机生成的AESKEY为"+aesKey);
		
		String encryptAesKey = RSA.encode(aesKey, pubkey);
		loginBook.getInstance().putAesKey(sessionID, aesKey);
		return encryptAesKey;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
