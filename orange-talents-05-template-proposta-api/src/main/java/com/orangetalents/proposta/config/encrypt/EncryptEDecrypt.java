package com.orangetalents.proposta.config.encrypt;

import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.propostas.ConsultaPropostaController;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(ConsultaPropostaController.class);

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

    public String ofuscar(String input) {
        String documento = decrypt(input);
        if (documento.contains("@")) {
            return documento.replaceAll("(?<=.)[^@](?=[^@]*?@)|(?:(?<=@.)|(?!^)\\\\G(?=[^@]*$)).(?=.*\\\\.)", "*");
        }
        if (documento.matches("([0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4})")) {
            return documento.replaceAll(".(?=.{4})", "*");
        }
        if (documento.length() == 11) {
            return documento.replaceAll(".(?=.{5})", "*");
        }
        if (documento.length() == 14) {
            return documento.replaceAll(".(?=.{7})", "*");
        }
        logger.warn("Falha no formato do documento: " + documento);
        throw new ErroInternoException();
    }
}