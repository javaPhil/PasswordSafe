package com.pwhitman.neonpasswordsafe;

import android.util.Base64;
import android.util.Log;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Philip on 5/7/2015.
 */
public class PasswordUtility {
    private static final String TAG = "PasswordUtility";

    private static final String KEY = "PasswordGeneratorKey";

    private static final SecureRandom random = new SecureRandom();
    private static SecretKeySpec sks = null;

    private PasswordUtility(UUID uuid){
        try{

            byte[] key = KEY.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest();
            key = Arrays.copyOf(key, 16);
            sks = new SecretKeySpec(key, "AES");
        }catch(Exception e){
            Log.e(TAG, "Error with AES key generation", e);
        }
    }

    public static PasswordUtility getInstance(UUID uuid){
        return new PasswordUtility(uuid);
    }

    public static String generateRandomString(){
        return new BigInteger(60, random).toString(32);
    }

//    public AlgorithmParameterSpec getIV()
//    {
//        //byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
//        final int blockSize = cipher.getBlockSize();
//        final byte[] ivData = new byte[blockSize];
//        random.nextBytes(ivData);
//
//
//        IvParameterSpec ivParameterSpec;
//        ivParameterSpec = new IvParameterSpec(ivData);
//
//        return ivParameterSpec;
//    }


//    public String encrypt(String plainText) throws Exception
//    {
//        if (cipher == null) return null;
//        cipher.init(Cipher.ENCRYPT_MODE, sks, spec);
//        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
//        String encryptedText = new String(Base64.encode(encrypted, Base64.DEFAULT), "UTF-8");
//
//        return encryptedText;
//    }
//
//    public String decrypt(String cryptedText) throws Exception
//    {
//        if (cipher == null) return null;
//        cipher.init(Cipher.DECRYPT_MODE, sks, spec);
//        byte[] bytes = Base64.decode(cryptedText, Base64.DEFAULT);
//        byte[] decrypted = cipher.doFinal(bytes);
//        String decryptedText = new String(decrypted, "UTF-8");
//
//        return decryptedText;
//    }
//
    public String encryptString(String input){
        if(input == null  ||  input.isEmpty()) return null;
//        Log.i(TAG, "sks: " + sks.toString());
        Log.i(TAG, "inputString: " + input);

        byte[] encodedBytes = null;
        try{
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, sks);
            encodedBytes = c.doFinal(input.getBytes("UTF-8"));
        }catch(Exception e){
            Log.e(TAG, "AES encryption error", e);
        }
        return Base64.encodeToString(encodedBytes, Base64.DEFAULT);
    }

    public String decryptString(String inputString){
        if(inputString == null || inputString.trim().isEmpty()) return null;
        Log.i(TAG, "decryptString: " + inputString);
        byte[] decodedBytes = null;
        try{
            if(sks != null){
                Cipher c = Cipher.getInstance("AES");
                c.init(Cipher.DECRYPT_MODE, sks);
                byte[] inputBytes = inputString.getBytes("UTF-8");
                decodedBytes = c.doFinal(Base64.decode(inputBytes, Base64.DEFAULT));

            }

        }catch(Exception e){
            Log.e(TAG, "AES decryption error: ",e);
        }
        return decodedBytes != null ? new String(decodedBytes) : "";
    }

    public byte[] asBytes(UUID uuid){
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());

        return bb.array();
    }

}
