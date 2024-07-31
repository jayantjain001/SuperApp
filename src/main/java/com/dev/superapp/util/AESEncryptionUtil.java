package com.dev.superapp.util;

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AESEncryptionUtil {

    private static final String AES = "AES";
    private AESEncryptionUtil(){

    }


    // Generate AES key
    @SneakyThrows
    public static SecretKey generateKey() {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(256); // AES 256 bits key size
        return keyGenerator.generateKey();
    }

    // Encrypt file content
    @SneakyThrows
    public static void encryptFile(String inputFilePath, String outputFilePath,SecretKey secretKey){
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(Files.readAllBytes(Paths.get(inputFilePath)));
        Files.write(Paths.get(outputFilePath), encrypted);
    }

    // Decrypt file content
    @SneakyThrows
    public static void decryptFile(String inputFilePath, String outputFilePath, SecretKey secretKey)  {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted = cipher.doFinal(Files.readAllBytes(Paths.get(inputFilePath)));
        Files.write(Paths.get(outputFilePath), decrypted);
    }
}

