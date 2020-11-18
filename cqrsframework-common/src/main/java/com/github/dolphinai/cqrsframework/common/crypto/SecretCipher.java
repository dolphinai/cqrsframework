package com.github.dolphinai.cqrsframework.common.crypto;

import com.github.dolphinai.cqrsframework.common.util.StringHelper;

import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Objects;

public interface SecretCipher {

  /**
   * encrypt the plain text.
   *
   * @param plainText original plain text
   * @return encrypted text
   */
  default String encrypt(final String plainText) {
    Objects.requireNonNull(plainText);
    byte[] encoded;
    try {
      encoded = encrypt(StringHelper.getBytesUtf8(plainText));
    } catch (GeneralSecurityException e) {
      throw new IllegalStateException(e);
    }
    return Base64.getEncoder().encodeToString(encoded);
  }

  /**
   * encrypt the plain byte array.
   *
   * @param plainBytes  The original plain byte array
   * @return  The encrypted byte array
   * @throws GeneralSecurityException exception
   */
  byte[] encrypt(byte[] plainBytes) throws GeneralSecurityException;

  default String decrypt(final String encryptedText) {
    Objects.requireNonNull(encryptedText);
    byte[] encoded = Base64.getDecoder().decode(encryptedText);
    byte[] data;
    try {
      data = decrypt(encoded);
    } catch (GeneralSecurityException e) {
      throw new IllegalStateException(e);
    }
    return StringHelper.getStringUtf8(data);
  }

  byte[] decrypt(byte[] encryptedBytes) throws GeneralSecurityException;
}
