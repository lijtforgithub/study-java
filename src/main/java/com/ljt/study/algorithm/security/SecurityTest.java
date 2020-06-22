package com.ljt.study.algorithm.security;

import org.junit.jupiter.api.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * @author LiJingTang
 * @date 2019-12-29 20:09
 */
public class SecurityTest {

    private static final String TEST_DATA = "LiJingTang";

    @Test
    public void testByte() {
        byte[] byteArray = parseHexStringToByte("110F01");

        System.out.println(Arrays.toString(byteArray));
        System.out.println(parseByteToHexString(byteArray));
    }

    @Test
    public void testMD5_encrypt() {
        System.out.println(TEST_DATA + " encrypt：" + MD5.encrypt(TEST_DATA));
    }

    @Test
    public void testSHA_encrypt() {
        // System.out.println(Character.MIN_RADIX);
        // System.out.println(Character.MAX_RADIX);

        System.out.println(SHA.encrypt(TEST_DATA));
    }

    @Test
    public void testMAC_encrypt() {
        String key = MAC.initMacKey();
        System.out.println("MAC 密钥: " + key);
        System.out.println("HMAC 加密: " + MAC.encryptHMAC(TEST_DATA, key));
        System.err.println("HMAC 加密: " + MAC.encryptHMAC(TEST_DATA, key));
    }

    @Test
    public void testAES_encrypt() {
        System.out.println(AES.encrypt(TEST_DATA));
    }

    @Test
    public void testAES_decrypt() {
        System.out.println(AES.decrypt(AES.encrypt(TEST_DATA)));
    }

    @Test
    public void testBASE64_encrypt() {
        System.out.println(TEST_DATA + " encrypt：" + BASE64.encrypt(TEST_DATA));
    }

    @Test
    public void testBASE64_decrypt() {
        String encryptData = BASE64.encrypt(TEST_DATA);
        System.out.println(encryptData + " decrypt：" + BASE64.decrypt(encryptData));
    }

    @Test
    public void testDES_encrypt() {
        System.out.println(DES.encrypt(TEST_DATA));
    }

    @Test
    public void testDES_decrypt() {
        String key = DES.initKey();
        System.out.println(key);
        String data = DES.encrypt(TEST_DATA, key);
        System.out.println(DES.decrypt(data, key));
    }

    @Test
    public void testPBE_encrypt() {
        byte[] salt = PBE.initSalt();
        System.out.println(PBE.encrypt(TEST_DATA, salt));
    }

    @Test
    public void testPBE_decrypt() {
        byte[] salt = PBE.initSalt();
        System.out.println(PBE.decrypt(PBE.encrypt(TEST_DATA, salt), salt));
    }


    private static class BASE64 {

        public static String encrypt(String data) {
            return new BASE64Encoder().encode(data.getBytes(UTF_8));
        }

        public static String decrypt(String data) {
            try {
                return new String(new BASE64Decoder().decodeBuffer(data));
            } catch (IOException e) {
                e.printStackTrace();

                return null;
            }
        }

        public static String encryptByte(byte[] byteArray) {
            return (new BASE64Encoder()).encodeBuffer(byteArray);
        }

        public static byte[] decryptByte(String data) {
            try {
                return (new BASE64Decoder()).decodeBuffer(data);
            } catch (IOException e) {
                e.printStackTrace();

                return null;
            }
        }
    }

    private static class MD5 {

        public static String encrypt(String data) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(data.getBytes(UTF_8));

                byte[] byteArray = messageDigest.digest();

//			    System.out.println(new BigInteger(byteArray).toString(16));
                return parseByteToHexString(byteArray);
//			    return BASE64.encrypt_byte(byteArray);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();

