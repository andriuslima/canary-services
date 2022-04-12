package xyz.andriuslima.canaryviewapi.utils;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;
import static java.lang.String.format;

import com.auth0.jwt.JWT;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import java.util.Date;

public class RequestSpecs {

  private static final String secret = "BD2A68BD1F319CB3C3D698F66D651";

  public static RequestSpecification jwt(Integer userId) {
    return new RequestSpecBuilder()
        .addHeader("Accept", "application/json")
        .addHeader("Content-Type", "application/json")
        .addHeader("Authorization", bearerJwt(userId))
        .build();
  }

  public static String token(Integer userId) {
    return JWT
        .create()
        .withIssuedAt(new Date())
        .withClaim("userId", userId)
        .sign(HMAC256(secret));
  }

  private static String bearerJwt(Integer userId) {
    return format("Bearer %s", token(userId));
  }
}
