package com.dangth.encryption.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EncryptionRequest {
    private String content;
    private String privateKey;
}
