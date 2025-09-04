package jpabasic.newsthinkybe.news.controller;

import io.swagger.v3.oas.annotations.Operation;
import jpabasic.newsthinkybe.news.domain.News;
import jpabasic.newsthinkybe.news.dto.NewsBodyDto;
import jpabasic.newsthinkybe.news.dto.NewsResponseDto;
import jpabasic.newsthinkybe.news.service.NewsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "뉴스 하나 조회", description = "Retrieve a single news item by its ID")
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

    @GetMapping("/{id}/recommandation/opposing-emotion")
    @Operation(summary = "반대 감정 뉴스 3개 추천", description = "Retrieve news recommendations with opposing emotions")
    public ResponseEntity<List<NewsResponseDto>> getOpposingEmotionRecommendations(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getOpposingEmotionRecommendations(id));
    }
}
