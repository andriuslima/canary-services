package xyz.andriuslima.canaryapi.api.serverlet;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.andriuslima.canaryapi.api.auth.UserInfoResolver;

import java.util.List;

@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class ServletConfiguration implements WebMvcConfigurer {

    private final UserInfoResolver userInfoResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userInfoResolver);
    }
}
