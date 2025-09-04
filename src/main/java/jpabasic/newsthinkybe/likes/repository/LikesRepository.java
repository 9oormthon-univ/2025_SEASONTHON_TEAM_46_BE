package jpabasic.newsthinkybe.likes.repository;

import jpabasic.newsthinkybe.likes.domain.Likes;
import jpabasic.newsthinkybe.news.domain.News;
import jpabasic.newsthinkybe.user.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByUserAndNews(User user, News news);
    List<Likes> findByUserId(Long userId);
}
