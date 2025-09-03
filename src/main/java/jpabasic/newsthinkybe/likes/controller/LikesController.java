package jpabasic.newsthinkybe.likes.controller;

import io.swagger.v3.oas.annotations.Operation;
import jpabasic.newsthinkybe.auth.security.CustomUserDetails;
import jpabasic.newsthinkybe.likes.service.LikesService;
import jpabasic.newsthinkybe.user.domain.user.User;
import org.aspectj.bridge.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LikesController {

    private final LikesService likesService;

    public LikesController(final LikesService likesService) {
        this.likesService = likesService;
    }

    @PostMapping("/likes")
    @Operation(summary = "특정 기사에 좋아요 누르기")
    public ResponseEntity<Map<String,Integer>> giveLikes(@RequestParam Long newsId, @AuthenticationPrincipal CustomUserDetails user){
        Long userId=user.getUserId();
        Integer likeCount=likesService.giveLikes(newsId,userId);

        Map<String,Integer> result=new HashMap<>();
        result.put("likeCount",likeCount);

        return ResponseEntity.ok(result);
    }


}
