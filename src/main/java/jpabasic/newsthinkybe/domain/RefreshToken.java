package jpabasic.newsthinkybe.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Indexed;

import java.util.Collection;
import java.util.stream.Collectors;

@RedisHash(value="refresh",timeToLive = 604800)
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RefreshToken {

    @Id
    private String id;
    private Collection<? extends GrantedAuthority> authorities;

    private String refreshToken;

    public String getAuthority(){
        return authorities.stream()
                .map(authority->new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList())
                .get(0)
                .getAuthority();
    }
}
