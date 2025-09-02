package jpabasic.newsthinkybe.auth.security;

import jakarta.transaction.Transactional;
import jpabasic.newsthinkybe.domain.user.KakaoUserInfo;
import jpabasic.newsthinkybe.domain.user.User;
import jpabasic.newsthinkybe.domain.user.UserRole;
import jpabasic.newsthinkybe.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PrincipalOauth2UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("✅ loadUser 호출");

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("✅oAuth2User:"+oAuth2User);
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(oAuth2User.getAttributes());


        User user = userRepository.findByEmail(kakaoUserInfo.getEmail())
                .orElseGet(() ->
                        userRepository.save(
                                User.builder()
                                        .userRole(UserRole.USER)
                                        .email(kakaoUserInfo.getEmail())
                                        .nickname(kakaoUserInfo.getNickname())
                                        .profileUrl(kakaoUserInfo.getProfileUrl())
                                        .provider(kakaoUserInfo.getProvider())
                                        .providerId(kakaoUserInfo.getProviderId())
                                        .password(passwordEncoder.encode("newspassword"))
                                        .build()

                ));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getUserRole().name());

        return new KakaoMemberDetails(String.valueOf(user.getEmail()),
                Collections.singletonList(authority),
                oAuth2User.getAttributes());
    }

}
