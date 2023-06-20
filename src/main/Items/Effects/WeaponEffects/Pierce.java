package main.Items.Effects.WeaponEffects;

import main.Entities.Entity;
import main.Entities.Player;

public class Pierce extends WeaponEffect{

    //Pierce, an effect that makes the player ignore armour during damage calc

    public Pierce(int value){
        this.value = 1; //Value is irrelevant
        this.type = WeaponEffectList.PIERCE;
    }

    @Override
    public void useEffect(Entity e) {
    }

    @Override
    public void addEffect(Entity e) {
    }

    @Override
    public void removeEffect(Entity e) {
    }
}
