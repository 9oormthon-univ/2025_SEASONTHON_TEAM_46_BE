package jpabasic.newsthinkybe.news.domain;

public enum PoliticalOrientation {

    PROGRESSIVE("progressive", "진보"),
    CONSERVATIVE("conservative", "보수"),
    MODERATE("moderate", "중도");

    private final String code;
    private final String description;

    PoliticalOrientation(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PoliticalOrientation fromCode(String code) {
        for (PoliticalOrientation o : values()) {
            if (o.code.equalsIgnoreCase(code)) {
                return o;
            }
        }
        throw new IllegalArgumentException("Unknown political orientation: " + code);
    }
}

