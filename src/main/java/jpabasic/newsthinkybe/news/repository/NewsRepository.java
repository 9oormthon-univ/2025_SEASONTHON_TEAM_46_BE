package jpabasic.newsthinkybe.news.repository;

import jpabasic.newsthinkybe.news.domain.Emotion;
import jpabasic.newsthinkybe.news.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT n FROM News n WHERE n.emotion = :emotion AND n.id <> :id ORDER BY function('RANDOM')")
    List<News> findRandom3ByEmotionAndIdNot(@Param("emotion") Emotion emotion,
                                            @Param("id") Long id,
                                            Pageable pageable);

}
