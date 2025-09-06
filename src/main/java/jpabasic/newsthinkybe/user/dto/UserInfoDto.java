package jpabasic.newsthinkybe.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    private String nickname;
    private String profileUrl;
    private String email;

    private String role="뉴스 편식가";
    
    public UserInfoDto(String nickname, String profileUrl, String email) {
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.email = email;
        this.role = "뉴스 편식가";
    }

}
