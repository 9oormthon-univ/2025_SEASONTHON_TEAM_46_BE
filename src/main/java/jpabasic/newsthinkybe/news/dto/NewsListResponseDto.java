package jpabasic.newsthinkybe.news.dto;

import jpabasic.newsthinkybe.news.domain.News;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NewsListResponseDto {

    private Long id;
    private String outlet;
    private String title;
//    @Size(max=30)
//    private String description;
//    private String image;
    private String politicalOrientation; //한글로 나오도록 해야함.
    private Integer likeCount;
    private String summary;



    public static NewsListResponseDto toDto(News news) {
        return NewsListResponseDto.builder()
                .id(news.getId())
                .outlet(news.getOutlet())
                .title(news.getTitle())
                .politicalOrientation(news.getPoliticalOrientation().getDescription())
                .likeCount(news.getLikeCount())
                .summary(news.getSummary())
                .build();
    }
}
