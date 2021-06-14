package com.orangetalents.proposta.propostas.encrypt;

import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component
@PropertySource("classpath:application.properties")
public class EncryptEDecrypt {
    @Value("${encrypt.key.secret}")
    private String key;
    @Value("${encrypt.initial.vector}")
    private String initialVector;
    private final String algorithm = "AES";
    private final String cipherAlgorithm = "AES/CBC/PKCS5PADDING";
    private final Charset charset = StandardCharsets.UTF_8;

    public String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initialVector.getBytes(charset));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(charset), algorithm);

            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErroInternoException();
        }
    }

    public String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initialVector.getBytes(charset));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(charset), algorithm);

            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErroInternoException();
        }
    }

    public String ofuscarDocumento(String documentoEncryptado) {
        String documento = decrypt(documentoEncryptado);
        if (documento.length() == 11) {
            return "******" + documento.substring(6, 11);
        }
        return "*******" + documento.substring(7, 14);
    }
}