package main.Items.Effects.ArmourEffects;

import main.Entities.Entity;
import main.Entities.Player;

public class Swiftness extends ArmourEffect{

    //Buffs the player's Initiative

    public Swiftness(int value){
        this.value = value;
        this.type = ArmourEffectList.SWIFTNESS;
    }

    @Override
    public void useEffect(Entity e) {

    }

    @Override
    public void addEffect(Entity e) {
        if(this.checkIfPlayer(e)){ //Checks if its a player entity
            Player p = (Player) e; //For sake of readability, casts it to Player and assigns a local var
            p.addInit(this.value); //Adds the buffed init
        }
    }

    @Override
    public void removeEffect(Entity e) {
        if(this.checkIfPlayer(e)){ //Checks if its a player entity
            Player p = (Player) e;
            p.resetInit(); //Resets init
        }
    }
}
