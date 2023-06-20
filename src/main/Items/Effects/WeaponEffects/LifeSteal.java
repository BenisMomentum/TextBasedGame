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

        //First it checks if the entity is a player. IF it is then it heals the player according to this forumla:

        // Total Strength * (LifeStealValue / 100.0)

        if(super.checkIfPlayer(e)){

            Player p = ((Player) e);

            double healVal = (double) (p.getStrength() + p.getEquipedWeapon().getStrengthBuff()) * ((double) this.value / 100.0);

            p.heal(Math.toIntExact(Math.round(healVal)));
        }
    }

    @Override
    public void addEffect(Entity e) {
        //crickets
    }

    @Override
    public void removeEffect(Entity e) {
        //crickets
    }
}
