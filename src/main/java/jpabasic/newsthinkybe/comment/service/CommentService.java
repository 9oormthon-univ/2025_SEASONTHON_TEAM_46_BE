package jpabasic.newsthinkybe.comment.service;

import jpabasic.newsthinkybe.comment.domain.Comment;
import jpabasic.newsthinkybe.comment.domain.dto.CommentRequestDto;
import jpabasic.newsthinkybe.comment.domain.dto.CommentResponseDto;
import jpabasic.newsthinkybe.comment.repository.CommentRepository;
import jpabasic.newsthinkybe.global.exception.CustomException;
import jpabasic.newsthinkybe.global.exception.ErrorCode;
import jpabasic.newsthinkybe.news.domain.News;
import jpabasic.newsthinkybe.news.repository.NewsRepository;
import jpabasic.newsthinkybe.user.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    public CommentService(CommentRepository commentRepository, NewsRepository newsRepository) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
    }

    public CommentResponseDto writeComment(User user, CommentRequestDto dto){
        News news=newsRepository.findById(dto.getNewsId())
                .orElseThrow(()->new CustomException(ErrorCode.NEWS_NOT_FOUND));

        String content=dto.getContent();
        Comment comment;

        try {
            comment = new Comment(user, content,news);
            commentRepository.save(comment);
        }catch(CustomException e){
            throw new CustomException(ErrorCode.COMMENT_SAVE_ERROR);
        }
        CommentResponseDto responseDto=CommentResponseDto.toResponse(comment,user);
        return responseDto;
    }

    public List<CommentResponseDto> getCommentList(Long newsId){
        News news=newsRepository.findById(newsId)
                .orElseThrow(()->new CustomException(ErrorCode.NEWS_NOT_FOUND));

        List<Comment> comments=news.getComments();
        List<CommentResponseDto> result=
                comments.stream()
                        .map(CommentResponseDto::toDto)
                        .toList();
        return result;
    }

    public void deleteComment(Long commentId){
        try {
            commentRepository.deleteById(commentId);
        }catch(CustomException e){
            throw new CustomException(ErrorCode.COMMENT_DELETE_FAILURE);
        }
    }

    public CommentResponseDto editComment(Long commentId,CommentRequestDto request){
        Comment comment=commentRepository.findById(commentId)
                .orElseThrow(()->new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        try {
            String edited = request.getContent();
            comment.updateContent(edited);
            commentRepository.save(comment);
        }catch(CustomException e){
            throw new CustomException(ErrorCode.COMMENT_SAVE_ERROR);
        }

        CommentResponseDto result=CommentResponseDto.updateDto(comment);
        return result;
    }
}
