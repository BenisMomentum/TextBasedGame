package main.Items.Effects.StatusEffects;

import main.Entities.Entity;

public abstract class StatusEffect {
    protected int duration; //Duration in turns
    protected int strength; //Whether it be Regen or Bleed, the value that its gonna increment or decrement by.

    public StatusEffect(int d, int eV){
        if(d > 0 && eV > 0){
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
}
