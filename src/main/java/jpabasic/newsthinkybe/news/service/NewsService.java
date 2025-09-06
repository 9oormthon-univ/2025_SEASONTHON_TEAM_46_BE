package jpabasic.newsthinkybe.news.service;

import jdk.jfr.Category;
import jpabasic.newsthinkybe.global.domain.BaseService;
import jpabasic.newsthinkybe.global.exception.CustomException;
import jpabasic.newsthinkybe.global.exception.ErrorCode;
import jpabasic.newsthinkybe.news.domain.Emotion;
import jpabasic.newsthinkybe.news.domain.News;
import jpabasic.newsthinkybe.news.domain.NewsCategory;
import jpabasic.newsthinkybe.news.dto.NewsBodyDto;
import jpabasic.newsthinkybe.news.dto.NewsResponseDto;
import jpabasic.newsthinkybe.news.repository.NewsRepository;
import jpabasic.newsthinkybe.view.dto.PagedResponse;
import jpabasic.newsthinkybe.view.repository.NewsViewRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService extends BaseService<News, Long> {

    private final NewsRepository newsRepository;
    private final NewsViewRepository newsViewRepository;

    // 뉴스 등록
//    public News createNews(News news) {
//        return newsRepository.save(news);
//    }

    // 뉴스 단건 조회
    public NewsResponseDto getNews(Long id) {
        News news = getEntityOrThrow(newsRepository, id, "News");
        return News.toDTO(news);
    }

    // 뉴스 전체 조회
    public List<NewsResponseDto> getAllNews() {
        return newsRepository.findAll().stream().toList().stream()
                .map(News::toDTO)
                .toList();
    }

    // 뉴스 수정
    public NewsResponseDto updateNews(Long id, News update) {
        News news = getEntityOrThrow(newsRepository, id, "News");

        // 엔티티 값 갱신
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
        news.setPoliticalOrientation(update.getPoliticalOrientation());
        news.setEmotionRating(update.getEmotionRating());
        news.setThumbnail(update.getThumbnail());
        news.setLikeCount(update.getLikeCount());
        news.setTaggedAt(update.getTaggedAt());

        // 저장 후 DTO 변환
        return News.toDTO(newsRepository.save(news));
    }
    // 뉴스 삭제
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    public NewsBodyDto getNewsBody(Long id) {
        News news = getEntityOrThrow(newsRepository, id, "News");

        try (Reader reader = Files.newBufferedReader(Paths.get("rss_news.csv"), StandardCharsets.UTF_8)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);

            for (CSVRecord record : records) {
                if (record.get("link").equals(news.getLink())) {
                    return new NewsBodyDto(record.get("body"));
                }
            }
            throw new CustomException(ErrorCode.NEWS_NOT_FOUND); // CSV에도 없는 경우
        } catch (IOException e) {
            throw new CustomException(ErrorCode.CSV_READ_ERROR, e.getMessage());
        }
    }

    public List<NewsResponseDto> getOpposingEmotionRecommendations(Long newsId, Long userId) {
        News curNews = getEntityOrThrow(newsRepository, newsId, "News");
        Emotion curEmotion = curNews.getEmotion();

        Emotion targetEmotion;
        if (curEmotion == Emotion.POSITIVE) {
            targetEmotion = Emotion.NEGATIVE;
        } else if (curEmotion == Emotion.NEGATIVE) {
            targetEmotion = Emotion.POSITIVE;
        } else {
            targetEmotion = Emotion.NEUTRAL;
        }

        List<News> recommendations = newsRepository
                .findUnviewedNewsByEmotionList(targetEmotion, userId, PageRequest.of(0, 2));

        return recommendations.stream()
                .map(News::toDTO)
                .toList();
    }


    public List<NewsResponseDto> getOpposingCategoryRecommendations(Long newsId, Long userId) {
        // 현재 뉴스 가져오기
        News curNews = getEntityOrThrow(newsRepository, newsId, "News");
        NewsCategory curCategory = curNews.getCategory();

        // 사용자가 본 뉴스 ID 목록 가져오기
        List<Long> viewedNewsIds = newsViewRepository.findByUserId(userId).stream()
                .map(view -> view.getNews().getId())
                .toList();

        // 다른 카테고리 뉴스 중, 사용자가 안 본 것만 가져오기
        List<News> candidates = newsRepository.findByCategoryNotAndIdNotIn(curCategory, viewedNewsIds);

        // 랜덤 2개 선택
        Collections.shuffle(candidates);
        return candidates.stream()
                .limit(2)
                .map(News::toDTO)
                .toList();
    }

    public PagedResponse<NewsResponseDto> recommendByCategory(NewsCategory category, Long userId, Pageable pageable) {
        Page<News> page = newsRepository.findUnviewedNewsByCategory(category, userId, pageable);
        Page<NewsResponseDto> dtoPage = page.map(News::toDTO);
        return new PagedResponse<>(dtoPage);
    }

    public PagedResponse<NewsResponseDto> recommendByEmotion(Emotion emotion, Long userId, Pageable pageable) {
        Page<News> page = newsRepository.findUnviewedNewsByEmotionPage(emotion, userId, pageable);
        Page<NewsResponseDto> dtoPage = page.map(News::toDTO);
        return new PagedResponse<>(dtoPage);
    }
}
