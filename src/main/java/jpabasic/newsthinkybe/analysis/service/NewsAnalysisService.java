package jpabasic.newsthinkybe.analysis.service;

import jpabasic.newsthinkybe.analysis.dto.NegativeRateDto;
import jpabasic.newsthinkybe.news.domain.Emotion;
import jpabasic.newsthinkybe.news.domain.NewsCategory;
import jpabasic.newsthinkybe.news.domain.Sentiment;
import jpabasic.newsthinkybe.news.service.NewsService;
import jpabasic.newsthinkybe.view.repository.NewsViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    public Map<Emotion, Long> countUserViewedNewsByEmotion(Long userId) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return newsViewRepository.findByUserIdAndViewedAtAfter(userId, sevenDaysAgo).stream()
                .map(view -> view.getNews().getEmotion())
                .collect(java.util.stream.Collectors.groupingBy(emotion -> emotion, java.util.stream.Collectors.counting()));
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


}
