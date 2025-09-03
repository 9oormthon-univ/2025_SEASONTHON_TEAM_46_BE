package jpabasic.newsthinkybe.likes.service;

import jakarta.transaction.Transactional;
import jpabasic.newsthinkybe.global.exception.CustomException;
import jpabasic.newsthinkybe.global.exception.ErrorCode;
import jpabasic.newsthinkybe.likes.domain.Likes;
import jpabasic.newsthinkybe.likes.repository.LikesRepository;
import jpabasic.newsthinkybe.news.domain.News;
import jpabasic.newsthinkybe.news.repository.NewsRepository;
import jpabasic.newsthinkybe.user.domain.user.User;
import jpabasic.newsthinkybe.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public Integer giveLikes(Long newsId, Long userId){

        User user=userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

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
}
