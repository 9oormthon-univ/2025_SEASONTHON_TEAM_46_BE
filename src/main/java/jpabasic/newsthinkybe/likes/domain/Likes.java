package jpabasic.newsthinkybe.likes.domain;

import jakarta.persistence.*;
import jpabasic.newsthinkybe.global.domain.BaseEntity;
import jpabasic.newsthinkybe.news.domain.News;
import jpabasic.newsthinkybe.user.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Likes extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="news_id")
    private News news;


    public Likes(User user, News news) {
        this.user = user;
        this.news = news;
    }
}
