package jpabasic.newsthinkybe.news.domain;

public enum NewsCategory {

    POLITICS("POLITICS", "Government, policy, diplomacy, elections"),
    SOCIETY("SOCIETY", "Incidents, accidents, labor, education, crime"),
    ECONOMY("ECONOMY", "Business, finance, industry, trade, stock, prices"),
    INTERNATIONAL("INTERNATIONAL", "International relations, war, diplomacy, overseas events"),
    CULTURE("CULTURE", "Movies, drama, entertainment, tradition"),
    SPORTS("SPORTS", "Baseball, soccer, basketball, Olympics, game results"),
    IT_SCIENCE("IT_SCIENCE", "Technology, science, internet, AI");

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
