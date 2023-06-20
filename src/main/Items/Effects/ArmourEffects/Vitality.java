package main.Items.Effects.ArmourEffects;

import main.Entities.Entity;
import main.Entities.Player;

public class Vitality extends ArmourEffect {

    //Gives the player Extra health

    public Vitality(int armourValBoost){
        this.value = armourValBoost;
        this.type = ArmourEffectList.VITALITY;
    }

    @Override
    public void useEffect(Entity e) {

    }

    @Override
    public void addEffect(Entity e) {
        if(this.checkIfPlayer(e)){ //Checks if e is a player
            Player p = (Player) e; //Casts it to a Player for the sake of readability
            if(p.getEquipedArmour().getEffects().contains(this)){ //If the armour actually HAS this effect
                p.addMAXHP(this.value); //Increments the MAXHP
                p.setHp(p.getHp() + this.value); //Increments the current HP by the same amount
            }
        }
    }

    @Override
    public void removeEffect(Entity e) {
        if(this.checkIfPlayer(e)){
            ((Player) e).resetMAXHP(); //Resets it to OG levels
        }
    }
}
