package com.sabi.globaladmin.utils;



import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by stanlee on 29/03/2018.
 */

@Service
public class AESEncryption {

//    private InstitutionCredentialsRepo institutionCredentialsRepo;

    private String password;

    public static final String COMMA = ",", FULL_COLON = ":", ALGORITHM = "AES/CBC/PKCS5Padding", AES = "AES";



    public static String encryptAES(String text, String secretkey, String iv) {

        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
        SecretKeySpec keyspec = new SecretKeySpec(secretkey.getBytes(), AES);
        try
        {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            return EncyptionUtil.bytesToHex(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {



        }
        return null;
    }

    public static String decryptAES(String encryptedRequest, String secretkey, String iv) {
        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
        SecretKeySpec keyspec = new SecretKeySpec(secretkey.getBytes(), AES);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            return new String(cipher.doFinal(EncyptionUtil.hexToBytes(encryptedRequest)), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {


        }
        return null;
    }



}
