package jpabasic.newsthinkybe.news.domain;

public enum Sentiment {

    HOPE_ENCOURAGE("HOPE_ENCOURAGE", "성취, 희망, 승리, 격려, 긍정적 메시지"),
    ANGER_CRITICISM("ANGER_CRITICISM", "분노, 비판, 불만, 논란"),
    ANXIETY_CRISIS("ANXIETY_CRISIS", "불안, 위기, 공포, 갈등"),
    SAD_SHOCK("SAD_SHOCK", "슬픔, 충격, 재난, 사고, 패배"),
    NEUTRAL_FACTUAL("NEUTRAL_FACTUAL", "중립적, 단순 사실 전달");

    private final String code;
    private final String description;

    Sentiment(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Sentiment fromCode(String code) {
        for (Sentiment s : values()) {
            if (s.code.equalsIgnoreCase(code)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown sentiment code: " + code);
    }
}