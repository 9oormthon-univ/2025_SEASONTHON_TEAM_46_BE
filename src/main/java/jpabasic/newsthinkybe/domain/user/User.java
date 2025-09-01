package jpabasic.newsthinkybe.domain.user;

import jakarta.persistence.*;
import jpabasic.newsthinkybe.domain.BaseEntity;
import lombok.*;

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


}
