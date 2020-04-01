package com.dangth.encryption.controller;

import com.dangth.encryption.model.DecryptionRequest;
import com.dangth.encryption.model.EncryptionData;
import com.dangth.encryption.model.EncryptionRequest;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@RestController
@RequestMapping("/api")
public class EncryptionController {

    @GetMapping("/generate")
    public EncryptionData generateKey() throws NoSuchAlgorithmException {
        Base64.Encoder encoder = Base64.getEncoder();
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair keyPair = keyGen.generateKeyPair();
        byte[] privateKey = keyPair.getPrivate().getEncoded();
        byte[] publicKey = keyPair.getPublic().getEncoded();
        return new EncryptionData(encoder.encodeToString((privateKey)), encoder.encodeToString(publicKey));
    }

    @PostMapping("/encrypt")
    public String encrypt(@RequestBody EncryptionRequest encryptionRequest) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        Base64.Decoder decoder = Base64.getDecoder();
        byte [] pkcs8EncodedBytes = decoder.decode(encryptionRequest.getPrivateKey());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(keySpec);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(decoder.decode(encryptionRequest.getContent())));
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestBody DecryptionRequest decryptionRequest) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte [] keyBytes = decoder.decode(decryptionRequest.getPublicKey());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        Cipher cipher = Cipher.getInstance("RSA");
        KeyFactory kf = KeyFactory.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, kf.generatePublic(spec));
        return Base64.getEncoder().encodeToString(cipher.doFinal(decoder.decode(decryptionRequest.getEncryptedText())));
    }
}
