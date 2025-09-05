package jpabasic.newsthinkybe.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpabasic.newsthinkybe.auth.jwt.TokenProvider;
import jpabasic.newsthinkybe.user.dto.TokenDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private static final String REDIRECT_URI = "http://localhost:5173/login/callback";

    public OAuth2SuccessHandler(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //access Token, refresh Token 발급
        String accessToken=tokenProvider.generateAccessToken(authentication);
        String refreshToken=tokenProvider.generateRefreshToken(authentication);

        //JWT 기반 Authentication 생성
        Authentication jwtAuth=tokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(jwtAuth);

        String targetUrl = UriComponentsBuilder.fromUriString(REDIRECT_URI)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().toUriString();


        //토큰 JSON 응답
//        response.setContentType("application/json;charset=UTF-8");
//        new ObjectMapper().writeValue(response.getWriter(),new TokenDto(accessToken,refreshToken));
        response.sendRedirect(targetUrl);
    }
}
