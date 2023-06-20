package main.Items;

import main.Entities.Entity;
import main.Entities.Player;
import main.Items.Effects.ArmourEffects.ArmourEffect;
import main.Items.Effects.Effect;

import java.util.ArrayList;
import java.util.List;

public class Armour extends Item{

    //Armour class, meant to be equipped to the player.

    protected int armour;
    protected List<ArmourEffect> effectList = new ArrayList<>(); //Effects the armour may have

    public Armour(Rarity r, String n, int armour) {
        super(r, n);
        this.armour = armour;
    }

    public Armour(Rarity r, String n) {
        super(r, n);
        this.armour = 0;
    }

    @Override
    public void use(Entity entity) {
        if(!(entity instanceof Player)){
            return;
        }

        if(entity.getArmour() == 0 &&
                this.equals(((Player) entity).getEquipedArmour())){
            entity.setArmour(this.armour);
        }
    }

    @Override
    public String toString() {
        String s = "ARMOUR/" + this.name
                + "/" + this.rarity
                + "/" + this.armour
                + "/Effects: ";

        for(ArmourEffect aE : this.effectList){
            s += aE.toString() + " ";
        }

        return s;
    }

    public int getArmour() {
        return armour;
    }

    public void applyAllArmourEffects(Player player){ //Applies all armour effects to the player (Vitality and Swiftness)
        if(player.getEquipedArmour().equals(this)){ //Checks first if its equipped tho
            for(ArmourEffect aE : player.getEquipedArmour().effectList){
                aE.addEffect(player);
            }
        }
    }

    public void removeAllArmourEffects(Player player){ //Removes all armour effects to the player (Vitality and Swiftness)
        if(player.getEquipedArmour().equals(this)){ //Checks first if its equipped tho
            for(ArmourEffect aE : player.getEquipedArmour().effectList){
                aE.removeEffect(player);
            }
        }
    }

    public List<ArmourEffect> getEffects() {
        return effectList;
    }
}
