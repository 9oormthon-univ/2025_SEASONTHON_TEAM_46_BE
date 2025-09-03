package jpabasic.newsthinkybe.news.service;

import jpabasic.newsthinkybe.global.domain.BaseService;
import jpabasic.newsthinkybe.global.exception.CustomException;
import jpabasic.newsthinkybe.global.exception.ErrorCode;
import jpabasic.newsthinkybe.news.domain.News;
import jpabasic.newsthinkybe.news.dto.NewsBodyDto;
import jpabasic.newsthinkybe.news.dto.NewsResponseDto;
import jpabasic.newsthinkybe.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService extends BaseService<News, Long> {

    private final NewsRepository newsRepository;

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
        news.setOrientation(update.getOrientation());
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
}