                return null;
            }
        }
    }

    private static class SHA {

        public static String encrypt(String data) {
            try {
                MessageDigest sha = MessageDigest.getInstance("SHA");
                sha.update(data.getBytes(UTF_8));

                return new BigInteger(sha.digest()).toString(16).toUpperCase();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private static class MAC {

        /**
         * MAC算法可选以下多种算法
         * HmacMD5
         * HmacSHA1
         * HmacSHA256
         * HmacSHA384
         * HmacSHA512
         */
        private static final String ALGORITHM = "HmacMD5";

        public static String initMacKey() {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
                SecretKey secretKey = keyGenerator.generateKey();
                return BASE64.encryptByte(secretKey.getEncoded());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }

        public static String encryptHMAC(String data) {
            return encryptHMAC(data, initMacKey());
        }

        private static String encryptHMAC(String data, String key) {
            SecretKey secretKey = new SecretKeySpec(BASE64.decryptByte(key), ALGORITHM);

            try {
                Mac mac = Mac.getInstance(secretKey.getAlgorithm());
                mac.init(secretKey);
                byte[] byteArray = mac.doFinal(data.getBytes(UTF_8));

                return new BigInteger(byteArray).toString(16).toUpperCase();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private static class AES {

        private static final String PASSWORD = "0123456789";

        public static String encrypt(String data) {
            try {
                SecretKeySpec secretKey = initKey(PASSWORD);
                Cipher cipher = Cipher.getInstance("AES");
                byte[] byteArray = data.getBytes(UTF_8);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byteArray = cipher.doFinal(byteArray);

                return parseByteToHexString(byteArray);
            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
        }

        public static String decrypt(String data) {
            try {
                SecretKeySpec secretKey = initKey(PASSWORD);
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] byteArray = cipher.doFinal(parseHexStringToByte(data));

                return new String(byteArray);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private static SecretKeySpec initKey(String password) throws NoSuchAlgorithmException {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes(UTF_8));
            keyGenerator.init(128, secureRandom);

            // keyGenerator.init(128,new SecureRandom(password.getBytes())); // Linux 会报错
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] encodeByteArray = secretKey.getEncoded();

            return new SecretKeySpec(encodeByteArray, "AES");
        }
    }

    private static class DES {

        // 加密后的byte数组是不能强制转换成字符串的，换言之：字符串和byte数组在这种情况下不是互逆的

        /**
         * ALGORITHM 算法 <br>
         * 可替换为以下任意一种算法，同时key值的size相应改变。
         *
         * <pre>
         * DES          		key size must be equal to 56
         * DESede(TripleDES) 	key size must be equal to 112 or 168
         * AES          		key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
         * Blowfish     		key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
         * RC2          		key size must be between 40 and 1024 bits
         * RC4(ARCFOUR) 		key size must be between 40 and 1024 bits
         * </pre>
         * <p>
         * 在Key toKey(byte[] key)方法中使用下述代码
         * <code>SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);</code> 替换
         * <code>
         * DESKeySpec dks = new DESKeySpec(key);
         * SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
         * SecretKey secretKey = keyFactory.generateSecret(dks);
         * </code>
         */
        private static final String ALGORITHM = "DES";
        private static String key;

        static {
            key = initKey();
        }

        public static String decrypt(String data) {
            return decrypt(data, key);
        }

        public static String decrypt(String data, String key) {
            try {
                Key k = toKey(BASE64.decryptByte(key));
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, k);

                return new String(cipher.doFinal(parseHexStringToByte(data)));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public static String encrypt(String data) {
            return encrypt(data, key);
        }

        public static String encrypt(String data, String key) {
            try {
                Key k = toKey(BASE64.decryptByte(key));
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, k);

                return parseByteToHexString(cipher.doFinal(data.getBytes())).toUpperCase();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private static Key toKey(byte[] key) {
            try {
                DESKeySpec dks = new DESKeySpec(key);
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);

                // 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
                // SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);

                return keyFactory.generateSecret(dks);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private static String initKey() {
            return initKey(null);
        }

        private static String initKey(String seed) {
            SecureRandom secureRandom = null;

            if (seed != null) {
                secureRandom = new SecureRandom(BASE64.decryptByte(seed));
            } else {
                secureRandom = new SecureRandom();
            }

            try {
                KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
                kg.init(secureRandom);
                SecretKey secretKey = kg.generateKey();

                return BASE64.encryptByte(secretKey.getEncoded());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private static class PBE {

        /**
         * 支持以下任意一种算法
         *
         * <pre>
         * PBEWithMD5AndDES
         * PBEWithMD5AndTripleDES
         * PBEWithSHA1AndDESede
         * PBEWithSHA1AndRC2_40
         * </pre>
         */
        private static final String ALGORITHM = "PBEWITHMD5andDES";
        private static final String PASSWORD = "0123456789";

        /**
         * 盐初始化
         */
        public static byte[] initSalt() {
            Random random = new Random();
            byte[] salt = new byte[8];
            random.nextBytes(salt);
            return salt;
        }

        /**
         * 转换密钥<br>
         */
        private static Key toKey(String password) throws Exception {
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);

            return keyFactory.generateSecret(keySpec);
        }

        /**
         * 加密
         */
        public static String encrypt(String data, byte[] salt) {
            return parseByteToHexString(encrypt(data, PASSWORD, salt));
        }

        public static byte[] encrypt(String data, String password, byte[] salt) {
            try {
                Key key = toKey(password);
                PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

                return cipher.doFinal(data.getBytes(UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * 解密
         */
        public static String decrypt(String data, byte[] salt) {
            return new String(decrypt(data, PASSWORD, salt));
        }

        public static byte[] decrypt(String data, String password, byte[] salt) {
            try {
                Key key = toKey(password);
                PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

                return cipher.doFinal(parseHexStringToByte(data));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 十六进制字符串转换成byte类型数组
     */
    private static byte[] parseHexStringToByte(String hexStr) {
        if (hexStr == null || hexStr.length() < 1) {
            return null;
        }

        byte[] byteArray = new byte[hexStr.length() / 2];

        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);

            byteArray[i] = (byte) (high * 16 + low);
        }

        return byteArray;
    }

    /**
     * byte类型数组转换成十六进制字符串
     */
    private static String parseByteToHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (byte b : byteArray) {
            String hex = Integer.toHexString(b & 0xFF);

            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }

}
