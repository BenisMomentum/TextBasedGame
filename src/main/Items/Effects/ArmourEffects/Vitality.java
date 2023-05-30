package main.Items.Effects.ArmourEffects;

import main.Entities.Entity;
import main.Entities.Player;

public class Vitality extends ArmourEffect {

    public Vitality(int armourValBoost){
        this.value = armourValBoost;
        this.type = ArmourEffectList.VITALITY;
    }

    @Override
    public void useEffect(Entity e) {

    }

    @Override
    public void addEffect(Entity e) {
        if(this.checkIfPlayer(e)){
            Player p = (Player) e;
            if(p.getEquipedArmour().getEffects().contains(this)){
                p.addMAXHP(this.value);
                p.setHp(p.getHp() + this.value);
            }
        }
    }

    @Override
    public void removeEffect(Entity e) {
        if(this.checkIfPlayer(e)){
            ((Player) e).resetMAXHP();
        }
    }
}
