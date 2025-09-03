package jpabasic.newsthinkybe.news.dto;

import jakarta.validation.constraints.Size;
import jpabasic.newsthinkybe.news.domain.PoliticalOrientation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewsListResponseDto {

    private String outlet;
    private String title;
    @Size(max=30)
    private String description;
    private String image;
    private PoliticalOrientation orientation; //한글로 나오도록 해야함.
}
