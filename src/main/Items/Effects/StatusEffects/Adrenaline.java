package main.Items.Effects.StatusEffects;

import main.Entities.Entity;
import main.Entities.Player;

public class Adrenaline extends StatusEffect{

    private boolean applied;

    public Adrenaline(int d, int eV) {
        super(d, eV);

        System.out.println("Adrenaline Duration: " + d);

        applied = false;
        this.type = StatusEffectList.ADRENALINE;
    }


    @Override
    public void tick(Entity e) {
        /*
            If Adrenline is not applied, then it will apply itself to the player
            If the duration runs out, applied will be false and the effect will be removed.
            Effect removal is handled in the Battle class
             */

        if(!applied){
            this.applied = true;
            ((Player) e).addInit(this.strength); //In this case this.strength refers to the INIT value

        } else if(this.duration < 0 && applied){

            this.applied = false;
            ((Player) e).addInit(-this.strength);
            System.out.println("Remove effect called:" + e.getInitiative());
        }
        System.out.println(e.getInitiative());


        duration--;

    }
}
