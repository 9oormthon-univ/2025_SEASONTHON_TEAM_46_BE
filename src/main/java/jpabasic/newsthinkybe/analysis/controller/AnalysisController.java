package jpabasic.newsthinkybe.analysis.controller;

import io.swagger.v3.oas.annotations.Operation;
import jpabasic.newsthinkybe.analysis.dto.NegativeRateDto;
import jpabasic.newsthinkybe.analysis.service.GptService;
import jpabasic.newsthinkybe.analysis.service.NewsAnalysisService;
import jpabasic.newsthinkybe.auth.security.CustomUserDetails;
import jpabasic.newsthinkybe.news.domain.Emotion;
import jpabasic.newsthinkybe.news.domain.NewsCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/analysis")
public class AnalysisController {

    private final NewsAnalysisService newsAnalysisService;
    private final GptService gptService;

    @GetMapping("/sentiment")
    @Operation(summary = "GPT 소비 분석",
               description = "사용자가 본 뉴스의 카테고리와 감정 분석을 바탕으로 요약된 분석 결과를 반환합니다.")
    public ResponseEntity<String> analyzeUserNews(@AuthenticationPrincipal CustomUserDetails user) {
        List<String> category = newsAnalysisService.getUserViewedNewsCategory(user.getUserId());
        List<String> sentiments = newsAnalysisService.getUserViewedNewsSentiment(user.getUserId());

        if (category.isEmpty() || sentiments.isEmpty()) {
            return ResponseEntity.ok("아직 본 뉴스가 없습니다.");
        }

        List<Map<String, String>> contents = category.stream()
                .map(cat -> Map.of("category", cat, "sentiment", sentiments.get(category.indexOf(cat))))
                .toList();

        String summary = gptService.summarizeSentimentFromCategory(contents);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/category")
    @Operation(summary = "카테고리별 뉴스 소비 분석",
               description = "사용자가 본 뉴스의 카테고리 별 수치를 반환합니다.")
    public ResponseEntity<Map<NewsCategory, Long>> analyzeUserNewsByCategory(@AuthenticationPrincipal CustomUserDetails user) {
        Map<NewsCategory, Long> categoryCount = newsAnalysisService.countUserViewedNewsByCategory(user.getUserId());
        return ResponseEntity.ok(categoryCount);
    }


    @GetMapping("/emotion")
    @Operation(summary = "감정별 뉴스 소비 분석",
               description = "사용자가 본 뉴스의 감정 별 수치를 반환합니다.")
    public ResponseEntity<Map<Emotion, Long>> analyzeUserNewsByEmotion(@AuthenticationPrincipal CustomUserDetails user) {
        Map<Emotion, Long> emotionCount = newsAnalysisService.countUserViewedNewsByEmotion(user.getUserId());
        return ResponseEntity.ok(emotionCount);
    }

    @GetMapping("/caution")
    @Operation(summary = "편항 감정 소비 경고",
               description = "분노/비판 뉴스 소비가 전체 뉴스 소비의 65%를 넘을 경우 경고 메시지를 반환합니다.")
    public ResponseEntity<NegativeRateDto> analyzeUserNewsForCaution(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(newsAnalysisService.NewsForCaution(user.getUserId()));
    }

}