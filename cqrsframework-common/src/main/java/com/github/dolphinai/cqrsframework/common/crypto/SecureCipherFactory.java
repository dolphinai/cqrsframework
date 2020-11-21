package com.github.dolphinai.cqrsframework.common.crypto;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
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

  public static SecureCipher tripleDESCipher(final String base64Key, final String base64Iv) {
    final SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(base64Key), "DESede");
    final IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(base64Iv));
    return SecureCipher.of("DESede/CBC/PKCS5Padding", keySpec, ivSpec);
  }

  public static SecureCipher aesCipher(final String base64Key, final String base64Iv) throws GeneralSecurityException {
    // requires BouncyCastleProvider
    final SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(base64Key), "AES");
    final IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(base64Iv));
    return SecureCipher.of("AES/CBC/PKCS7Padding", keySpec, ivSpec);
  }

  public static SecureCipher rsaCipher(final KeyPair rsaKeyPair) {
    return SecureCipher.of(
      () -> CipherUtils.encryptCipher("RSA", rsaKeyPair.getPrivate(), null),
      () -> CipherUtils.decryptCipher("RSA", rsaKeyPair.getPublic(), null));
  }

  public static SecureCipher rsaCipher(final byte[] publicKeyBytes, final byte[] privateKeyBytes) throws GeneralSecurityException {
    // 私钥加密，公钥解密
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    // public key
    PublicKey publicKey = null;
    if (publicKeyBytes != null) {
      publicKey = keyFactory.generatePublic( new X509EncodedKeySpec(publicKeyBytes));
    }
    // private key
    PrivateKey privateKey = null;
    if (privateKeyBytes != null) {
      privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
    }
    return rsaCipher(new KeyPair(publicKey, privateKey));
  }

  public static void main(String[] args) throws GeneralSecurityException {

    SecureCipher des = SecureCipherFactory.desCipher("qIYsFYaoFWI=", "MTIzNDU2Nzg=");
    String s2 = des.encrypt("hello");
    System.out.println(s2);
    System.out.println(des.decrypt(s2));

    KeyGenerator generator = KeyGenerator.getInstance("DESede");
    System.out.println(Base64.getEncoder().encodeToString(generator.generateKey().getEncoded()));
    KeyPairGenerator keyPairGen = null;
    try {
      keyPairGen = KeyPairGenerator.getInstance("RSA");
    } catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    SecureCipher rsa = SecureCipherFactory.rsaCipher(
      Base64.getDecoder().decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwx/65SjwHPJpx+nn7IToTk82MRwXSdNUXpDVmLnw4Mijb+5KNPY1AvX6chfbBPcZf/7qJZMEdAr9XH+lFwiIealJ5h0oiTVpfnquMdnYFVqji+oXWRBbt7oGO2K78UwzIaskh8d/xnBWCKfwE4K+IDo7JHquSb6kGvCJ6dj1YmQIDAQAB"),
      Base64.getDecoder().decode("MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALDH/rlKPAc8mnH6efshOhOTzYxHBdJ01RekNWYufDgyKNv7ko09jUC9fpyF9sE9xl//uolkwR0Cv1cf6UXCIh5qUnmHSiJNWl+eq4x2dgVWqOL6hdZEFu3ugY7YrvxTDMhqySHx3/GcFYIp/ATgr4gOjskeq5JvqQa8Inp2PViZAgMBAAECgYBTPspPXIqp6brcw1Nfk4TypR4c2XD/aq7RxCy1IlyyZcRh2rRnOD3k55bQhDDvTHS2zgh8HGrni1yo3UkO5V0E5reU1hRrJpQLQH8t4JEmaS1efeifu13cJgu/u0wXLu7BRBmZcLL1l7TacWQuDATkrtShPFyIiN2lbBSxLTb31QJBAORPf/gfk7Hlu0NgrG7ZkChvmxiQse+ui9h5QJaVHcUO1JK3IL9YhsHra+CCf5IGG6UGeWmxnXDH2CZbd9QgBUsCQQDGOKHdVJIVPrlnznOQImKHtN/mNku9ljOu49mRg1bvbbq/u7AiNXlyQzhvwqxI+MUmH2JSUqJa3T9kvj+UTT8rAkEAxGV+97pyGr48cKFgs5qD38Th7ifKaRwFQJpNbTuo+DLhubO+f9tvULFrgx2/D6Yhcbw4BSu8+59t2NEmK1vAMQJBAIEDVR64L4hSVdwhfAfAyX5oFB5DEpLnkuXueKoD4O5e7eBi6WWqhkv/QllDTF0GRVLltFm+XNtlCyCnzrvYdxcCQQDPD7lPVgrpQibwoRP86vCI7Nfp8b/9QSXmF5K6vtxyWFbmDxdJPGtJA6G75KX+z+OwBCJw9P94rYL17I2y79HB"));

    String s5 = rsa.encrypt("test");
    System.out.println(s5);
    System.out.println(rsa.decrypt(s5));
  }
}
