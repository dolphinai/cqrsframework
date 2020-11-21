package com.github.dolphinai.cqrsframework.common.crypto;

import com.github.dolphinai.cqrsframework.common.util.StringHelper;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;
import java.util.Objects;
import java.util.function.Supplier;

final class SecureCipherImpl implements SecureCipher {

  private final Supplier<Cipher> encryptCipher;
  private final Supplier<Cipher> decryptCipher;

  public SecureCipherImpl(final Supplier<Cipher> encryptInstance, final Supplier<Cipher> decryptInstance) {
    this.encryptCipher = encryptInstance;
    this.decryptCipher = decryptInstance;
  }

  @Override
  public String encrypt(final String plain) {
    Objects.requireNonNull(plain);
    byte[] encoded;
    try {
      encoded = encrypt(StringHelper.getBytesUtf8(plain));
    } catch (GeneralSecurityException e) {
      throw new IllegalStateException(e);
    }
    return Base64.getEncoder().encodeToString(encoded);
  }

  public byte[] encrypt(final byte[] plainBytes) throws GeneralSecurityException {
    // simple data.
    return encryptCipher.get().doFinal(plainBytes);
  }

  @Override
  public String decrypt(final String encryptedData) {
    Objects.requireNonNull(encryptedData);
    byte[] encoded = Base64.getDecoder().decode(encryptedData);
    byte[] data;
    try {
      data = decrypt(encoded);
    } catch (GeneralSecurityException e) {
      throw new IllegalStateException(e);
    }
    return StringHelper.getStringUtf8(data);
  }

  public byte[] decrypt(final byte[] encryptedBytes) throws GeneralSecurityException {
    // simple data
    return decryptCipher.get().doFinal(encryptedBytes);
  }
}
