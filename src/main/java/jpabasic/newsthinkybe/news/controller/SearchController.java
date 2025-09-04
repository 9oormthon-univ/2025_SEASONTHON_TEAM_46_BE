package jpabasic.newsthinkybe.news.controller;

import io.swagger.v3.oas.annotations.Operation;
import jpabasic.newsthinkybe.news.dto.NewsListResponseDto;
import jpabasic.newsthinkybe.news.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    private final SearchService searchService;
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search/news")
    @Operation(summary="기사 검색하기")
    public ResponseEntity<List<NewsListResponseDto>> searchNews(@RequestParam String keyword,@RequestParam int page){
        List<NewsListResponseDto> resultList=searchService.searchNews(keyword,page);
        return ResponseEntity.ok(resultList);
    }
}
