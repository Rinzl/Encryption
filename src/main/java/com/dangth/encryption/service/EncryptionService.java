package com.dangth.encryption.service;

import com.dangth.encryption.model.DecryptionRequest;
import com.dangth.encryption.model.EncryptionData;
import com.dangth.encryption.model.EncryptionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
@Slf4j
public class EncryptionService {

    public String encrypt(EncryptionRequest encryptionRequest) throws Exception {
        log.info("{}", encryptionRequest);
        Cipher cipher = Cipher.getInstance("RSA");
        byte [] pkcs8EncodedBytes = Base64.getDecoder().decode(encryptionRequest.getPrivateKey());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(keySpec);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(encryptionRequest.getContent().getBytes()));
    }

    public String decrypt(DecryptionRequest decryptionRequest) throws Exception {
        log.info("{}", decryptionRequest);
        byte [] publicKeyBytes = Base64.getDecoder().decode(decryptionRequest.getPublicKey());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
        Cipher cipher = Cipher.getInstance("RSA");
        KeyFactory kf = KeyFactory.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, kf.generatePublic(spec));
        String decryptedString = new String(cipher.doFinal(Base64.getDecoder().decode(decryptionRequest.getEncryptedText())));
        log.info("Decrypted result : {}", decryptedString);
        return decryptedString;
    }

    public EncryptionData generate() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair keyPair = keyGen.generateKeyPair();
        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        return new EncryptionData(privateKey, publicKey);
    }
}
