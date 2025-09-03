package jpabasic.newsthinkybe.news.dto;

import jpabasic.newsthinkybe.news.domain.NewsCategory;
import jpabasic.newsthinkybe.news.domain.Sentiment;
import jpabasic.newsthinkybe.news.domain.PoliticalOrientation;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NewsResponseDto {
    private Long id;
    private String outlet;   // 언론사
    private String feedUrl;
    private String title;
    private String author;
    private String summary;
    private String link;
    private String published;
    private LocalDateTime crawledAt;

    private NewsCategory category;          // 카테고리
    private Sentiment sentiment;            // 감정 분석 결과
    private Double confidence;              // 확신도
    private String rationale;               // 근거
    private PoliticalOrientation orientation; // 정치 성향

    private double emotionRating;
    private String thumbnail;
    private Long likeCount;
    private LocalDateTime taggedAt;
}
