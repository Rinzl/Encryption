package com.dangth.encryption.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class EncryptionRequest {
    private String content;
    private String privateKey;
}
