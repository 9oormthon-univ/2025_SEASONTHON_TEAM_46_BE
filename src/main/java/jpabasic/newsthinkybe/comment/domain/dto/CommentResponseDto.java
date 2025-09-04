package jpabasic.newsthinkybe.comment.domain.dto;

import jpabasic.newsthinkybe.comment.domain.Comment;
import jpabasic.newsthinkybe.user.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentResponseDto {

    private String profileUrl;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentResponseDto toResponse(Comment comment, User user) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setProfileUrl(user.getProfileUrl());
        dto.setUsername(user.getNickname());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }

    public static CommentResponseDto toDto(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUsername(comment.getUser().getNickname());
        dto.setProfileUrl(comment.getUser().getProfileUrl());
        return dto;
    }

    public static CommentResponseDto updateDto(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUsername(comment.getUser().getNickname());
        dto.setProfileUrl(comment.getUser().getProfileUrl());
        dto.setUpdatedAt(comment.getUpdatedAt());
        return dto;
    }


}
