package Security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import com.mysql.cj.core.util.Base64Decoder;

/**
 * RSAUtil工具类
 */
public class RSAUtil {

    // RSA key位数
    private static final int KEY_SIZE = 512;

    // 单次RSA加密操作所允许的最大块长度，该值与 KEY_SIZE、padding方法有关。
    // 1024key->128,2048key->1256,512key->53,11个字节用于保存padding信息。
    private static final int BLOCK_SIZE = 53;

    private static final int OUTPUT_BLOCK_SIZE = 64;

    // private static final int KEY_SIZE = 1024;
    // private static final int BLOCK_SIZE = 117;
    // private static final int OUTPUT_BLOCK_SIZE = 128;

    // private static final int KEY_SIZE = 2048;
    // private static final int BLOCK_SIZE = 245;
    // private static final int OUTPUT_BLOCK_SIZE = 256;

    private static SecureRandom secrand = new SecureRandom();
    public static Cipher rsaCipher;

    public static String Algorithm = "RSA";// RSA、RSA/ECB/PKCS1Padding

    // public static String
    // Algorithm="RSA/ECB/PKCS1Padding";//RSA、RSA/ECB/PKCS1Padding

    public RSAUtil() throws Exception {
    }

    /**
     * 生成密钥对
     * 
     * @return KeyPair
     * @throws Exception
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static KeyPair generateRSAKeyPair() {
        KeyPair keyPair  =  null;
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator
                    .getInstance(Algorithm);
            // 密钥位数
            keyPairGen.initialize(KEY_SIZE);
            // 密钥对
            keyPair = keyPairGen.generateKeyPair();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyPair;
    }

    /**
     * 生成密钥对
     * 
     * @return KeyPair
     * @throws Exception
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static String[] generateRSAKeyPairStringArray() {
        String[] keypair = new String[2];
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator
                    .getInstance(Algorithm);
            // 密钥位数
            keyPairGen.initialize(KEY_SIZE);
            // 密钥对
            KeyPair keyPair = keyPairGen.generateKeyPair();

            // 公钥
            PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

            // 私钥
            PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            String publicKeyString = getKeyString(publicKey);
            System.out.println("Public KEY===>" + publicKeyString);
            keypair[0] = publicKeyString;

            String privateKeyString = getKeyString(privateKey);
            System.out.println("Private KEY===>" + privateKeyString);
            keypair[1] = privateKeyString;

        } catch (Exception e) {
            System.err.println("Exception:" + e.getMessage());
        }
        return keypair;
    }

    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.getUrlDecoder().decode(key.getBytes("UTF-8"));

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithm);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.getUrlDecoder().decode(key.getBytes("UTF-8"));

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithm);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        
        return privateKey;
    }

    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        byte[] urlbytes=Base64.getUrlEncoder().encode(keyBytes);
        String s = new String(urlbytes, "UTF-8");
        return s;
    }

    /**
     * 对content采用RSA 公钥加密，再使用BASE64加密
     * 
     * @param strPubKey
     *            公钥值
     * @param content
     *            明文串
     * @return cardSecretPwd
     * @throws Exception
     */
    public static String encode(String content,String publicKeyString)
            throws Exception {

        try {
            rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw e;
        }

        Key publicKey = getPublicKey(publicKeyString);
        try {
            // PublicKey pubkey = keys.getPublic();
            rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey, secrand);
            // System.out.println(rsaCipher.getBlockSize());
            // System.out.println(Message.getBytes("utf-8").length);
            // byte[] encryptedData =
            // rsaCipher.doFinal(Message.getBytes("utf-8"));
            byte[] data = content.getBytes("UTF-8");
            int blocks = data.length / BLOCK_SIZE;
            int lastBlockSize = data.length % BLOCK_SIZE;
            byte[] encryptedData = new byte[(lastBlockSize == 0 ? blocks
                    : blocks + 1) * OUTPUT_BLOCK_SIZE];
            for (int i = 0; i < blocks; i++) {
                // int thisBlockSize = ( i + 1 ) * BLOCK_SIZE > data.length ?
                // data.length - i * BLOCK_SIZE : BLOCK_SIZE ;
                rsaCipher.doFinal(data, i * BLOCK_SIZE, BLOCK_SIZE,
                        encryptedData, i * OUTPUT_BLOCK_SIZE);
            }
            if (lastBlockSize != 0) {
                rsaCipher.doFinal(data, blocks * BLOCK_SIZE, lastBlockSize,
                        encryptedData, blocks * OUTPUT_BLOCK_SIZE);
            }
       
            return   new String (Base64.getUrlEncoder().encode(encryptedData),"UTF-8");
            

        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new IOException("InvalidKey");
        } catch (ShortBufferException e) {
            e.printStackTrace();
            throw new IOException("ShortBuffer");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IOException("UnsupportedEncoding");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new IOException("IllegalBlockSize");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new IOException("BadPadding");
        } finally {

        }
    }



    /**
     * BASE64解密，再RSA解密
     * 
     * @param strPriKey
     *            私钥值
     * @param content
     *            密文串
     * @return 用私钥解密串
     * @throws Exception
     */
    public static String decode(String content,String privateKeyString)
            throws Exception {

        try {
            rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw e;
        }

        byte[] decoded = null;
        try {
        		
            decoded = Base64.getUrlDecoder().decode(content.getBytes("UTF-8"));
         
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Key privateKey = getPrivateKey(privateKeyString);
        try {
            rsaCipher.init(Cipher.DECRYPT_MODE, privateKey, secrand);
            int blocks = decoded.length / OUTPUT_BLOCK_SIZE;
            ByteArrayOutputStream decodedStream = new ByteArrayOutputStream(
                    decoded.length);
            for (int i = 0; i < blocks; i++) {
                decodedStream.write(rsaCipher.doFinal(decoded, i
                        * OUTPUT_BLOCK_SIZE, OUTPUT_BLOCK_SIZE));
            }
            return new String(decodedStream.toByteArray(), "UTF-8");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new IOException("InvalidKey");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IOException("UnsupportedEncoding");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new IOException("IllegalBlockSize");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new IOException("BadPadding");
        } finally {

        }
    }
    


    public static void main(String[] args) throws Exception {
        KeyPair kp = RSAUtil.generateRSAKeyPair();
        RSAPublicKey pubk = (RSAPublicKey) kp.getPublic();//生成公钥
        RSAPrivateKey prik= (RSAPrivateKey) kp.getPrivate();//生成私钥
        String publicKey = RSAUtil.getKeyString(pubk);
        String privateKey = RSAUtil.getKeyString(prik);
        
        String publicKey1 = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAM_9bM8nF2OZxSC5GqfhkIyyGbHYhdbzi5tDlponutJGq0zSq-VGrnhr_lskIaX7mbl4Vv54MiBOyII06uEniscCAwEAAQ==";
        String privateKey1 = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAz_1szycXY5nFILkap-GQjLIZsdiF1vOLm0OWmie60karTNKr5UaueGv-WyQhpfuZuXhW_ngyIE7IgjTq4SeKxwIDAQABAkB5BRdrzW4hiRaXfL0S0_-crgCfZ8IwmVMxC3m0xstAj8paiCkD8tjWF5RTVckXr417K6vNoFC8YLj_C8SDjUPRAiEA_dLnJkYoUFIk33ruGYu5a_nHx4dgFbaJ033wBOIEz38CIQDRxezQX7m1laSItKCVP1vc4242bJLV0VOLyVq5-Z5ouQIgUlP7B44P-2V5ckdEPWL1pJEi_JPrVX-cms2pcVwCJW8CIQCF1vhvQoQkfSOLLWO_lbswxdLN2pwc2_-oEoJWYhNV0QIgE55idcAReeqftQU5WNFShqHfj3ijc0VAUCH_2ruaOlQ=";
        System.out.println("生成的公钥 " + publicKey1);
        System.out.println("生成的秘钥 " + privateKey1);
        String encodeStr = RSAUtil.encode("123abc", publicKey1);
        System.out.println("使用公钥后加密的内容 ：" +encodeStr);
        String decodeStr = RSAUtil.decode(encodeStr,privateKey1);
        System.out.println("解密后的内容："+decodeStr);
        

    }

}