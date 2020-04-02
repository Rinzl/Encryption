package com.dangth.encryption.utils;

import java.util.Base64;

public class EncryptionUtils {
    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    public static String byteArrayToString(byte[] bytes) {
        return ENCODER.encodeToString(bytes);
    }

    public static byte[] stringToByteArray(String text) {
        return DECODER.decode(text);
    }

}
