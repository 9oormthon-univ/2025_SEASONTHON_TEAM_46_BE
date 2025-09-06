package jpabasic.newsthinkybe.news.domain;

import jakarta.persistence.*;
import jpabasic.newsthinkybe.comment.domain.Comment;
import jpabasic.newsthinkybe.global.domain.BaseEntity;
import jpabasic.newsthinkybe.news.dto.NewsResponseDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class News extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String outlet; // 언론사

    @Column
    private String outlet_img = "static/img/khan.png"; // 언론사 이미지

    @Column(name = "feed_url")
    private String feedUrl;

    @Column
    private String title;

    @Column
    private String author;

    @Column(columnDefinition = "text")
    private String summary;

    @Column(unique = true)
    private String link;

    @Column
    private String published; // ISO 8601 format

    @Column
    private LocalDateTime crawledAt;

    @Enumerated(EnumType.STRING)
    private NewsCategory category;  // 카테고리

    @Enumerated(EnumType.STRING)
    private Sentiment sentiment; // 감정 분석 결과

    @Column
    private Double confidence; // 감정 분석 확신도 (0.0 ~ 1.0)

    @Column(columnDefinition = "text")
    private String rationale;  // 감정 분석 근거 (텍스트 일부 발췌)

    @Enumerated(EnumType.STRING)
    @Column(name = "political_orientation")
    private PoliticalOrientation politicalOrientation; // 성향 분석 결과

    @Enumerated(EnumType.STRING)
    private Emotion emotion; // 감정 (긍정, 중립, 부정)

    @Column(name = "emotion_rating", nullable = false)
    private Double emotionRating = 0.0;

    @Column
    private String thumbnail;

    @Column(name = "like_count")
    @ColumnDefault("0")
    private Integer likeCount;

    @Column(name = "tagged_at")
    private LocalDateTime taggedAt;

    // ✅ ArticleImage 연관관계 매핑
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsImage> images = new ArrayList<>();

    @OneToMany(mappedBy="news",cascade=CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public static NewsResponseDto toDTO(News news) {
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
                .category(news.getCategory())
                .sentiment(news.getSentiment())
                .confidence(news.getConfidence())
                .rationale(news.getRationale())
                .orientation(news.getPoliticalOrientation())
                .emotion(news.getEmotion())
                .emotionRating(news.getEmotionRating() != null ? news.getEmotionRating() : 0.0)
                .thumbnail(news.getThumbnail())
                .likeCount(news.getLikeCount())
                .taggedAt(news.getTaggedAt())
                .build();
    }


    public Integer increaseCount(){
        likeCount++;
        return likeCount;
    }

    public Integer decreaseCount(){
        if (likeCount > 0){
            likeCount--;
        }
        return likeCount;
    }
}
