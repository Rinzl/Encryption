package com.dangth.encryption.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EncryptionData {
    private String privateKey;
    private String publicKey;
}
