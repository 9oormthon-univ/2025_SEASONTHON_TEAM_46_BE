package jpabasic.newsthinkybe.view.controller;

import io.swagger.v3.oas.annotations.Operation;
import jpabasic.newsthinkybe.auth.security.CustomUserDetails;
import jpabasic.newsthinkybe.view.dto.NewsViewResponseDto;
import jpabasic.newsthinkybe.view.service.NewsViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news-view")
public class NewsViewController {

    private final NewsViewService newsViewService;

    @PostMapping("/{newsId}")
    @Operation(summary = "Record a news view", description = "Records that a user has viewed a specific news item")
    public ResponseEntity<NewsViewResponseDto> viewNews(@PathVariable Long newsId,
                                                        @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(newsViewService.recordView(user.getUserId(), newsId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<NewsViewResponseDto>> getViewList(
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(newsViewService.getViewList(user.getUserId()));
    }
}
