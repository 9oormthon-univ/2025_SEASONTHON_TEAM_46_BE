package jpabasic.newsthinkybe.view.repository;


import jpabasic.newsthinkybe.view.domain.NewsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsViewRepository extends JpaRepository<NewsView, Long> {
    List<NewsView> findByUserId(Long userId);

    List<NewsView> findByUserIdAndViewedAtAfter(Long userId, LocalDateTime since);
}
