package main.Items;

import main.Entities.Entity;
import main.Entities.Player;
import main.Items.Effects.ArmourEffects.ArmourEffect;
import main.Items.Effects.Effect;

import java.util.ArrayList;
import java.util.List;

public class Armour extends Item{
    protected int armour;
    protected List<ArmourEffect> effectList = new ArrayList<>();;

    /*public Armour(Rarity r, String n, int a, List<ArmourEffect> lAE){
        this(r,n,a);
        this.effectList = lAE;
    }*/

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

    public void applyAllArmourEffects(Player player){
        if(player.getEquipedArmour().equals(this)){
            for(ArmourEffect aE : player.getEquipedArmour().effectList){
                aE.addEffect(player);
            }
        }
    }

    public List<ArmourEffect> getEffects() {
        return effectList;
    }
}
