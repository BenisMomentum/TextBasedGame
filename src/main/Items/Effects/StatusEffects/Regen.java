package main.Items.Effects.StatusEffects;

import main.Entities.Entity;
import main.Entities.Player;

public class Regen extends StatusEffect{

    public Regen(int d, int eV) {
        super(d, eV);
        this.type = StatusEffectList.REGEN;
    }

    @Override
    public void tick(Entity e) {
        if(duration >= 0){
            if(e instanceof Player){
                ((Player) e).heal(this.strength);
            }else{
                e.setHp(e.getHp() + this.strength);
            }
        }
        duration--;
    }
}
