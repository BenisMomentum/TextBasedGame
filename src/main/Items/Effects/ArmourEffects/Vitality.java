package main.Items.Effects.ArmourEffects;

import main.Entities.Entity;
import main.Entities.Player;
import main.Items.Effects.Effect;

public class Vitality extends ArmourEffect {
    private final String name = "VITALITY";

    public Vitality(int armourValBoost){
        this.value = armourValBoost;
    }

    @Override
    public void useEffect(Entity e) {

    }

    @Override
    public void addEffect(Entity e) {
        if(e != null){
            if(e instanceof Player){
                Player p = (Player) e;
                if(!p.getEquipedArmour().getEffects().contains(this)){
                    p.addMAXHP(this.value);
                }
            }
        }
    }

    @Override
    public void removeEffect(Entity e) {
        if(e != null){
            if(e instanceof Player){
                ((Player) e).resetMAXHP();
            }
        }
    }
}
