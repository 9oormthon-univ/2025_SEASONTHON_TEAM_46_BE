package jpabasic.newsthinkybe.user.controller.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jpabasic.newsthinkybe.auth.security.CustomUserDetails;
import jpabasic.newsthinkybe.user.dto.TokenDto;
import jpabasic.newsthinkybe.user.dto.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    @GetMapping("/auth/login")
    public ResponseEntity loginKakao(@RequestParam(name="accessToken") String accessToken,
                                     @RequestParam(name="refreshToken") String refreshToken) {
        TokenDto tokenDto=new TokenDto(accessToken, refreshToken);
        return ResponseEntity.ok(tokenDto);
    }

    @GetMapping("/api/sign/reissue")
    public ResponseEntity reissue(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String accessToken = (String) session.getAttribute("accessToken");
        String refreshToken = (String) session.getAttribute("refreshToken");

        return new ResponseEntity(new TokenDto(accessToken, refreshToken), HttpStatus.OK);
    }

    @GetMapping("/user/info")
    @Operation(summary="유저 정보 가져오기")
    public ResponseEntity<UserInfoDto> info(@AuthenticationPrincipal CustomUserDetails user) {
        UserInfoDto dto=new UserInfoDto(user.getUsername(),user.getProfileUrl());
        return ResponseEntity.ok(dto);
    }

}
