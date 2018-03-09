package com.example.base.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
  
/***
 * RSA加密工具类
* <p>Title: RSACryptUtil</p>
* <p>Description: </p>
* <p>Company: infinite </p>
* @author Junhua Hu
* @date 2016-8-8
 */
public class RSACryptUtil {  
    public static final String KEY_ALGORITHM = "RSA";  
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";  
    private static final String PRIVATE_KEY = "RSAPrivateKey";  
  
    /** 
     * 用私钥对信息生成数字签名 
     *  
     * @param data 
     *            加密数据 
     * @param privateKey 
     *            私钥 
     *  
     * @return 
     * @throws Exception 
     */  
    public static String sign(String data, String privateKey) throws Exception {  
    	byte[] databyt = data.getBytes("UTF-8");
        // 解密由base64编码的私钥  
        byte[] keyBytes = decryptBASE64(privateKey);  
  
        // 构造PKCS8EncodedKeySpec对象  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
  
        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
  
        // 取私钥匙对象  
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 用私钥对信息生成数字签名  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        signature.initSign(priKey);  
        signature.update(databyt);  
  
        return encryptBASE64(signature.sign());  
    }  
  
    /** 
     * 校验数字签名 
     *  
     * @param data 
     *            加密数据 
     * @param publicKey 
     *            公钥 
     * @param sign 
     *            数字签名 
     *  
     * @return 校验成功返回true 失败返回false 
     * @throws Exception 
     *  
     */  
    public static boolean verify(String data, String publicKey, String sign)  
            throws Exception {  
    	byte[] databyt = data.getBytes("UTF-8");
        // 解密由base64编码的公钥  
        byte[] keyBytes = decryptBASE64(publicKey);  
  
        // 构造X509EncodedKeySpec对象  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
  
        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
  
        // 取公钥匙对象  
        PublicKey pubKey = keyFactory.generatePublic(keySpec);  
  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        signature.initVerify(pubKey);  
        signature.update(databyt);  
        // 验证签名是否正常  
        return signature.verify(decryptBASE64(sign));  
    }  
    
    public static byte[] decryptBASE64(String data){
    	 return Base64.decodeBase64(data);
    }
    
