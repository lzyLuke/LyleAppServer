package Security;

	import javax.crypto.Cipher;
	import java.io.ByteArrayOutputStream;
	import java.security.*;
	import java.security.interfaces.RSAPrivateKey;
	import java.security.interfaces.RSAPublicKey;
	import java.security.spec.InvalidKeySpecException;
	import java.security.spec.PKCS8EncodedKeySpec;
	import java.security.spec.X509EncodedKeySpec;
	import java.util.Base64;
	import java.util.HashMap;
	import java.util.Map;

	public class RSA {
		/*
	    public static final String CHARSET = "UTF-8";
	    public static final String RSA_ALGORITHM = "RSA";


	    public static Map<String, String> createKeys(int keySize){
	        //为RSA算法创建一个KeyPairGenerator对象
	        KeyPairGenerator kpg;
	        try{
	            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
	        }catch(NoSuchAlgorithmException e){
	            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
	        }

	        //初始化KeyPairGenerator对象,密钥长度
	        kpg.initialize(keySize);
	        //生成密匙对
	        KeyPair keyPair = kpg.generateKeyPair();
	        //得到公钥
	        Key publicKey = keyPair.getPublic();
	        
	        String publicKeyStr = Base64.getUrlEncoder().encodeToString(publicKey.getEncoded());
	        //得到私钥
	        Key privateKey = keyPair.getPrivate();
	        String privateKeyStr = Base64.getUrlEncoder().encodeToString(privateKey.getEncoded());
	        Map<String, String> keyPairMap = new HashMap<String, String>();
	        keyPairMap.put("publicKey", publicKeyStr);
	        keyPairMap.put("privateKey", privateKeyStr);

	        return keyPairMap;
	    }


	    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
	        //通过X509编码的Key指令获得公钥对象
	        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
	        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getUrlDecoder().decode(publicKey));
	        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
	        return key;
	    }

	    public static String encode(String str,String publicKey) throws Exception{
	    		return 	publicEncrypt(str, RSA.getPublicKey(publicKey));
	    	
	    }
	    
	    public static String decode(String str,String privateKey) throws Exception {
	    		return 	privateDecrypt(str, RSA.getPrivateKey(privateKey));
	    }

	    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
	        //通过PKCS#8编码的Key指令获得私钥对象
	        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
	        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getUrlDecoder().decode(privateKey));
	        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
	        return key;
	    }


	    public static String publicEncrypt(String data, RSAPublicKey publicKey){
	        try{
	            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
	            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	            return Base64.getUrlEncoder().encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
	        }catch(Exception e){
	            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
	        }
	    }


	    public static String privateDecrypt(String data, RSAPrivateKey privateKey){
	        try{
	            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
	            cipher.init(Cipher.DECRYPT_MODE, privateKey);
	           
	            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.getUrlDecoder().decode(data), privateKey.getModulus().bitLength()), CHARSET);
	        }catch(Exception e){
	            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
	        }
	    }



	    public static String privateEncrypt(String data, RSAPrivateKey privateKey){
	        try{
	            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
	            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	            
	            return Base64.getUrlEncoder().encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
	        }catch(Exception e){
	            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
	        }
	    }


	    public static String publicDecrypt(String data, RSAPublicKey publicKey){
	        try{
	            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
	            cipher.init(Cipher.DECRYPT_MODE, publicKey);
	            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.getUrlDecoder().decode(data), publicKey.getModulus().bitLength()), CHARSET);
	        }catch(Exception e){
	            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
	        }
	    }

	    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize){
	        int maxBlock = 0;
	        if(opmode == Cipher.DECRYPT_MODE){
	            maxBlock = keySize / 8;
	        }else{
	            maxBlock = keySize / 8 - 11;
	        }
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        byte[] buff;
	        int i = 0;
	        try{
	            while(datas.length > offSet){
	                if(datas.length-offSet > maxBlock){
	                    buff = cipher.doFinal(datas, offSet, maxBlock);
	                }else{
	                    buff = cipher.doFinal(datas, offSet, datas.length-offSet);
	                }
	                out.write(buff, 0, buff.length);
	                i++;
	                offSet = i * maxBlock;
	            }
	        }catch(Exception e){
	            throw new RuntimeException("加解密阀值为["+maxBlock+"]的数据时发生异常", e);
	        }
	        byte[] resultDatas = out.toByteArray();
	        try {
	        out.close();
	        }catch(Exception e) {e.printStackTrace();}
	        return resultDatas;
	    }

	    
	    public static void main (String[] args) throws Exception {
	        Map<String, String> keyMap = RSA.createKeys(1024);
	        //String  publicKey = keyMap.get("publicKey");
	        //String  privateKey = keyMap.get("privateKey");
	        //System.out.println("公钥: \n\r" + publicKey);
	        //System.out.println("私钥： \n\r" + privateKey);

	        String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBXMpCAdCmt1-jvgisJHQPdAgqFRg0uZhR8ed5cAcDhB37k-RDr0_cZupF4nflxfqFERPD-d5bA3pJe5N7wSvwpCxHJdoYf05HKj7p35eJzQxhKS_QBro2OSxtgGiGJ1oS0qB8Kg313uF2e4lfRfkqlIxL_TCZtqdX0ui0UNR57wIDAQAB";
	        String prikey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIFcykIB0Ka3X6O-CKwkdA90CCoVGDS5mFHx53lwBwOEHfuT5EOvT9xm6kXid-XF-oURE8P53lsDekl7k3vBK_CkLEcl2hh_TkcqPunfl4nNDGEpL9AGujY5LG2AaIYnWhLSoHwqDfXe4XZ7iV9F-SqUjEv9MJm2p1fS6LRQ1HnvAgMBAAECgYB_NwIMMX7ARLq_6wTaTKr5-3B_-eipCQ87Hro02S98jLNzkTe8PC48H9UpTpH8G1rG-0lFw3agaGYc13LtE8Wh4lgitpOUnNFkhd2yPRYxKDC4t2uDUFmyOK9ZN0zlX47PWAlcRSdPmWNFTsA2fr11drkntm3tPjobnaEUPA4OqQJBANE3vStUyPPs1Kmunx2qOzTn-ZZGCju3qHT9afmx8a1SRvlXrInLnFoXYHbELCMplBCmL9yjvDBqyZ_tBMPyCZMCQQCeSeRWe36tG6bNYDmOGdFY8zTECR8rpuUANVBgYLEdLFUVJUh21eExLCvNPeZlAbUfrveMpz3ZFPlJ9Yyhepe1AkAhA_DAM8L6azcsB7hoUALD2I-UXT0kK1lUDOF6W9BNj5xPoLxDinQD64h3dYGZkuggsHkBEn2SDmf-6sJDwaAtAkA3luvCE4zhbpyd8_VCmn6TJpaZYjq4Mdyqvg6ESnDpNfCLhJR2Z5a2ljSr19laD8d0YRY_J1zpKByEhN_mtg8hAkEAgX7K-mrmnLmR2YOxMaXxNLluoyIKUiM7D8HekAQA9pfKQMDkVRLpYjsGZViXS3Ol80Na_1LAqcuXT1eUTIkGGg==";
	        String encod ="HJ7WlL-oaZON8ArIp0kIGzB5QdXUQyI0L4U6yCD7bDeyhPpPzaI7C_5z9NDmMKwAGM6JGpwgiak1uWP5bGQjzEiP1Y3yD_RFRRlWuowcpLdgf_wnqHB29uZQ39kLVLu5WrMHTE1-TpHmePApGkVlhuQtFnc9DlElTy87d1ARZvc=";
	        System.out.println("公钥加密——私钥解密");
	        String str = "abc";
	        System.out.println("\r明文：\r\n" + str);
	        System.out.println("\r明文大小：\r\n" + str.getBytes().length);
	        //String encodedData = RSA.publicEncrypt(str, RSA.getPublicKey(publicKey));
	        String encodedData = RSA.encode(str,pubkey);
	        System.out.println("密文：\r\n" + encodedData);
	        //String decodedData = RSA.privateDecrypt(encodedData, RSA.getPrivateKey(privateKey));
	        String decodedData = RSA.decode(encodedData,prikey);
	        System.out.println("解密后文字: \r\n" + decodedData);


	    }
	    */
		
		
	
}

