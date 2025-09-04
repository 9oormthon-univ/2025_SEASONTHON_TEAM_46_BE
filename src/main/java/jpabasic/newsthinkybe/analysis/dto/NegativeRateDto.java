package jpabasic.newsthinkybe.analysis.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NegativeRateDto {
    private double rate;

    public NegativeRateDto(double rate) {
        this.rate = rate;
    }
}
