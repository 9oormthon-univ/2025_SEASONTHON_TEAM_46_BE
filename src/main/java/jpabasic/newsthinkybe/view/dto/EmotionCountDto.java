package jpabasic.newsthinkybe.view.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmotionCountDto {
    private String code;
    private long count;
}