    public static String encryptBASE64(byte[] data){
    	return Base64.encodeBase64String(data);
    }
    /** 
     * 解密<br> 
     * 用私钥解密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptByPrivateKey(byte[] data, String key)  
            throws Exception {  
        // 对密钥解密  
        byte[] keyBytes = decryptBASE64(key);  
  
        // 取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 对数据解密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
  
        byte[] enBytes = null;
		for (int i = 0; i < data.length; i += 128) {
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i,
					i + 128));
			enBytes = ArrayUtils.addAll(enBytes, doFinal);
		}
		return enBytes; 
    } 
    public static String decryptByPrivateKey(String data, String key)  
            throws Exception {  
        // 对密钥解密  
        byte[] keyBytes = decryptBASE64(key);  
        byte[] dataBytes = decryptBASE64(data);
  
        // 取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 对数据解密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
  
        byte[] enBytes = null;
		for (int i = 0; i < dataBytes.length; i += 128) {
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(dataBytes, i,
					i + 128));
			enBytes = ArrayUtils.addAll(enBytes, doFinal);
		}
		return new String(enBytes,"utf-8"); 
    }
  
    /** 
     * 解密<br> 
     * 用公钥解密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptByPublicKey(byte[] data, String key)  
            throws Exception {  
        // 对密钥解密  
        byte[] keyBytes = decryptBASE64(key);  
  
        // 取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key publicKey = keyFactory.generatePublic(x509KeySpec);  
  
        // 对数据解密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, publicKey);  
  
		byte[] enBytes = null;
		for (int i = 0; i < data.length; i += 128) {
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i,
					i + 128));
			enBytes = ArrayUtils.addAll(enBytes, doFinal);
		}
		return enBytes;
    }  
    
    /***
     * 用公钥解密-包装方法 
     * @param data - base64编码后的加密数据
     * @param key -公钥
     * @return 解密后原始数据字符串
     * @throws Exception String
     * @author Junhua Hu
     * @date 2016-8-9
     */
    public static String decryptByPublicKey(String data, String key)  
            throws Exception {  
        // 对密钥解密  
        byte[] keyBytes = decryptBASE64(key);  
        byte[] dataBytes = decryptBASE64(data);
  
        // 取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key publicKey = keyFactory.generatePublic(x509KeySpec);  
  
        // 对数据解密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, publicKey);  
  
		byte[] enBytes = null;
		for (int i = 0; i < dataBytes.length; i += 128) {
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(dataBytes, i,
					i + 128));
			enBytes = ArrayUtils.addAll(enBytes, doFinal);
		}
		return new String(enBytes,"UTF-8");
    }
    /** 
     * 加密<br> 
     * 用公钥加密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] encryptByPublicKey(byte[] data, String key)  
            throws Exception {  
        // 对公钥解密  
        byte[] keyBytes = decryptBASE64(key);  
  
        // 取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key publicKey = keyFactory.generatePublic(x509KeySpec);  
  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
  
        byte[] enBytes = null;
        for (int i = 0; i < data.length; i += 64) {  
        // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i,i + 64));  
            enBytes = ArrayUtils.addAll(enBytes, doFinal);  
        }
        return enBytes;   
    }  
  
    
    /** 
     * 加密<br> 
     * 用公钥加密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static String encryptByPublicKey(String data, String key)  
            throws Exception {  
        // 对公钥解密  
        byte[] keyBytes = decryptBASE64(key);  
        
        byte[] dataBytes = data.getBytes("UTF-8");
  
        // 取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key publicKey = keyFactory.generatePublic(x509KeySpec);  
  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
  
        byte[] enBytes = null;
        for (int i = 0; i < dataBytes.length; i += 64) {  
        // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(dataBytes, i,i + 64));  
            enBytes = ArrayUtils.addAll(enBytes, doFinal);  
        }
        return encryptBASE64(enBytes);   
    }  
    /** 
     * 加密<br> 
     * 用私钥加密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] encryptByPrivateKey(byte[] data, String key)  
            throws Exception {  
        // 对密钥解密  
        byte[] keyBytes = decryptBASE64(key);  
  
        // 取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
        byte[] enBytes = null;
        for (int i = 0; i < data.length; i += 64) {  
        // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i,i + 64));  
            enBytes = ArrayUtils.addAll(enBytes, doFinal);  
        }
        return enBytes;  
    }
    /***
     * 用私钥加密  -加密-包装方法 
     * @param data -原始数据
     * @param key - 私钥
     * @return -base64编码后的字符串
     * @throws Exception String
     * @author Junhua Hu
     * @date 2016-8-9
     */
    public static String encryptByPrivateKey(String data, String key)  
            throws Exception {  
        // 对密钥解密  
        byte[] keyBytes = decryptBASE64(key);  
        byte[] dataBytes = data.getBytes("UTF-8");
        // 取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
        byte[] enBytes = null;
        for (int i = 0; i < dataBytes.length; i += 64) {  
        // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(dataBytes, i,i + 64));  
            enBytes = ArrayUtils.addAll(enBytes, doFinal);  
        }
        return encryptBASE64(enBytes);  
    }
    
    
    
    /** 
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[] 
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程 
 
     *  
     * @param arrB 
     *            需要转换的byte数组 
 
     * @return 转换后的字符串 
     * @throws Exception 
     *             本方法不处理任何异常，所有异常全部抛出 
     */  
    public static String byteArr2HexStr(byte[] arrB) throws Exception {  
        int iLen = arrB.length;  
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍  
        StringBuffer sb = new StringBuffer(iLen * 2);  
        for (int i = 0; i < iLen; i++) {  
            int intTmp = arrB[i];  
            // 把负数转换为正数  
            while (intTmp < 0) {  
                intTmp = intTmp + 256;  
            }  
            // 小于0F的数需要在前面补0  
            if (intTmp < 16) {  
                sb.append("0");  
            }  
            sb.append(Integer.toString(intTmp, 16));  
        }  
        return sb.toString();  
    }  
    
    
    
    /** 
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB) 
     * 互为可逆的转换过程 
     *  
     * @param strIn 
     *            需要转换的字符串 
     * @return 转换后的byte数组 
 
     * @throws Exception 
     *             本方法不处理任何异常，所有异常全部抛出 
     */  
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {  
        byte[] arrB = strIn.getBytes();  
        int iLen = arrB.length;  
  
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2  
        byte[] arrOut = new byte[iLen / 2];  
        for (int i = 0; i < iLen; i = i + 2) {  
            String strTmp = new String(arrB, i, 2);  
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);  
        }  
        return arrOut;  
    } 
    
    /** 
     * 取得私钥 
     *  
     * @param keyMap 
     * @return 
     * @throws Exception 
     */  
    public static String getPrivateKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get(PRIVATE_KEY);  
  
        return encryptBASE64(key.getEncoded());  
    }  
  
    /** 
     * 取得公钥 
     *  
     * @param keyMap 
     * @return 
     * @throws Exception 
     */  
    public static String getPublicKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get(PUBLIC_KEY);  
  
        return encryptBASE64(key.getEncoded());  
    }  
  
    /** 
     * 初始化密钥 
     *  
     * @return 
     * @throws Exception 
     */  
    public static Map<String, Object> initKey() throws Exception {  
        KeyPairGenerator keyPairGen = KeyPairGenerator  
                .getInstance(KEY_ALGORITHM);  
        keyPairGen.initialize(1024);  
  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
  
        // 公钥  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
  
        // 私钥  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
  
        Map<String, Object> keyMap = new HashMap<String, Object>(2);  
        System.out.println("公钥："+encryptBASE64(publicKey.getEncoded()));
        System.out.println("私钥："+encryptBASE64(privateKey.getEncoded()));
        keyMap.put(PUBLIC_KEY, publicKey);  
        keyMap.put(PRIVATE_KEY, privateKey);  
        return keyMap;  
    }  
    
}  