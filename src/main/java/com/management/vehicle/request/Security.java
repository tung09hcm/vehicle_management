package com.management.vehicle.request;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Security {
    private final byte[] key1 = {0x45, 0x47, 0x57, 0x47, 0x74, 0x51, 0x4f, 0x51, 0x4d, 0x73, 0x70, 0x55, 0x73, 0x76, 0x5a, 0x6c, 0x4a, 0x50, 0x4b, 0x5a, 0x7a, 0x71, 0x36, 0x42, 0x59, 0x37, 0x35, 0x4c, 0x69, 0x6a, 0x45, 0x48};
    private final byte[] key2 = {0x4e, 0x48, 0x43, 0x72, 0x77, 0x7a, 0x6e, 0x61, 0x6b, 0x45, 0x76, 0x63, 0x5a, 0x76, 0x6c, 0x70, 0x35, 0x31, 0x4d, 0x59, 0x45, 0x38, 0x7a, 0x31, 0x54, 0x7a, 0x39, 0x38, 0x6f, 0x32, 0x45, 0x6d};

    /**
     * Encrypts the given data using AES encryption and the provided keys.
     *
     * @param data the data to be encrypted.
     * @return the encrypted data.
     * @throws Exception if there's an error during the encryption.
     */
    public byte[] encrypt(byte[] data) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key2, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedWithAES = Base64.getEncoder().encodeToString(cipher.doFinal(data)).getBytes();
        byte[] encryptedWithKey1 = new byte[encryptedWithAES.length];
        for (int i = 0; i < encryptedWithAES.length; i++) {
            encryptedWithKey1[i] = (byte) (encryptedWithAES[i] ^ key1[i % key1.length]);
        }

        return encryptedWithKey1;
    }

    /**
     * Decrypts the given data using AES decryption and the provided keys.
     *
     * @param data the data to be decrypted.
     * @return the decrypted data.
     * @throws Exception if there's an error during the decryption.
     */
    public byte[] decrypt(byte[] data) throws Exception {
        byte[] decryptedWithKey1 = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            decryptedWithKey1[i] = (byte) (data[i] ^ key1[i % key1.length]);
        }
        SecretKeySpec keySpec = new SecretKeySpec(key2, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        return cipher.doFinal(Base64.getDecoder().decode(new String(decryptedWithKey1)));
    }

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param bytes the byte array to be converted.
     * @return a string representing the hexadecimal value of the byte array.
     */
    public String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Converts a hexadecimal string to a byte array.
     *
     * @param hex the hexadecimal string to be converted.
     * @return a byte array representing the value of the hexadecimal string.
     */
    public byte[] hexToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }

    /**
     * Converts a byte array to a string.
     *
     * @param bytes the byte array to be converted.
     * @return a string representing the value of the byte array.
     */
    public String byteToString(byte[] bytes) {
        return new String(bytes);
    }
}
