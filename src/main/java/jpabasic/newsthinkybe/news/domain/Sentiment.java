package jpabasic.newsthinkybe.news.domain;

public enum Sentiment {

    HOPE_ENCOURAGE("HOPE_ENCOURAGE", "Hope, encouragement, achievement", "성취", "#38D1B8", "rgba(123, 234, 215, 0.26)"),
    ANGER_CRITICISM("ANGER_CRITICISM", "Anger, criticism, dissatisfaction", "분노", "#F63E3E", "rgba(255, 118, 118, 0.26)"),
    ANXIETY_CRISIS("ANXIETY_CRISIS", "Anxiety, crisis, conflict", "불안", "#F63E3E", "rgba(255, 118, 118, 0.26)"),
    SAD_SHOCK("SAD_SHOCK", "Sadness, shock, disaster, defeat", "슬픔", "#F63E3E", "rgba(255, 118, 118, 0.26)"),
    NEUTRAL_FACTUAL("NEUTRAL_FACTUAL", "Neutral, factual reporting", "중립", "#F2F2F2", "rgba(242, 242, 242, 0.26)"),
    FUN_INTEREST("FUN_INTEREST", "Fun, interest, humor", "재미", "#38D1B8", "rgba(123, 234, 215, 0.26)");

    private final String code;        // 코드 값
    private final String description; // 영문 설명
    private final String text;        // 프론트 노출명 (한글)
    private final String color;       // 대표 색상
    private final String bgColor;     // 배경 색상 (투명도 포함)

    Sentiment(String code, String description, String text, String color, String bgColor) {
        this.code = code;
        this.description = description;
        this.text = text;
        this.color = color;
        this.bgColor = bgColor;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    public String getBgColor() {
        return bgColor;
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
