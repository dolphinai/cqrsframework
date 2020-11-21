package com.github.dolphinai.cqrsframework.common.crypto;

import com.github.dolphinai.cqrsframework.common.util.StringHelper;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

/**
 */
public final class CipherUtils {

  private CipherUtils(){}

  public static PublicKey getPublicKey(final InputStream inputStream) throws CertificateException {
    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    Certificate crt = cf.generateCertificate(inputStream);
    return crt.getPublicKey();
  }

  public static SecretKey generateKey(final String algorithm, final byte[] keyBytes) {
    Objects.requireNonNull(algorithm);
    Objects.requireNonNull(keyBytes);
    KeyGenerator generator;
    try {
      generator = KeyGenerator.getInstance(algorithm);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
    // init.
    // size: DES=56, AES=128
    generator.init(new SecureRandom(keyBytes));
    return generator.generateKey();
  }

  public static SecretKey generateDESKey(final byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
    final SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
    DESKeySpec keySpec = new DESKeySpec(keyBytes);
    return factory.generateSecret(keySpec);
  }

  public static Cipher encryptCipher(final String algorithm, final Key key, final IvParameterSpec ivParameter) {
    if(StringHelper.isEmpty(algorithm) || key == null) {
      return null;
    }
    Cipher instance;
    try {
      instance = Cipher.getInstance(algorithm);
      if (ivParameter == null) {
        instance.init(Cipher.ENCRYPT_MODE, key);
      } else {
        instance.init(Cipher.ENCRYPT_MODE, key, ivParameter);
      }
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
      throw new IllegalStateException(e);
    }
    return instance;
  }

  public static Cipher decryptCipher(final String algorithm, final Key key, final IvParameterSpec ivParameter) {
    if(StringHelper.isEmpty(algorithm) || key == null) {
      return null;
    }
    Cipher instance;
    try {
      instance = Cipher.getInstance(algorithm);
      if (ivParameter == null) {
        instance.init(Cipher.DECRYPT_MODE, key);
      } else {
        instance.init(Cipher.DECRYPT_MODE, key, ivParameter);
      }
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
      throw new IllegalStateException(e);
    }
    return instance;
  }
}
