package jpabasic.newsthinkybe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/auth/failure")
    public ResponseEntity<String> authFailure() {
        return ResponseEntity.ok("로그인 실패.");
    }
}
