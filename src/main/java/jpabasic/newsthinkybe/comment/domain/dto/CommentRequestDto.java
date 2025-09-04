package jpabasic.newsthinkybe.comment.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    private Long newsId;
    private String content;
}
