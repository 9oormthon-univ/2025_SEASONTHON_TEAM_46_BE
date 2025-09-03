package jpabasic.newsthinkybe.global;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpabasic.newsthinkybe.auth.security.CustomUserDetails;
import jpabasic.newsthinkybe.user.domain.user.User;
import jpabasic.newsthinkybe.user.domain.user.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class DummyUserFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        User dummyUser = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("테스트유저")
                .profileUrl("https://example.com/profile.png")
                .userRole(UserRole.USER)
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(dummyUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}

