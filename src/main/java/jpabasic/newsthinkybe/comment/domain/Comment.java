package jpabasic.newsthinkybe.comment.domain;

import jakarta.persistence.*;
import jpabasic.newsthinkybe.global.domain.BaseEntity;
import jpabasic.newsthinkybe.news.domain.News;
import jpabasic.newsthinkybe.user.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

//    @Column(name = "like_count")
//    @ColumnDefault("0")
//    private Integer likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="news_id",nullable = false)
    private News news;

    public Comment(User user,String content,News news){
        this.user = user;
        this.content = content;
        this.news = news;
    }

    public void updateContent(String content){
        this.content = content;
    }

}
