package jpabasic.newsthinkybe.news.controller;

import io.swagger.v3.oas.annotations.Operation;
import jdk.jfr.Category;
import jpabasic.newsthinkybe.auth.security.CustomUserDetails;
import jpabasic.newsthinkybe.news.domain.Emotion;
import jpabasic.newsthinkybe.news.domain.News;
import jpabasic.newsthinkybe.news.dto.NewsBodyDto;
import jpabasic.newsthinkybe.news.dto.NewsResponseDto;
import jpabasic.newsthinkybe.news.service.NewsService;
import jpabasic.newsthinkybe.view.dto.PagedResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    // 뉴스 등록
//    @PostMapping
//    public ResponseEntity<News> createNews(@RequestBody News news) {
//        return ResponseEntity.ok(newsService.createNews(news));
//    }

    // 뉴스 단건 조회
    @GetMapping("/{id}")
    @Operation(summary = "뉴스 하나 조회 (뉴스 본문은 따로 조회해야합니다 !)", description = "Retrieve a single news item by its ID")
    public ResponseEntity<NewsResponseDto> getNews(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getNews(id));
    }

    // 뉴스 전체 조회
    @GetMapping("/all")
    @Operation(summary = "뉴스 전체 조회", description = "Retrieve a list of all news items")
    public ResponseEntity<List<NewsResponseDto>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

//    // 뉴스 수정
//    @PutMapping("/{id}")
//    public ResponseEntity<NewsResponseDto> updateNews(@PathVariable Long id, @RequestBody News news) {
//        return ResponseEntity.ok(newsService.updateNews(id, news));
//    }

//    // 뉴스 삭제
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
//        newsService.deleteNews(id);
//        return ResponseEntity.noContent().build();
//    }

    @GetMapping("/{id}/body")
    @Operation(summary = "뉴스 본문 조회", description = "Retrieve the body of a news item by its ID")
    public ResponseEntity<NewsBodyDto> getNewsBody(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getNewsBody(id));
    }

    @GetMapping("/{id}/recommendation/opposing-emotion")
    @Operation(summary = "반대 감정 뉴스 2개 추천", description = "Retrieve news recommendations with opposing emotions")
    public ResponseEntity<List<NewsResponseDto>> getOpposingEmotionRecommendations(@PathVariable Long id,
                                                                                   @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(newsService.getOpposingEmotionRecommendations(id, user.getUserId()));
    }

    @GetMapping("/{id}/recommendation/opposing-category")
    @Operation(summary = "다른 카테고리 뉴스 2개 추천", description = "현재 기사와 다른 카테고리 뉴스 추천 (랜덤)")
    public ResponseEntity<List<NewsResponseDto>> getOpposingCategoryRecommendations(@PathVariable Long id,
                                                                                    @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(newsService.getOpposingCategoryRecommendations(id, user.getUserId()));
    }

    @GetMapping("/category")
    @Operation(summary = "선택한 카테고리 뉴스 조회 (페이징)")
    public ResponseEntity<PagedResponse<NewsResponseDto>> recommendByCategory(
            @RequestParam Category category,
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(newsService.recommendByCategory(category, user.getUserId(), pageable));
    }

    @GetMapping("/emotion")
    @Operation(summary = "선택한 감정 뉴스 조회 (페이징)")
    public ResponseEntity<PagedResponse<NewsResponseDto>> recommendByEmotion(
            @RequestParam Emotion emotion,
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(newsService.recommendByEmotion(emotion, user.getUserId(), pageable));
    }
}
