package jpabasic.newsthinkybe.auth.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final jpabasic.newsthinkybe.domain.user.User user;

    public CustomUserDetails(jpabasic.newsthinkybe.domain.user.User user) {
        this.user = user;
    }

    public String getProfileUrl(){
        return user.getProfileUrl();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return user.getNickname();
    }
}
