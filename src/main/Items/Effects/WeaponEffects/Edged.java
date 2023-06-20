package main.Items.Effects.WeaponEffects;

import main.Entities.Entity;
import main.Entities.Player;
import main.Items.Effects.ArmourEffects.ArmourEffect;

public class Edged extends WeaponEffect {

    //EDGED is meant to deal damage to you for the same amount it buffs your str by.

    public Edged(int value){
        this.value = value;
        this.type = WeaponEffectList.EDGED;
    }

    @Override
    public void useEffect(Entity e) {
        if(checkIfPlayer(e)){
            ((Player) e).takeDamage(this.value); //Meant to be used only while attacking with the Weapon
        }
    }

    @Override
    public void addEffect(Entity e) {
        if(checkIfPlayer(e)){ //Checks if its a player
            Player p = (Player) e; // <-- Done only for the sake of readability

            if(p.getEquipedWeapon().getEffects().contains(this)){ //Checks if the weapon is actually equipped to the player
                p.setStrength(p.getStrength() + this.value); //Increments the strength value
            }
        }
    }

    @Override
    public void removeEffect(Entity e) {
        if(checkIfPlayer(e)){ //Checks if its a player

            ((Player) e).resetSTR(); //Resets the str buff to neutral
        }
    }
}
