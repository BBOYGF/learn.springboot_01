package com.learn.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptTool {
    public static  String encryptSHA256(String str,String secretkey) {
        Mac sha256_HMAC = null;
        StringBuilder sb = null;
        try {
            sha256_HMAC = Mac.getInstance("HmacSHA256");


        SecretKeySpec secret_key = new SecretKeySpec(secretkey.getBytes("UTF-8"), "HmacSHA256");

        sha256_HMAC.init(secret_key);

        byte[] array = sha256_HMAC.doFinal(str.getBytes("UTF-8"));

        sb = new StringBuilder();

        for (byte item : array) {

            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));

        }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString().toUpperCase();


    }
}
