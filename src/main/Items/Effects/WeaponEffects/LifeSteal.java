package main.Items.Effects.WeaponEffects;

import main.Entities.Entity;
import main.Entities.Player;

public class LifeSteal extends WeaponEffect{


    public LifeSteal(int value) {
        this.value = value;
        this.type = WeaponEffectList.LIFESTEAL;
    }

    @Override
    public void useEffect(Entity e) {
        if(super.checkIfPlayer(e)){
            Player p = ((Player) e);

            double healVal = (double) (p.getStrength() + p.getEquipedWeapon().getStrengthBuff()) * ((double) this.value / 100.0);

            p.heal(Math.toIntExact(Math.round(healVal)));
        }
    }

    @Override
    public void addEffect(Entity e) {

    }

    @Override
    public void removeEffect(Entity e) {

    }
}
