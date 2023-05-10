package main.Items;

public enum Rarity {
    COMMON(50.0),
    UNCOMMON(25.0),
    RARE(12.5),
    EPIC(5.0),
    LEGENDARY(1.5);


    Rarity(double v) {
    }

    public static Rarity parse(String input){
        return switch (input) {
            case "COMMON" -> COMMON;
            case "UNCOMMON" -> UNCOMMON;
            case "RARE" -> RARE;
            case "EPIC" -> EPIC;
            case "LEGENDARY" -> LEGENDARY;
            default -> null;
        };
    }
}
