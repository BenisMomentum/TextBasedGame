package main.Items.Effects.StatusEffects;

import main.Entities.Entity;

public abstract class StatusEffect {
    protected int duration; //Duration in turns
    protected int strength; //Whether it be Regen or Bleed, the value that its gonna increment or decrement by.
    protected StatusEffectList type;

    public StatusEffect(int d, int eV){
        if(d >= 0){
            this.duration = d;
            this.strength = eV;
        }
    }

    public int getDuration() {
        return duration;
    }

    public int getStrength() {
        return strength;
    }

    public abstract void tick(Entity e);

    @Override
    public String toString() {
        return this.type.name() + "=" + duration + "/" + strength; //NAME=3/5 (3 turns, 5 str)
    }

    public String toHumanReadable(){ //lmao
        return this.type.name() + " Rank: " + strength + " | (" + this.duration + ") turns";
    }

    public static StatusEffect readStatus(String input){
        String[] raw = input.trim().split("=");
        String[] deez = raw[1].split(",");

        int duration = Integer.parseInt(deez[0]);
        int str = Integer.parseInt(deez[1]);

        switch(StatusEffectList.valueOf(raw[0].toUpperCase())){
            case ADRENALINE -> {
                return new Adrenaline(duration,str);
            }
            case BLEED -> {
                return new Bleed(duration,str);
            }
            case RAGE -> {
                return new Rage(duration,str);
            }
            case REGEN -> {
                return new Regen(duration,str);
            }
            default -> {return null;}
        }
    }
}
