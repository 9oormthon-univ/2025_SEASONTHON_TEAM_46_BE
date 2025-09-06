package jpabasic.newsthinkybe.news.service;

import jpabasic.newsthinkybe.news.domain.News;
import jpabasic.newsthinkybe.news.domain.NewsCategory;
import jpabasic.newsthinkybe.news.dto.NewsListResponseDto;
import jpabasic.newsthinkybe.news.repository.NewsRepository;
import jpabasic.newsthinkybe.view.dto.PagedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SearchService {

    private final NewsRepository newsRepository;
    public SearchService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public PagedResponse<NewsListResponseDto> searchNews(String keyword, int page, int size, NewsCategory category) {
        //page=조회할 페이지의 번호, 10=한 페이지에 보여줄 뉴스 개수
        Pageable pageable = PageRequest.of(page, size);
        Page<News> getSearchResult = null;
        if (category == null) {
            //필터링
            getSearchResult=newsRepository.searchByTitle(keyword, pageable);
        } else {
            getSearchResult=newsRepository.searchByTitleAndCategory(keyword, pageable, category);
        }
        System.out.println("✅getSearchResult:" + getSearchResult);
        Page<NewsListResponseDto> dtoPage = getSearchResult.map(NewsListResponseDto::toDto);
        return new PagedResponse<>(dtoPage);
    }
}
