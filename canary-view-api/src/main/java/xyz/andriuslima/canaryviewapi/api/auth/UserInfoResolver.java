package xyz.andriuslima.canaryviewapi.api.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import xyz.andriuslima.canaryviewapi.api.auth.domain.UserInfo;
import xyz.andriuslima.canaryviewapi.api.auth.jwt.JwtDecoder;
import xyz.andriuslima.canaryviewapi.api.exceptions.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserInfoResolver implements HandlerMethodArgumentResolver {

    private final JwtDecoder decoder;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        var request = (HttpServletRequest) webRequest.getNativeRequest();
        var user = getUserFromJwt(request);
        var path = request.getServletPath();

        if (user.isPresent()) {
            log.debug("[{}] {} {}", user.get().getUserId(), request.getMethod(), path);

            return user.get();
        }

        log.info("{} {}: not authorized", request.getMethod(), path);

        throw new UnauthorizedException("Could not proceed with authorization");
    }

    private Optional<UserInfo> getUserFromJwt(HttpServletRequest request) {
        var header = request.getHeader("Authorization");

        if (isBlank(header)) {
            return empty();
        }

        var claims = decoder.decode(header);
        var userId = claims.getUserId();

        return of(new UserInfo(userId));
    }
}
