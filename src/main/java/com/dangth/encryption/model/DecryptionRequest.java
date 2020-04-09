package com.dangth.encryption.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class DecryptionRequest {
    private String publicKey;
    private String encryptedText;
}
