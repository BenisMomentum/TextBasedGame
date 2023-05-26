package main.Items.Effects.WeaponEffects;

import main.Entities.Entity;
import main.Entities.Player;

public class Pierce extends WeaponEffect{
    public Pierce(int value){
        this.value = 1;
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
