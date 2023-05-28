package main.Items.Effects.StatusEffects;

import main.Entities.Entity;

public class Bleed extends StatusEffect{
    public Bleed(int d, int eV) {
        super(d, eV);
    }

    @Override
    public void tick(Entity e) {
        if(this.duration >= 0){
            e.setHp(e.getHp() - this.strength);
        }
        duration --;
    }
}
