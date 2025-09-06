package jpabasic.newsthinkybe.news.dto;

import jpabasic.newsthinkybe.news.domain.Sentiment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SentimentMeta {
    private String code;
    private String text;
    private String description;
    private String color;
    private String bgColor;

    public static SentimentMeta fromEnum(Sentiment s) {
        if (s == null) return null;
        return SentimentMeta.builder()
                .code(s.getCode())
                .text(s.getText())
                .description(s.getDescription())
                .color(s.getColor())
                .bgColor(s.getBgColor())
                .build();
    }

}
