package jpabasic.newsthinkybe.view.domain;

import jakarta.persistence.*;
import jpabasic.newsthinkybe.news.domain.News;
import jpabasic.newsthinkybe.view.dto.NewsViewResponseDto;
import jpabasic.newsthinkybe.user.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "news_view")
public class NewsView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    private News news;

    @Column(name = "viewed_at", nullable = false)
    private LocalDateTime viewedAt = LocalDateTime.now();

    public NewsViewResponseDto toDTO() {
        return NewsViewResponseDto.builder()
                .id(this.id)
                .userId(this.user.getId())
                .newsId(this.news.getId())
                .viewedAt(this.viewedAt.toString())
                .build();
    }
}
