package jpabasic.newsthinkybe.view.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsViewResponseDto {
    private Long id;
    private Long userId;
    private Long newsId;
    private String viewedAt;
}
