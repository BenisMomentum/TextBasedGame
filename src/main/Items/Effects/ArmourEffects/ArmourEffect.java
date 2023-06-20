package main.Items.Effects.ArmourEffects;

import main.Entities.Entity;
import main.Entities.Player;
import main.Items.Effects.Effect;

public abstract class ArmourEffect implements Effect {

    //Base Armour Effect class

    protected int value; //The value of what exactly is going to be adjusted
    protected ArmourEffectList type;

    protected boolean checkIfPlayer(Entity e){
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArmourEffect)) return false;

        ArmourEffect that = (ArmourEffect) o;

        if (value != that.value) return false;
        return type != null ? type.equals(that.type) : that.type == null;
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

    public ArmourEffectList getType() {
        return type;
    }
}
