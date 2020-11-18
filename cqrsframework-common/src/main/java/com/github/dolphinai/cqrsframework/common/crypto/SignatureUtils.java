package com.github.dolphinai.cqrsframework.common.crypto;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Signature Utility.
 */
public final class SignatureUtils {

  private SignatureUtils() {}

  /**
   * Sign the original byte array with Private Key.
   * @param algorithm     security algorithm
   * @param privateKey    private key
   * @param originalData  original byte array
   * @return  signed byte array
   * @throws GeneralSecurityException exception
   */
  public static byte[] sign(final String algorithm, final Key privateKey, final byte[] originalData) throws GeneralSecurityException {

    // 构造PKCS8EncodedKeySpec对象
    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
    // KEY_ALGORITHM 指定的加密算法
    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

    // 取私钥匙对象
    PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

    // 用私钥对信息生成数字签名
    Signature signature = Signature.getInstance(algorithm);
    signature.initSign(priKey);
    signature.update(originalData);

    return signature.sign();
  }

  /**
   * Verify the signed byte array with public key.
   * @param algorithm     security algorithm
   * @param publicKey     public key
   * @param originalData  original byte array
   * @param signedData    signed byte array
   * @return  signed is matched to original or not
   * @throws GeneralSecurityException exception
   */
  public static boolean verify(final String algorithm, final Key publicKey, final byte[] originalData, final byte[] signedData) throws GeneralSecurityException {

    // 构造X509EncodedKeySpec对象
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey.getEncoded());

    // KEY_ALGORITHM 指定的加密算法
    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

    // 取公钥匙对象
    PublicKey pubKey = keyFactory.generatePublic(keySpec);

    Signature signature = Signature.getInstance(algorithm);
    signature.initVerify(pubKey);
    signature.update(originalData);

    // 验证签名是否正常
    return signature.verify(signedData);
  }
}
