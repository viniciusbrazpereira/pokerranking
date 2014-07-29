package sfingsolutions.utils;

import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe utilitária contendo as funções de criptografia e descriptografia de
 * mensagens.
 * 
 * @author Eduardo Galego.
 */
public class DesEncrypterUtils {

    private static final Logger log = LoggerFactory
            .getLogger(DesEncrypterUtils.class);

    private static DesEncrypterUtils instance;

    private final String passPhrase = "PRODESP52";

    private Cipher ecipher;
    private Cipher dcipher;

    // 8-byte Salt
    private static final byte[] salt = {(byte) 0xA9, (byte) 0x9B, (byte) 0xC8,
        (byte) 0x32, (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03, };

    // Iteration count
    private static final int iterationCount = 19;

    /**
     * Obtém a instância de DesEncrypterUtils.
     * 
     * @return DesEncrypterUtils.
     */
    public static DesEncrypterUtils getInstance() {
        if (instance == null) {
            instance = new DesEncrypterUtils();
        }
        return instance;
    }

    /**
     * Construtor da classe DesEncrypterUtils.
     */
    public DesEncrypterUtils() {
        try {
            // Create the key
            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt,
                    iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
                    .generateSecret(keySpec);
            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());

            // Prepare the parameter to the ciphers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,
                    iterationCount);

            // Create the ciphers
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } catch (java.security.InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            log.error("Algoritimo inválido " + e.getMessage());
        } catch (java.security.spec.InvalidKeySpecException e) {
            e.printStackTrace();
            log.error("Chave inválida " + e.getMessage());
        } catch (javax.crypto.NoSuchPaddingException e) {
            e.printStackTrace();
            log.error("Algoritimo inválido " + e.getMessage());
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (java.security.InvalidKeyException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    /**
     * Método utilizado para encriptografar uma mensagem.
     * 
     * @param str
     *            Mensagem
     * @return Mensagem encriptografada.
     */
    public String encrypt(String str) {
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            String ret = new sun.misc.BASE64Encoder().encode(enc);

            return ret;
        } catch (javax.crypto.BadPaddingException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * Método utilizado para descriptografar uma mensagem.
     * 
     * @param str
     *            Mensagem criptografada.
     * @return Mensagem original.
     */

    public String decrypt(String str) {
        try {
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (javax.crypto.BadPaddingException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (java.io.IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }
}
