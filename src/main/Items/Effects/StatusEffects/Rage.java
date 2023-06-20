package main.Items.Effects.StatusEffects;

import main.Entities.Entity;
import main.Entities.Player;

public class Rage extends StatusEffect {



    private boolean applied;

    public Rage(int d, int eV) {
        super(d, eV);
        applied = false;
        this.type = StatusEffectList.RAGE;
    }

    @Override
    public void tick(Entity e) {
        /*
            If Rage is not applied, then it will apply itself to the player
            If the duration runs out, applied will be false and the effect will be removed.
            Effect removal is handled in the Battle class
             */

        if(!applied && this.duration > 0){
            this.applied = true;
            e.setStrength(e.getStrength() + this.strength); //In this case this.strength refers to the Rage value
        }
        if(this.duration < 0 && applied){
            this.applied = false;
            e.setStrength(e.getStrength() - this.strength);
        }

        duration--;
    }
}
