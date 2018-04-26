package Security;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	
	private AES instance = new AES();
	public ConcurrentHashMap<String,String> user2key = new ConcurrentHashMap<String,String>();
	
	public void addAESKey(String userId,String AESKEY) {
		user2key.put(userId, AESKEY);
	}
	public String getKey(String userId) {
		return user2key.get(userId);
	}
	
	public AES getInstance() {
		return instance;
	}
	
	private AES() {
		
	}
	
	public static void main(String[] args) {
		String password = "ABCDEFGHIJKLMNOP";
        String content1 = "1";
        System.out.println("加密前：" + content1);  
        String content2 = Encrypt(content1, password);
        System.out.println("加密后：" + content2);
        String content3 = Decrypt(content2, password);
        System.out.println("解密后：" + content3);

	}

	public static String newAESKey(){
		  
	    String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

	        Random random=new Random();  
	        StringBuffer sb=new StringBuffer();
	
	        for(int i=0; i<16; ++i){
	         
	          int number=random.nextInt(62);
	      
	          sb.append(str.charAt(number));
	        }
	      
	        return sb.toString();
	}
	
	public static String Encrypt(String sSrc, String sKey) {
        try{
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

            return Base64.getUrlEncoder().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
	
	public static String Decrypt(String sSrc, String sKey) {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64.getUrlDecoder().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
}
