package jpabasic.newsthinkybe.likes.service;

import jakarta.transaction.Transactional;
import jpabasic.newsthinkybe.global.exception.CustomException;
import jpabasic.newsthinkybe.global.exception.ErrorCode;
import jpabasic.newsthinkybe.likes.domain.Likes;
import jpabasic.newsthinkybe.likes.repository.LikesRepository;
import jpabasic.newsthinkybe.news.domain.News;
import jpabasic.newsthinkybe.news.dto.NewsListResponseDto;
import jpabasic.newsthinkybe.news.repository.NewsRepository;
import jpabasic.newsthinkybe.user.domain.user.User;
import jpabasic.newsthinkybe.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LikesService {

    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final LikesRepository likesRepository;

    public LikesService(UserRepository userRepository, NewsRepository newsRepository, LikesRepository likesRepository) {
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;
        this.likesRepository = likesRepository;
    }

    @Transactional
    public Integer giveLikes(Long newsId, User user){

        News news=newsRepository.findById(newsId)
                .orElseThrow(()->new CustomException(ErrorCode.NEWS_NOT_FOUND));

        //이미 좋아요 했는지 확인
        Optional<Likes> existing=likesRepository.findByUserAndNews(user,news);
        Integer likeCount;

        if(existing.isPresent()){
            //삭제
            likesRepository.delete(existing.get());
            likeCount=news.decreaseCount();
        }else{
            //likes 레포에 저장
            Likes likes=new Likes(user,news);
            likesRepository.save(likes);

            //좋아요 수 +1 //동시성 문제 해결해야 🚨
            likeCount=news.increaseCount();
        }

        newsRepository.save(news);
        return likeCount;
    }

    @Scheduled(cron="0 0 * * * *") //매시 정각
    @Transactional
    public List<NewsListResponseDto> giveTopLikes(){
        //오늘
        LocalDateTime today=LocalDateTime.now();
        System.out.println("🖥️today:"+today);

        // 이번 주 월요일 00:00
        LocalDateTime monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .toLocalDate()
                .atStartOfDay();

        // 이번 주 일요일 23:59:59
        LocalDateTime sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                .toLocalDate()
                .atTime(LocalTime.MAX);
        //좋아요 많은 순으로 정렬된 뉴스 받아오기
        List<News> newsList=getUntilNowNews(monday,sunday);
        List<NewsListResponseDto> resultList=
                newsList.stream()
                        .map(NewsListResponseDto::toDto)
                        .toList();

        return resultList;
    }

    private List<News> getUntilNowNews(LocalDateTime thisMonday, LocalDateTime thisSunday){
        Pageable top10= PageRequest.of(0, 10);
        List<News> thisWeekNews=newsRepository.findTopByCreatedAtBetween(thisMonday,thisSunday,top10);
        return thisWeekNews;
    }

    public List<NewsListResponseDto> getMyLikes(Long userId){

        List<Likes> likes=likesRepository.findByUserId(userId);
        List<News> news=likes.stream()
                .map(Likes::getNews)
                .toList();
        List<NewsListResponseDto> resultList=
                news.stream()
                        .map(NewsListResponseDto::toDto)
                        .toList();

        return resultList;
    }


}
