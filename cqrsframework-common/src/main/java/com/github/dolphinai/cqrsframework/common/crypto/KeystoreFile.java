package com.github.dolphinai.cqrsframework.common.crypto;

import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.Objects;

/**
 *
 */
public final class KeystoreFile {

  private final KeyStore keystore;
  private String defaultAlias;

  public KeystoreFile(final String storeType, final InputStream keystoreStream, final String keystorePassword) throws GeneralSecurityException {
    Objects.requireNonNull(keystoreStream);
    Objects.requireNonNull(keystorePassword);

    keystore = KeyStore.getInstance(storeType);
    try {
      keystore.load(keystoreStream, keystorePassword.toCharArray());
    } catch (IOException e) {
      throw new GeneralSecurityException(e);
    }
    // alias
    if(keystore.aliases().hasMoreElements()) {
      this.defaultAlias = keystore.aliases().nextElement();
    }
  }

  public String getDefaultAlias() {
    return defaultAlias;
  }

  @SneakyThrows
  public Enumeration<String> getAliases() {
    return this.keystore.aliases();
  }

  public Key getKey(final String alias, final String keyPassword) throws GeneralSecurityException {
    Objects.requireNonNull(keyPassword);
    Objects.requireNonNull(keystore);
    return keystore.getKey(alias, keyPassword.toCharArray());
  }

  public KeyPair getKeyPair(final String alias, final String keyPassword) throws GeneralSecurityException {
    PrivateKey privateKey = (PrivateKey) getKey(alias, keyPassword);
    return new KeyPair(getCertificate(alias).getPublicKey(), privateKey);
  }

  public Certificate getCertificate(final String alias)throws GeneralSecurityException {
    return keystore.getCertificate(alias);
  }
}
