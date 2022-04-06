package com.sabi.globaladmin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static com.sabi.globaladmin.utils.AESEncryption.AES;



public class EncyptionUtil {

    private static Logger LOG = LoggerFactory.getLogger(EncyptionUtil.class);

    public static String generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(AES);
        SecureRandom random = new SecureRandom(); // cryptograph. secure random
        keyGen.init(random);
        SecretKey secretKey = keyGen.generateKey();

        byte[] raw = secretKey.getEncoded();

        return org.apache.commons.codec.binary.Base64.encodeBase64String(raw).substring(0, 16);
    }

    public static String bytesToHex(final byte[] data) {
        int len = data.length;
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if ((data[i] & 0xFF) < 16) {
                b.append("0").append(Integer.toHexString(data[i] & 0xFF));
            } else {
                b.append(Integer.toHexString(data[i] & 0xFF));
            }
        }
        return b.toString();
    }

    public static byte[] hexToBytes(final String str) {
        if (str.length() < 2) {
            return null;
        } else {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }
    }

    public static String generateSha256(String originalString){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
            return sha256BytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("Unable to generateSha256 of {} ", originalString, e);
        }

        return "";
    }

    private static String sha256BytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
