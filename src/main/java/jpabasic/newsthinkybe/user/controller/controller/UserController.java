package jpabasic.newsthinkybe.user.controller.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jpabasic.newsthinkybe.auth.security.CustomUserDetails;
import jpabasic.newsthinkybe.user.domain.user.User;
import jpabasic.newsthinkybe.user.dto.TokenDto;
import jpabasic.newsthinkybe.user.dto.UserInfoDto;
import jpabasic.newsthinkybe.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
        User me=user.getUser();
        UserInfoDto dto=new UserInfoDto(user.getUsername(),user.getProfileUrl(),me.getEmail());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/logout")
    @Operation(summary="로그아웃")
    public void logout(HttpServletRequest request,@AuthenticationPrincipal User user){
        String email=user.getEmail();
        userService.logout(request,email);
    }

}
