package main.Items.UseableItems;

import main.Entities.Entity;
import main.Entities.Player;
import main.Items.Effects.StatusEffects.Regen;
import main.Items.Rarity;

public class RegenItem extends DurationalItem{

    public RegenItem(Rarity r, String n, int d, int rA) {
        super(r,n,d,rA);
    }

    @Override
    public void use(Entity entity) {
        if(entity instanceof Player){
            entity.getStatusEffects().add(new Regen(this.duration,this.value));
        }
    }

    @Override
    public String toString() {
        return "REGEN_ITEM/" + this.name + "/" + this.rarity + "/" + this.value + "HP For " + this.duration + " turns";
    }

}
