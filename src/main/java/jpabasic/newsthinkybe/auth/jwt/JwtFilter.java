package jpabasic.newsthinkybe.auth.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jpabasic.newsthinkybe.user.dto.TokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private static final String ACCESS_HEADER = "Authorization";
    private static final String REFRESH_HEADER="x-refresh-token";

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        if(isRequestPassURI(request,response,filterChain)){
//            return;
//        }

        String accessToken=getTokenFromHeader(request,ACCESS_HEADER);

        //access token 검사
        try {
            if (tokenProvider.validateToken(accessToken)) {
                SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(accessToken));
            }
        }catch(ExpiredJwtException e){
            //refresh token 꺼내오는 로직
            String refreshToken=getTokenFromHeader(request,REFRESH_HEADER);
            log.info("access token 만료됨, refresh token 로직 실행 필요");
            //access token,refresh token 재발급
            TokenDto tokenDto=tokenProvider.reIssueAccessToken(refreshToken);
            String newAT=tokenDto.getAccessToken();

            SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(newAT));
            tokenDto.setRefreshToken(refreshToken);

            redirectReissueURI(request,response,tokenDto);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request, String header) {
        String bearerToken=request.getHeader(header);
        if(bearerToken!=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    private static void redirectReissueURI(HttpServletRequest request,HttpServletResponse response,TokenDto tokenDto)
        throws IOException{

        HttpSession session=request.getSession();
        session.setAttribute("accessToken",tokenDto.getAccessToken());
        session.setAttribute("refreshToken",tokenDto.getRefreshToken());
        response.sendRedirect("/api/sign/reissue");
    }
}
