package jpabasic.newsthinkybe.user.domain.user;

import jakarta.persistence.*;
import jpabasic.newsthinkybe.global.domain.BaseEntity;
import jpabasic.newsthinkybe.likes.domain.Likes;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String provider;

    private String providerId;

    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private String refreshToken;

    @OneToMany(mappedBy="user",cascade=CascadeType.ALL,orphanRemoval = true)
    private List<Likes> likes=new ArrayList<>();

}
