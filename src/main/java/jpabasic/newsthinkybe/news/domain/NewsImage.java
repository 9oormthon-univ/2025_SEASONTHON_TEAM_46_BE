package jpabasic.newsthinkybe.news.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    private News news;

    @Column(columnDefinition = "text", nullable = false)
    private String src;

    @Column(columnDefinition = "text")
    private String alt;
}
