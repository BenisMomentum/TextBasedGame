package main.Items.Effects.ArmourEffects;

public enum ArmourEffectList {
    VITALITY("VITALITY"),
    SWIFTNESS("SWIFTNESS");

    private final String name;

    ArmourEffectList(String name) {
        this.name = name;
    }

    public static ArmourEffectList parse(String input){
        return ArmourEffectList.valueOf(input.toUpperCase());
    }
}
