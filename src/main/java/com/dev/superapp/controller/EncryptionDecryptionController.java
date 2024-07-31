package com.dev.superapp.controller;

import com.dev.superapp.util.AESEncryptionUtil;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@RestController
@RequestMapping("/api")
public class EncryptionDecryptionController {

    private static final String AES = "AES";
    // Encrypt endpoint
    @PostMapping("/encrypt")
    public String encryptFile(@RequestParam String inputFilePath, @RequestParam String outputFilePath, @RequestParam String secretKey) {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, AES);
        AESEncryptionUtil.encryptFile(inputFilePath, outputFilePath, originalKey);
        return "File encrypted successfully!";
    }

    // Decrypt endpoint
    @PostMapping("/decrypt")
    public String decryptFile(@RequestParam String inputFilePath, @RequestParam String outputFilePath, @RequestParam String secretKey) {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, AES);
        AESEncryptionUtil.decryptFile(inputFilePath, outputFilePath, originalKey);
        return "File decrypted successfully!";
    }

    // Generate AES Secret Key endpoint
    @GetMapping("/generateKey")
    public String generateAESKey() {
        SecretKey secretKey = AESEncryptionUtil.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

}
