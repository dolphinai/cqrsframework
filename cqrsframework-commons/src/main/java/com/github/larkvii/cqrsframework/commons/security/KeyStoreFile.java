package com.github.larkvii.cqrsframework.commons.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Objects;

/**
 *
 */
public final class KeyStoreFile {

  private static KeyStore keystore;

  public KeyStoreFile(final String storeType, final InputStream keystoreStream, final String keystorePassword) throws GeneralSecurityException {
    Objects.requireNonNull(keystoreStream);
    Objects.requireNonNull(keystorePassword);

    keystore = KeyStore.getInstance(storeType);
    try {
      keystore.load(keystoreStream, keystorePassword.toCharArray());
    } catch (IOException e) {
      throw new GeneralSecurityException(e);
    }
  }

  public Key getKey(final String alias, final String keyPassword) throws GeneralSecurityException {
    Objects.requireNonNull(keyPassword);
    Objects.requireNonNull(keystore);
    return keystore.getKey(alias, keyPassword.toCharArray());
  }

  public static PublicKey getPublicKey(final InputStream inputStream) throws CertificateException {
    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    Certificate crt = cf.generateCertificate(inputStream);
    return crt.getPublicKey();
  }
}
