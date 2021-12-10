package org.snakeplanner.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HashService {
  public String getHashedValue(String data, String salt) {
    byte[] hashedValue = null;

    KeySpec spec = new PBEKeySpec(data.toCharArray(), salt.getBytes(), 5000, 128);
    try {
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      hashedValue = factory.generateSecret(spec).getEncoded();
    } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
      throw new RuntimeException();
    }

    return Base64.getEncoder().encodeToString(hashedValue);
  }
}
