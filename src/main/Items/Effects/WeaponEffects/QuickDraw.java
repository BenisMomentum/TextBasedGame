package main.Items.Effects.WeaponEffects;

import main.Entities.Entity;
import main.Entities.Player;

public class QuickDraw extends WeaponEffect{

    public QuickDraw(int value) {
        super.value = value;
        super.type = WeaponEffectList.QUICKDRAW;
    }

    @Override
    public void useEffect(Entity e) {

    }

    @Override
    public void addEffect(Entity e) {
        if(checkIfPlayer(e)){
            ((Player) e).addInit(this.value); //Checks if its a player and then adds the value.
        }
    }

    @Override
    public void removeEffect(Entity e) {
        if(checkIfPlayer(e)){
            ((Player) e).addInit(-this.value);
        }
    }
}
