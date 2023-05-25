package main.Items.Effects.ArmourEffects;

import main.Entities.Entity;
import main.Entities.Player;

public class Swiftness extends ArmourEffect{

    public Swiftness(int value){
        this.value = value;
        this.type = ArmourEffectList.SWIFTNESS;
    }

    @Override
    public void useEffect(Entity e) {

    }

    @Override
    public void addEffect(Entity e) {
        if(this.checkIfPlayer(e)){
            Player p = (Player) e;
            p.addInit(this.value);
        }
    }

    @Override
    public void removeEffect(Entity e) {
        if(this.checkIfPlayer(e)){
            Player p = (Player) e;
            p.resetInit();
        }
    }
}
