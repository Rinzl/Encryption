package com.dangth.encryption.controller;

import com.dangth.encryption.model.DecryptionRequest;
import com.dangth.encryption.model.EncryptionData;
import com.dangth.encryption.model.EncryptionRequest;
import static com.dangth.encryption.utils.EncryptionUtils.*;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@RestController
@RequestMapping("/api")
public class EncryptionController {

    @GetMapping("/generate")
    public EncryptionData generateKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair keyPair = keyGen.generateKeyPair();
        byte[] privateKey = keyPair.getPrivate().getEncoded();
        byte[] publicKey = keyPair.getPublic().getEncoded();
        return new EncryptionData(byteArrayToString(privateKey), byteArrayToString(publicKey));
    }

    @PostMapping("/encrypt")
    public String encrypt(@RequestBody EncryptionRequest encryptionRequest) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        byte [] pkcs8EncodedBytes = stringToByteArray(encryptionRequest.getPrivateKey());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(keySpec);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return byteArrayToString(cipher.doFinal(stringToByteArray(encryptionRequest.getContent())));
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestBody DecryptionRequest decryptionRequest) throws Exception {
        byte [] keyBytes = stringToByteArray(decryptionRequest.getPublicKey());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        Cipher cipher = Cipher.getInstance("RSA");
        KeyFactory kf = KeyFactory.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, kf.generatePublic(spec));
        return byteArrayToString(cipher.doFinal(stringToByteArray(decryptionRequest.getEncryptedText())));
    }
}
