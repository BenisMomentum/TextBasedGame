package main.Items.Effects.WeaponEffects;

public enum WeaponEffectList {
    QUICKDRAW("QUICKDRAW"), //Boosts Init and always makes you strike first
    LIFESTEAL("LIFESTEAL"), //Percentage of amount healed
    PIERCE("PIERCE"), //Ignores armour amount
    EDGED("EDGED"); //Increases damage but damages user by the same value; if the boost is 2 you get damaged by 2.

    WeaponEffectList(String edged) {

    }

    public static WeaponEffectList parse(String s) {
        return WeaponEffectList.valueOf(s.toUpperCase());
    }
}
