package com.github.dolphinai.cqrsframework.common.crypto;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.Security;
import java.util.Base64;

/**
 */
public final class SecureCipherFactory {

  private SecureCipherFactory() {}

  public static void addProvider(final Provider provider) {
    Security.addProvider(provider);
  }

  public static SecureCipher desCipher(final String base64Key, final String base64Iv) throws GeneralSecurityException {
    final SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(base64Key), "DES");
    final IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(base64Iv));
    return SecureCipher.of("DES/CBC/PKCS5Padding", keySpec, ivSpec);
  }

  public static SecureCipher aesCipher(final String base64Key, final String base64Iv) throws GeneralSecurityException {
    // requires BouncyCastleProvider
    final SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(base64Key), "AES");
    final IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(base64Iv));
    return SecureCipher.of("AES/CBC/PKCS7Padding", keySpec, ivSpec);
  }

  public static void main(String[] args) throws GeneralSecurityException {

    SecureCipher des = SecureCipherFactory.desCipher("qIYsFYaoFWI=", "MTIzNDU2Nzg=");
    String s2 = des.encrypt("hello");
    System.out.println(s2);
    System.out.println(des.decrypt(s2));
  }
}
