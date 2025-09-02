package jpabasic.newsthinkybe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    private String nickname;
    private String profileUrl;
}
