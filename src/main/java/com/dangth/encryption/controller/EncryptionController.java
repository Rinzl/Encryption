package com.dangth.encryption.controller;

import com.dangth.encryption.model.DecryptionRequest;
import com.dangth.encryption.model.EncryptionData;
import com.dangth.encryption.model.EncryptionRequest;
import com.dangth.encryption.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EncryptionController {

    private final EncryptionService encryptionService;

    @Autowired
    public EncryptionController(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @GetMapping("/generate")
    public EncryptionData generateKey() throws Exception {
        return encryptionService.generate();
    }

    @PostMapping("/encrypt")
    public String encrypt(@RequestBody EncryptionRequest encryptionRequest) throws Exception {
        return encryptionService.encrypt(encryptionRequest);
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestBody DecryptionRequest decryptionRequest) throws Exception {
        return encryptionService.decrypt(decryptionRequest);
    }
}
