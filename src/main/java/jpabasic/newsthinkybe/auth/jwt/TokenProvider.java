package jpabasic.newsthinkybe.auth.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import jpabasic.newsthinkybe.auth.security.CustomUserDetails;
import jpabasic.newsthinkybe.user.domain.RefreshToken;
import jpabasic.newsthinkybe.user.domain.user.KakaoUserInfo;
import jpabasic.newsthinkybe.user.dto.TokenDto;
import jpabasic.newsthinkybe.global.exception.CustomException;
import jpabasic.newsthinkybe.user.domain.user.User;
import jpabasic.newsthinkybe.user.repository.RefreshTokenRepository;
import jpabasic.newsthinkybe.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static jpabasic.newsthinkybe.global.exception.ErrorCode.*;

@Component
public class TokenProvider {


    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${jwt.key}")
    private String key;
    private SecretKey secretKey;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 60L; //1뷴
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7;
    private static final String KEY_ROLE="role";
    private final UserRepository userRepository;

    public TokenProvider(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @PostConstruct
    private void setSecretKey() {
        secretKey= Keys.hmacShaKeyFor(key.getBytes());
    }

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication,ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String generateRefreshToken(Authentication authentication) {
        String refreshToken=generateToken(authentication,REFRESH_TOKEN_EXPIRE_TIME);

        OAuth2User oAuth2User=(OAuth2User) authentication.getPrincipal();
        KakaoUserInfo kakaoUserInfo=new KakaoUserInfo(oAuth2User.getAttributes());

        User user=userRepository.findByEmail(kakaoUserInfo.getEmail())
                        .orElseThrow(()->new CustomException(USER_NOT_FOUND));

        saveRefreshTokenOnRedis(user,refreshToken);
        return refreshToken;

    }

    public void saveRefreshTokenOnRedis(User user, String refreshToken) {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities=new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(user.getUserRole().name()));
        refreshTokenRepository.save(RefreshToken.builder()
                .id(user.getEmail())
                .authorities(simpleGrantedAuthorities)
                .refreshToken(refreshToken)
                .build());
    }

    public String generateToken(Authentication authentication, long expireTime) {
        Date now=new Date();
        Date expiredDate=new Date(now.getTime()+expireTime);

        String authorities=authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());


        return Jwts.builder()
                .subject(authentication.getName())
                .claim(KEY_ROLE,authorities)
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(secretKey,Jwts.SIG.HS256)
                .compact();
    }

    //token으로부터 Authentication 객체를 만들어 리턴하는 메서드
    public Authentication getAuthentication(String token) {
        Claims claims=parseClaims(token);
        List<SimpleGrantedAuthority> authorities=getAuthorities(claims);

        String email = claims.getSubject();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), token, authorities);
    }

    private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        return Collections.singletonList(new SimpleGrantedAuthority(claims.get(KEY_ROLE).toString()));
    }

    //access token 재발급
//    public String reissueAccessToken(String accessToken){
//        if(StringUtils.hasText(accessToken)){
//            Token token=tokenService.findByAccessTokenOrThrow(accessToken);
//            String refreshToken=token.getRefreshToken();
//
//            if(validateToken(refreshToken)){
//                String reissueAccessToken=generateAccessToken(getAuthentication(refreshToken));
//                tokenService.updateToken(reissueAccessToken,token);
//                return reissueAccessToken;
//            }
//        }
//        return null;
//    }

    @Transactional
    public TokenDto reIssueAccessToken(String refreshToken){
        // refreshToken 파싱 -> email 추출
        Claims claims=parseClaims(refreshToken);
        String email=claims.getSubject();

        //Redis에서 저장된 refreshToken 조회
        RefreshToken stored=refreshTokenRepository.findById(email)
                .orElseThrow(()->new CustomException(INVALID_REFRESH_TOKEN));

        // Redis 값과 요청 refreshToken 비교
        if(!stored.equals(refreshToken)){
            throw new CustomException(INVALID_REFRESH_TOKEN);
        }

        //새 access token 발급
        Authentication authentication=getAuthentication(refreshToken);
        String newAT=generateAccessToken(authentication);

        return new TokenDto(newAT,refreshToken);
    }

    public boolean validateToken(String token) {
        if(!StringUtils.hasText(token)){
            return false;
        }

        Claims claims = parseClaims(token);
        return claims.getExpiration().after(new Date());
    }

    private Claims parseClaims(String token) {
        try{
            return Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token).getPayload();
        }catch(ExpiredJwtException e){
            return e.getClaims();
        }catch(MalformedJwtException e){
            throw new CustomException(INVALID_TOKEN);
        }catch(SecurityException e){
            throw new CustomException(INVALID_JWT_SIGNATURE);
        }
    }
}
