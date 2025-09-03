package jpabasic.newsthinkybe.analysis.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GptService {

    private final WebClient webClient;

    public GptService(@Value("${openai.api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String summarizeSentimentFromCategory(List<Map<String, String>> newsData) {
        // newsData를 문자열로 변환 (예: "정치-부정, 경제-중립...")
        String formatted = newsData.stream()
                .map(d -> d.get("category") + "-" + d.get("sentiment"))
                .collect(Collectors.joining(", "));

        String prompt = """
        사용자가 읽은 뉴스의 카테고리와 감정 분류 결과입니다.
        이 데이터를 분석하여 전반적인 독서 경향을 2~3문장으로 요약해 주세요.
        
        요약에는 다음 요소가 반드시 포함되어야 합니다:
        1. 어떤 카테고리 기사에 더 집중했는지
        2. 긍정/부정 감정의 전반적 비율 또는 경향
        3. 전반적인 해석적 결론 (예: 관심사나 분위기)
        
        출력 형식 규칙:
        - 반드시 따옴표(")로 감싼 한국어 문장 리스트로 출력합니다.
        - 불필요한 설명이나 접두어 없이 결과 문장만 출력합니다.
        
        예시:
        "이번 주는 경제 기사보다 정치 기사에 더 많은 시간을 소비했습니다."
        "부정적 뉴스 비율이 전체 50%로 높게 나타났습니다."
        
        데이터:
        """ + formatted;

        Map<String, Object> request = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a helpful assistant."),
                        Map.of("role", "user", "content", prompt)
                )
        );

        Map<String, Object> response = webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> firstChoice = choices.get(0);
        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");

        return (String) message.get("content");
    }

}
