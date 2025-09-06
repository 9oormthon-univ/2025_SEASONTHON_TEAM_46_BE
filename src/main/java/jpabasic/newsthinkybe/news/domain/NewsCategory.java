package jpabasic.newsthinkybe.news.domain;

public enum NewsCategory {

    POLITICS("POLITICS", "Government, policy, diplomacy, elections", "정치", "#1E88E5", "rgba(30, 136, 229, 0.2)"),
    SOCIETY("SOCIETY", "Incidents, accidents, labor, education, crime", "사회", "#43A047", "rgba(67, 160, 71, 0.2)"),
    ECONOMY("ECONOMY", "Business, finance, industry, trade, stock, prices", "경제", "#FB8C00", "rgba(251, 140, 0, 0.2)"),
    INTERNATIONAL("INTERNATIONAL", "International relations, war, diplomacy, overseas events", "국제", "#8E24AA", "rgba(142, 36, 170, 0.2)"),
    CULTURE("CULTURE", "Movies, drama, entertainment, tradition", "문화", "#FDD835", "rgba(253, 216, 53, 0.2)"),
    SPORTS("SPORTS", "Baseball, soccer, basketball, Olympics, game results", "스포츠", "#00ACC1", "rgba(0, 172, 193, 0.2)"),
    IT_SCIENCE("IT_SCIENCE", "Technology, science, internet, AI", "IT·과학", "#5E35B1", "rgba(94, 53, 177, 0.2)");

    private final String label;       // 코드 값
    private final String description; // 영문 설명
    private final String text;        // 프론트 노출명 (한글)
    private final String color;       // 대표 색상
    private final String bgColor;     // 배경 색상 (투명도 포함)

    NewsCategory(String label, String description, String text, String color, String bgColor) {
        this.label = label;
        this.description = description;
        this.text = text;
        this.color = color;
        this.bgColor = bgColor;
    }

    public String getLabel() {
        return label;
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

    public static NewsCategory fromLabel(String label) {
        for (NewsCategory c : values()) {
            if (c.label.equalsIgnoreCase(label)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown category label: " + label);
    }
}
