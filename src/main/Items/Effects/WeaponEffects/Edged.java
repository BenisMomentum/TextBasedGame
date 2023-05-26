package main.Items.Effects.WeaponEffects;

import main.Entities.Entity;
import main.Entities.Player;
import main.Items.Effects.ArmourEffects.ArmourEffect;

public class Edged extends WeaponEffect {

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

    }

    @Override
    public void removeEffect(Entity e) {

    }
}
