package jpabasic.newsthinkybe.news.dto;

import jpabasic.newsthinkybe.news.domain.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
@Data
@Builder
public class NewsResponseDto {
    private Long id;
    private String outlet;
    private String outlet_img;
    private String feedUrl;
    private String title;
    private String author;
    private String summary;
    private String link;
    private String published;
    private LocalDateTime crawledAt;

    private NewsCategory category;          // 원본 enum
    private Sentiment sentiment;
    private Double confidence;
    private String rationale;
    private PoliticalOrientation orientation;
    private Emotion emotion;
    private double emotionRating;
    private String thumbnail;
    private Integer likeCount;
    private LocalDateTime taggedAt;

    private CategoryMeta categoryMeta;      // ✅ UI-friendly 데이터

    @Data
    @Builder
    public static class CategoryMeta {      // 🔥 static으로!
        private String label;
        private String text;
        private String description;
        private String color;
        private String bgColor;

        public static CategoryMeta fromEnum(NewsCategory c) {
            return CategoryMeta.builder()
                    .label(c.getLabel())
                    .text(c.getText())
                    .description(c.getDescription())
                    .color(c.getColor())
                    .bgColor(c.getBgColor())
                    .build();
        }
    }

    public static NewsResponseDto fromEntity(News news) {
        NewsCategory c = news.getCategory();

        return NewsResponseDto.builder()
                .id(news.getId())
                .outlet(news.getOutlet())
                .outlet_img(news.getOutlet_img())
                .feedUrl(news.getFeedUrl())
                .title(news.getTitle())
                .author(news.getAuthor())
                .summary(news.getSummary())
                .link(news.getLink())
                .published(news.getPublished())
                .crawledAt(news.getCrawledAt())
                .category(c)
                .sentiment(news.getSentiment())
                .confidence(news.getConfidence())
                .rationale(news.getRationale())
                .orientation(news.getPoliticalOrientation())
                .emotion(news.getEmotion())
                .emotionRating(news.getEmotionRating())
                .thumbnail(news.getThumbnail())
                .likeCount(news.getLikeCount())
                .taggedAt(news.getTaggedAt())
                // ✅ Enum → UI-friendly meta 변환
                .categoryMeta(CategoryMeta.fromEnum(c))
                .build();
    }
}

