package com.github.dolphinai.cqrsframework.common.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.util.function.Supplier;

public interface SecureCipher {

  String encrypt(final String plain);

  byte[] encrypt(byte[] plain) throws GeneralSecurityException;

  String decrypt(final String encryptedData);

  byte[] decrypt(byte[] encryptedData) throws GeneralSecurityException;

  static SecureCipher of (final Supplier<Cipher> encryptCipher, final Supplier<Cipher> decryptCipher) {
    return new SecureCipherImpl(encryptCipher, decryptCipher);
  }

  static SecureCipher of(final String algorithm, final SecretKeySpec keySpec, final IvParameterSpec ivParameters) {
    return of(
      () -> CipherUtils.encryptCipher(algorithm, keySpec, ivParameters),
      () -> CipherUtils.decryptCipher(algorithm, keySpec, ivParameters));
  }
}
