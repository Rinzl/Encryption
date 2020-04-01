package com.dangth.encryption.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DecryptionRequest {
    private String publicKey;
    private String encryptedText;
}
