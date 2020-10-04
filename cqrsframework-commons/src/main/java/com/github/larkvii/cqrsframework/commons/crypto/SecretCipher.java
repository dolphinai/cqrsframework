package com.github.larkvii.cqrsframework.commons.crypto;

import com.github.larkvii.cqrsframework.commons.util.Charsets;

import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Objects;

public interface SecretCipher {

    default String encrypt(final String plainText) {
      Objects.requireNonNull(plainText);
      byte[] encoded;
      try {
        encoded = encrypt(Charsets.getBytesUtf8(plainText));
      } catch (GeneralSecurityException e) {
        throw new IllegalStateException(e);
      }
      return Base64.getEncoder().encodeToString(encoded);
    }

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
      return Charsets.getStringUtf8(data);
    }

    byte[] decrypt(byte[] encryptedBytes) throws GeneralSecurityException;
}
