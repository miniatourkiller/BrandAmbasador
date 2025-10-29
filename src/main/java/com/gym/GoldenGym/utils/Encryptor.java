package com.gym.GoldenGym.utils;

import java.util.Base64;

public class Encryptor {

    // >>>>>>>>>>>>>>>Encryptor process<<<<<<<<<<<<<<<<<<<<

    // add 5 characters in the front

    // convert to bytes

    // reverse the bytes

    // base64 the reversed bytes

    // add 5 characters to result

    // convert into bytes

    // reverse the bytes

    // base 64 bytes

    public static String encrypt(String string) {
        String rand = RandomGenerator.generateRandomString(5);
        String newString = rand + string + rand;
        byte[] bytes = stringToBytes(newString);
        byte[] reversedBytes = reverseBytes(bytes);
        String base64EncodedString = base64EncodeBytes(reversedBytes);

        // repeat the process
        String newRand = RandomGenerator.generateRandomString(5);
        newString = newRand + base64EncodedString + newRand;
        bytes = stringToBytes(newString);
        reversedBytes = reverseBytes(bytes);
        base64EncodedString = base64EncodeBytes(reversedBytes);
        return base64EncodedString;
    }

    public static String decrypt(String string) {
        byte[] bytes = base64DecodeString(string);
        byte[] reversedBytes = reverseBytes(bytes);
        String newString = new String(reversedBytes);
        String v1 = newString.substring(5, newString.length() - 5);

        // repeat the process
        bytes = base64DecodeString(v1);
        reversedBytes = reverseBytes(bytes);
        newString = new String(reversedBytes);
        v1 = newString.substring(5, newString.length() - 5);
        return v1;
    }

    private static byte[] reverseBytes(byte[] bytes) {
        byte[] reversedBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            reversedBytes[i] = bytes[bytes.length - 1 - i];
        }
        return reversedBytes;
    }

    private static String base64EncodeBytes(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static byte[] base64DecodeString(String encodedString) {
        return Base64.getDecoder().decode(encodedString);
    }

    private static byte[] stringToBytes(String string) {
        return string.getBytes();
    }

}
