package jpabasic.newsthinkybe.likes.controller;

import io.swagger.v3.oas.annotations.Operation;
import jpabasic.newsthinkybe.auth.security.CustomUserDetails;
import jpabasic.newsthinkybe.likes.service.LikesService;
import jpabasic.newsthinkybe.news.dto.NewsListResponseDto;
import jpabasic.newsthinkybe.user.domain.user.User;
import org.aspectj.bridge.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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

    @GetMapping("/top-likes-list")
    @Operation(summary="좋아요 많은 순서대로 나열")
    public ResponseEntity<List<NewsListResponseDto>> giveTopLikes(){
        List<NewsListResponseDto> result=likesService.giveTopLikes();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/my-likes")
    @Operation(summary="내가 좋아요한 기사들 조회")
    public ResponseEntity<List<NewsListResponseDto>> getMyLikes(@AuthenticationPrincipal CustomUserDetails user){
        Long userId=user.getUserId();
        List<NewsListResponseDto> result=likesService.getMyLikes(userId);
        return ResponseEntity.ok(result);
    }


}
