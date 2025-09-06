package jpabasic.newsthinkybe.analysis.service;

import jpabasic.newsthinkybe.analysis.dto.NegativeRateDto;
import jpabasic.newsthinkybe.news.domain.Emotion;
import jpabasic.newsthinkybe.news.domain.NewsCategory;
import jpabasic.newsthinkybe.news.domain.Sentiment;
import jpabasic.newsthinkybe.news.dto.SentimentPercentageDto;
import jpabasic.newsthinkybe.news.service.NewsService;
import jpabasic.newsthinkybe.view.domain.NewsView;
import jpabasic.newsthinkybe.view.dto.EmotionCountDto;
import jpabasic.newsthinkybe.view.repository.NewsViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsAnalysisService {

    private final NewsViewRepository newsViewRepository;
    private final NewsService newsService;

    public List<String> getUserViewedNewsSentiment(Long userId) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return newsViewRepository.findByUserIdAndViewedAtAfter(userId, sevenDaysAgo).stream()
                .map(view -> view.getNews().getSentiment().toString()) // News 엔티티 안의 본문
                .toList();
    }

    public List<String> getUserViewedNewsCategory(Long userId) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return newsViewRepository.findByUserIdAndViewedAtAfter(userId, sevenDaysAgo).stream()
                .map(view -> view.getNews().getCategory().toString()) // News 엔티티 안의 본문
                .toList();
    }

    public Map<NewsCategory, Long> countUserViewedNewsByCategory(Long userId) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return newsViewRepository.findByUserIdAndViewedAtAfter(userId, sevenDaysAgo).stream()
                .map(view -> view.getNews().getCategory())
                .collect(java.util.stream.Collectors.groupingBy(cat -> cat, java.util.stream.Collectors.counting()));
    }

    public List<EmotionCountDto> countUserViewedNewsByEmotion(Long userId) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        // DB에서 최근 7일간 본 뉴스 가져오기
        Map<Emotion, Long> counts = newsViewRepository
                .findByUserIdAndViewedAtAfter(userId, sevenDaysAgo).stream()
                .map(view -> view.getNews().getEmotion())
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        // 모든 Emotion을 돌면서 없는 건 0으로 세팅
        return Arrays.stream(Emotion.values())
                .map(emotion -> EmotionCountDto.builder()
                        .code(emotion.getCode())
                        .count(counts.getOrDefault(emotion, 0L))
                        .build())
                .toList();
    }

    public NegativeRateDto NewsForCaution(Long userId) {
        List<String> sentiments = getUserViewedNewsSentiment(userId);
        long negativeCount = sentiments.stream().filter(s -> s.equals(Sentiment.ANGER_CRITICISM.toString())).count();
        long totalCount = sentiments.size();

        if (totalCount == 0) {
            return new NegativeRateDto(0);
        }

        return new NegativeRateDto((double) negativeCount / totalCount);
    }


    public List<SentimentPercentageDto> countUserViewedNewsBySentimentPercentage(Long userId) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        List<NewsView> views = newsViewRepository
                .findByUserIdAndViewedAtAfter(userId, sevenDaysAgo);

        // 전체 개수
        long total = views.size();
        if (total == 0) {
            return List.of(); // 빈 리스트 반환
        }

        // 감정별 개수 집계
        Map<Sentiment, Long> counts = views.stream()
                .map(v -> v.getNews().getSentiment())
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        // 퍼센티지 변환
        // 모든 Emotion을 돌면서, 없는 건 0으로 처리
        return Arrays.stream(Sentiment.values())
                .map(sentiment -> SentimentPercentageDto.builder()
                        .code(sentiment.getCode())
                        .percentage(total > 0
                                ? (counts.getOrDefault(sentiment, 0L) * 100.0 / total)
                                : 0.0)
                        .build())
                .toList();
    }
}