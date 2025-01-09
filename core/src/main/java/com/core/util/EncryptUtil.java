package com.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
    public static String sha256Encrypt(String data){
        StringBuilder result = new StringBuilder();
        try {
            // 创建 SHA-256 哈希函数实例
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // 计算输入字符串的哈希值
            byte[] hash = digest.digest(data.getBytes());
            // 将字节数组转换为十六进制字符串
            for (byte b : hash) {
                result.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return result.toString();
    }
}
