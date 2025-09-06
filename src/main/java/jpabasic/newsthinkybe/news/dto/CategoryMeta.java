package jpabasic.newsthinkybe.news.dto;

import jpabasic.newsthinkybe.news.domain.NewsCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryMeta {      // 🔥 static으로!
    private String label;
    private String text;
    private String description;
    private String color;
    private String bgColor;

    public static CategoryMeta fromEnum(NewsCategory c) {
        return CategoryMeta.builder()
                .label(c.getLabel())
                .text(c.getText())
                .description(c.getDescription())
                .color(c.getColor())
                .bgColor(c.getBgColor())
                .build();
    }
}