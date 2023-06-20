package main.Items.Effects.WeaponEffects;

import main.Entities.Entity;
import main.Entities.Player;
import main.Items.Effects.ArmourEffects.ArmourEffectList;
import main.Items.Effects.Effect;

public abstract class WeaponEffect implements Effect {
    protected int value;
    protected WeaponEffectList type;

    protected boolean checkIfPlayer(Entity e){

        //Checks if e is null, then returns a boolean value on if its a Player or not

        if(e != null){
            return e instanceof Player;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.type + "=" + this.value;
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public int getValue() {
        return value;
    }

    public WeaponEffectList getType() {
        return type;
    }

}
