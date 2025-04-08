package net.kwkang.gallery.common.utill;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtils {

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "MySecretKey12345"; // 16. 24, 32비트 여야한대요.

    public static String encrypt(String value) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedValue = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedValue);
        }catch(Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            throw new RuntimeException("데이터 암호화 중 오류가 발생했습니다.");
        }
    }

    public static String decrypt(String value) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decryptedValue = Base64.getDecoder().decode(value);
            return new String(cipher.doFinal(decryptedValue));
        }catch(Exception e) {
            throw new RuntimeException("데이터 복호화 중 오류가 발생했습니다.");
        }
    }
}
