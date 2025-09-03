package jpabasic.newsthinkybe.news.service;

import jpabasic.newsthinkybe.news.domain.News;
import jpabasic.newsthinkybe.news.dto.NewsListResponseDto;
import jpabasic.newsthinkybe.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    // 뉴스 등록
    public News createNews(News news) {
        return newsRepository.save(news);
    }

    // 뉴스 단건 조회
    public Optional<News> getNews(Long id) {
        return newsRepository.findById(id);
    }

    // 뉴스 전체 조회
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    // 뉴스 수정
    public News updateNews(Long id, News update) {
        return newsRepository.findById(id)
                .map(news -> {
                    news.setOutlet(update.getOutlet());
                    news.setFeedUrl(update.getFeedUrl());
                    news.setTitle(update.getTitle());
                    news.setAuthor(update.getAuthor());
                    news.setSummary(update.getSummary());
                    news.setLink(update.getLink());
                    news.setPublished(update.getPublished());
                    news.setCrawledAt(update.getCrawledAt());
                    news.setCategory(update.getCategory());
                    news.setSentiment(update.getSentiment());
                    news.setConfidence(update.getConfidence());
                    news.setRationale(update.getRationale());
                    news.setOrientation(update.getOrientation());
                    news.setEmotionRating(update.getEmotionRating());
                    news.setThumbnail(update.getThumbnail());
                    news.setLikeCount(update.getLikeCount());
                    news.setTaggedAt(update.getTaggedAt());
                    return newsRepository.save(news);
                })
                .orElseThrow(() -> new IllegalArgumentException("News not found with id: " + id));
    }

    // 뉴스 삭제
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }


}
