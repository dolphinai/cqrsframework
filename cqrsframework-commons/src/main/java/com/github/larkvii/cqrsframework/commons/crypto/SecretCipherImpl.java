package com.github.larkvii.cqrsframework.commons.crypto;

import com.github.larkvii.cqrsframework.commons.util.Charsets;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;

public final class SecretCipherImpl implements SecretCipher {

  private final Cipher encryptCipher;
  private final Cipher decryptCipher;

  public SecretCipherImpl(final String transform, final String key) throws GeneralSecurityException {
    this(transform, generateKey(transform, key));
  }

  public SecretCipherImpl(final String transform, final Key key) throws GeneralSecurityException {
    this(transform, key, key);
  }

  public SecretCipherImpl(final String transform, final Key encryptKey, final Key decryptKey) throws GeneralSecurityException {
    this.encryptCipher = Cipher.getInstance(transform);
    encryptCipher.init(Cipher.ENCRYPT_MODE, encryptKey);

    this.decryptCipher = Cipher.getInstance(transform);
    decryptCipher.init(Cipher.DECRYPT_MODE, decryptKey);
  }

  public byte[] encrypt(final byte[] plainBytes) throws GeneralSecurityException {
    // simple data.
    // return encryptCipher.doFinal(plainBytes);
    // long data.
    int blockSize = encryptCipher.getBlockSize();
    int outputSize = encryptCipher.getOutputSize(plainBytes.length);

    int leavedSize = plainBytes.length % blockSize;
    int blocks = leavedSize != 0 ? plainBytes.length / blockSize + 1 : plainBytes.length / blockSize;
    byte[] raw = new byte[outputSize * blocks];
    int i = 0;
    while (plainBytes.length - i * blockSize > 0) {
      if (plainBytes.length - i * blockSize > blockSize) {
        encryptCipher.doFinal(plainBytes, i * blockSize, blockSize, raw, i * outputSize);
      } else {
        encryptCipher.doFinal(plainBytes, i * blockSize, plainBytes.length - i * blockSize, raw, i * outputSize);
      }
      i++;
    }
    return raw;
  }

  public byte[] decrypt(final byte[] encryptedBytes) throws GeneralSecurityException {
    // simple data
    //return decryptCipher.doFinal(encryptedBytes);
    // long data.
    byte[] result;
    int blockSize = decryptCipher.getBlockSize();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      int i = 0;
      while (encryptedBytes.length - i * blockSize > 0) {
        outputStream.write(decryptCipher.doFinal(encryptedBytes, i * blockSize, blockSize));
        i++;
      }
      outputStream.flush();
      result = outputStream.toByteArray();
    } catch (IOException e) {
      throw new IllegalStateException(e);
    } finally {
      try {
        outputStream.close();
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
    }
    return result;
  }

  public static SecretKey generateKey(final String algorithm, final String seed) {
    KeyGenerator generator;
    try {
      generator = KeyGenerator.getInstance(algorithm);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
    // init.
    if (seed != null && seed.length() > 0) {
      SecureRandom random = new SecureRandom(Charsets.getBytesUtf8(seed));
      // size: DES=56, AES=128
      generator.init(random);
    }
    return generator.generateKey();
  }
}
