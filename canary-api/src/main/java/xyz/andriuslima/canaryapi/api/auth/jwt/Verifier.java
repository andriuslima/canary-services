package xyz.andriuslima.canaryapi.api.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Verifier {

    @Value("${auth.jwt.hmac256.secret}")
    private String secret;

    public DecodedJWT verify(String token) {
        return JWT
                .require(algorithm())
                .acceptLeeway(2)
                .build()
                .verify(token);
    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }

}
