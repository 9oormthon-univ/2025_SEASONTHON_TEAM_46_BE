package jpabasic.newsthinkybe.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpabasic.newsthinkybe.auth.jwt.JwtFilter;
import jpabasic.newsthinkybe.auth.security.OAuth2SuccessHandler;
import jpabasic.newsthinkybe.auth.security.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final AuthenticationEntryPoint jwtAuthenticationFailEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, PrincipalOauth2UserService principalOauth2UserService, JwtFilter jwtFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //csrf 보호 기능 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling->{
                    exceptionHandling.authenticationEntryPoint(jwtAuthenticationFailEntryPoint); //인증 실패
                })
                .authorizeHttpRequests(authorizeRequest->
                        authorizeRequest.requestMatchers("/auth/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/h2-console/**",
                                        "/favicon.ico",
                                        "/error",
                                        "/api/news/**"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(handler->handler.authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
                    }
                }))
                .headers(
                        headConfigurer->headConfigurer.frameOptions(
                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                        )
                );
        http.oauth2Login(oauth2Configurer->oauth2Configurer
                .userInfoEndpoint(userInfo->userInfo.userService(principalOauth2UserService))
                .successHandler(oAuth2SuccessHandler)
                .failureHandler((request,response,exception)->{
                    response.sendRedirect("/auth/failure");
                })
        );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
