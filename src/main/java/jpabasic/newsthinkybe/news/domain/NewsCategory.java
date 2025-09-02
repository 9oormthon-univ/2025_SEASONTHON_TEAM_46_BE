package jpabasic.newsthinkybe.news.domain;

public enum NewsCategory {

    POLITICS("Politics", "Government, policy, diplomacy, elections"),
    SOCIETY("Society", "Incidents, accidents, labor, education, crime"),
    ECONOMY("Economy", "Business, finance, industry, trade, stock, prices"),
    INTERNATIONAL("International", "International relations, war, diplomacy, overseas events"),
    CULTURE("Culture", "Movies, drama, entertainment, tradition"),
    SPORTS("Sports", "Baseball, soccer, basketball, Olympics, game results"),
    IT_SCIENCE("IT_Science", "Technology, science, internet, AI");

    private final String label;
    private final String description;

    NewsCategory(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
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
