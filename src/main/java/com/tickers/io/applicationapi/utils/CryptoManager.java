package com.tickers.io.applicationapi.utils;

import com.tickers.io.applicationapi.services.PolygonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class CryptoManager {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PolygonService polygonService;

    public static final byte[] KEY =
            {118, 106, 107, 122, 76, 99, 69, 83, 101, 103, 82, 101, 116, 75, 101, 127};

    public String encrypt(String plainText) {
        if (plainText == null || plainText.isEmpty()) {
            logger.info("No data to encrypt!");
            return plainText;
        }
        Cipher cipher = null;
        String encryptedString = "";
        try {
            // Creating a Cipher object
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            // Creating a secret key from KEY byte array
            final SecretKeySpec secretKey = new SecretKeySpec(KEY, "AES");

            // Initializing a Cipher object
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Encrypting the plain text string
            byte[] encryptedText = cipher.doFinal(polygonService.getSecretKey().concat(plainText).getBytes());

            // Encoding the encrypted text to Base64
            encryptedString = Base64.getEncoder().encodeToString(encryptedText);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println("Exception caught while encrypting : " + ex);
        }
        return encryptedString;
    }

    public String decrypt(String cipherText) {
        if (cipherText == null || cipherText.isEmpty()) {
            logger.info("No data to decrypt!");
            return cipherText;
        }
        String decryptedString = "";
        Cipher cipher = null;
        try {
            // Creating a Cipher object
            cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

            // Creating a secret key from KEY byte array
            final SecretKeySpec secretKey = new SecretKeySpec(KEY, "AES");

            // Initializing a Cipher object
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Decoding from Base64
            byte[] encryptedText = Base64.getDecoder().decode(cipherText.getBytes());

            // Decrypting to plain text
            decryptedString = new String(cipher.doFinal(encryptedText));

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
                | IllegalBlockSizeException | BadPaddingException ex) {
            logger.info("Exception caught while decrypting : " + ex);
        }
        return decryptedString.replace(polygonService.getSecretKey(), "");
    }

    public String encryptApiKey() {
        return encrypt(polygonService.getApiKey());
    }

    public String decryptApiKey() {
        return decrypt(encryptApiKey());
    }
}
