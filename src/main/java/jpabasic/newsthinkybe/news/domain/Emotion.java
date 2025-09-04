package jpabasic.newsthinkybe.news.domain;

public enum Emotion {

    POSITIVE("POSITIVE", "긍정"),
    NEGATIVE("NEGATIVE", "부정"),
    NEUTRAL("NEUTRAL", "중립");

    private final String code;
    private final String description;

    Emotion(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Emotion fromCode(String code) {
        for (Emotion s : values()) {
            if (s.code.equalsIgnoreCase(code)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown sentiment code: " + code);
    }
}
