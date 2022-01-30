package xyz.andriuslima.canaryapi.api.auth.jwt;

import com.auth0.jwt.interfaces.Claim;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.andriuslima.canaryapi.api.exceptions.UnauthorizedException;

import static java.util.Optional.ofNullable;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtDecoder {

    private final Verifier jwt;

    public UserClaims decode(String header) {
        var token = bearerReader(header);
        log.trace(header);
        return claims(token);
    }

    public UserClaims claims(String token) {
        var decoded = jwt.verify(token);

        var userId = ofNullable(decoded.getClaim("userId"))
                .map(Claim::asInt)
                .orElseThrow(() -> new UnauthorizedException(
                        "Given JWT claims are invalid: missing required user ID"));

        log.debug("User authenticated: {}", userId);

        return new UserClaims(userId);
    }

    private String bearerReader(String header) {
        return ofNullable(header)
                .filter(h -> h.startsWith("Bearer "))
                .map(h -> h.substring(7))
                .orElseThrow(() -> new UnauthorizedException("Could not find JWT"));
    }
}

