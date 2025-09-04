package jpabasic.newsthinkybe.news.dto;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SentimentPercentageDto {
    private String code;
    private double percentage;
}
