package jpabasic.newsthinkybe.view.service;

import jakarta.transaction.Transactional;
import jpabasic.newsthinkybe.global.domain.BaseService;
import jpabasic.newsthinkybe.global.exception.CustomException;
import jpabasic.newsthinkybe.global.exception.ErrorCode;
import jpabasic.newsthinkybe.news.domain.News;
import jpabasic.newsthinkybe.view.dto.NewsViewResponseDto;
import jpabasic.newsthinkybe.news.repository.NewsRepository;
import jpabasic.newsthinkybe.user.domain.user.User;
import jpabasic.newsthinkybe.user.repository.UserRepository;
import jpabasic.newsthinkybe.view.domain.NewsView;
import jpabasic.newsthinkybe.view.repository.NewsViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsViewService extends BaseService<NewsView, Long> {

    private final NewsViewRepository newsViewRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    @Transactional
    public NewsViewResponseDto recordView(Long userId, Long newsId) {
        User member = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, "User not found with id: " + userId));
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new CustomException(ErrorCode.NEWS_NOT_FOUND, "News not found with id: " + newsId));

        NewsView view = new NewsView();
        view.setUser(member);
        view.setNews(news);
        view.setViewedAt(LocalDateTime.now());

        NewsView savedView = newsViewRepository.save(view);
        return savedView.toDTO();
    }

    public List<NewsViewResponseDto> getViewList(Long userId) {
        List<NewsView> views = newsViewRepository.findByUserId(userId);
        return views.stream().map(NewsView::toDTO).toList();
    }
}
