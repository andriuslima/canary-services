package xyz.andriuslima.canaryapi.api.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserClaims {
    private Integer userId;
}
