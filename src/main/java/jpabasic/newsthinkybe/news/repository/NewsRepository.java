package jpabasic.newsthinkybe.news.repository;

import jdk.jfr.Category;
import jpabasic.newsthinkybe.news.domain.Emotion;
import jpabasic.newsthinkybe.news.domain.News;
import jpabasic.newsthinkybe.news.domain.NewsCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT n FROM News n WHERE n.emotion = :emotion AND n.id <> :id ORDER BY function('RANDOM')")
    List<News> findRandom3ByEmotionAndIdNot(@Param("emotion") Emotion emotion,
                                            @Param("id") Long id,
                                            Pageable pageable);

    List<News> findByEmotionAndIdNot(Emotion targetEmotion, Long id);


    // 사용자가 본 뉴스 ID를 제외하고 감정 반대 뉴스 추천
    @Query("SELECT n FROM News n " +
            "WHERE n.emotion = :emotion " +
            "AND n.id NOT IN (SELECT v.news.id FROM NewsView v WHERE v.user.id = :userId)")
    List<News> findUnviewedNewsByEmotionList(@Param("emotion") Emotion emotion,
                                             @Param("userId") Long userId,
                                             Pageable pageable);

    List<News> findByCategoryNotAndIdNotIn(NewsCategory curCategory, List<Long> viewedNewsIds);


    @Query("SELECT n FROM News n WHERE n.category = :category " +
            "AND n.id NOT IN (SELECT v.news.id FROM NewsView v WHERE v.user.id = :userId)")
    Page<News> findUnviewedNewsByCategory(@Param("category") NewsCategory category,
                                          @Param("userId") Long userId,
                                          Pageable pageable);

    @Query("SELECT n FROM News n WHERE n.emotion = :emotion " +
            "AND n.id NOT IN (SELECT v.news.id FROM NewsView v WHERE v.user.id = :userId)")
    Page<News> findUnviewedNewsByEmotionPage(@Param("emotion") Emotion emotion,
                                             @Param("userId") Long userId,
                                             Pageable pageable);
    @Query("SELECT n FROM News n WHERE n.taggedAt BETWEEN :start AND :end ORDER BY n.likeCount DESC")
    List<News> findTopByCreatedAtBetween(@Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end,
                                         Pageable pageable);

    @Query("SELECT n FROM News n WHERE n.title LIKE %:keyword% ORDER BY n.id DESC")
    Page<News> searchByTitle(@Param("keyword") String keyword, Pageable pageable);





}
