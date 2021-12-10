package org.snakeplanner.security;

import static io.smallrye.jwt.util.KeyUtils.readPrivateKey;

import io.smallrye.jwt.build.Jwt;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.HashSet;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GenerateJWT {
  String privateKeyLocation = "/privateKey.pem";
  PrivateKey privateKey = readPrivateKey(privateKeyLocation);

  public GenerateJWT() throws GeneralSecurityException, IOException {}

  public String generate(String email, int expiration) {
    return Jwt.issuer("https://example.com/issuer")
        .upn(email)
        .expiresIn(expiration)
        .groups(new HashSet<>(Arrays.asList("User", "Admin")))
        .sign(privateKey);
  }
}
