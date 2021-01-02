package com.github.dolphinai.cqrsframework.web.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.dolphinai.cqrsframework.common.exception.TokenTimeOutException;
import com.github.dolphinai.cqrsframework.common.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.function.Consumer;

/**
 */
public final class JwtEncoderImpl implements JwtEncoder {

  private static final Logger log = LoggerFactory.getLogger(JwtEncoder.class);
  private static final String KEY_MAP = "map";
  private final Algorithm algorithm;
  private final String issuer;

  public JwtEncoderImpl(final String issuer, final Algorithm algorithm) {
    this.issuer = issuer;
    this.algorithm = algorithm;
  }

  public JwtEncoderImpl(final String issuer, final RSAPublicKey publicKey, final RSAPrivateKey privateKey) {
    this(issuer, Algorithm.RSA256(publicKey, privateKey));
  }

  @Override
  public String encode(final String subject, final Date issueDate, final Date expiryDate) {
    Objects.requireNonNull(subject);
    return encode(issueDate, expiryDate, builder -> builder.withJWTId(subject));
  }

  @Override
  public String encodeMap(final Map<String, Object> claims, final Date issueDate, final Date expiryDate) {
    Objects.requireNonNull(claims);
    return encode(issueDate, expiryDate, builder -> builder.withClaim(KEY_MAP, claims));
  }

  protected String encode(final Date issueDate, final Date expiryDate, final Consumer<JWTCreator.Builder> builderHandler) {
    if(log.isDebugEnabled()) {
      log.debug("Jwt data, issue={}, expiry={}", issueDate, expiryDate);
    }
    JWTCreator.Builder builder = JWT.create()
      .withIssuer(issuer)
      .withIssuedAt(issueDate)
      .withNotBefore(issueDate)
      .withExpiresAt(expiryDate);

    builderHandler.accept(builder);
    String result = null;
    try {
      result = builder.sign(this.algorithm);
    } catch (Exception e) {
      log.error("Failed to encode JWT", e);
    }
    return result;
  }

  @Override
  public String decode(final String jwt) {
    DecodedJWT decodedJWT = decodeJWT(jwt);
    if (decodedJWT != null) {
      return decodedJWT.getId();
    }
    return null;
  }

  @Override
  public Map<String, Object> decodeMap(final String jwt) {
    DecodedJWT decodedJWT = decodeJWT(jwt);
    if (decodedJWT == null || decodedJWT.getClaim(KEY_MAP) == null) {
      return Collections.emptyMap();
    }
    return decodedJWT.getClaim(KEY_MAP).asMap();
  }

  @Override
  public Date getExpiresAt(final String jwt) {
    DecodedJWT decodedJWT = decodeJWTWithoutChecking(jwt);
    if (decodedJWT != null) {
      return decodedJWT.getExpiresAt();
    }
    return null;
  }

  protected DecodedJWT decodeJWTWithoutChecking(final String jwt) {
    if (StringHelper.isEmpty(jwt)) {
      return null;
    }
    return JWT.decode(jwt);
  }

  protected DecodedJWT decodeJWT(final String jwt) throws TokenTimeOutException {
    if (StringHelper.isEmpty(jwt)) {
      return null;
    }
    JWTVerifier verifier = JWT.require(algorithm)
      .withIssuer(this.issuer)
      .acceptLeeway(5L)
      .build();
    DecodedJWT decodedJWT;
    try {
      decodedJWT = verifier.verify(jwt);
    } catch (TokenExpiredException e) {
      throw new TokenTimeOutException(e);
    } catch (Exception e) {
      throw new TokenTimeOutException(e);
    }
    return decodedJWT;
  }
}
