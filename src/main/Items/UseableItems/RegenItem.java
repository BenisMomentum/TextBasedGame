package main.Items.UseableItems;

import main.Entities.Entity;
import main.Entities.Player;
import main.Items.Effects.StatusEffects.Regen;
import main.Items.Rarity;

public class RegenItem extends UseableItem{
    protected int regenAmount;
    protected int duration;
    public RegenItem(Rarity r, String n, int d, int rA) {
        super(r, n);
        this.duration = Math.abs(d);
        this.regenAmount = Math.abs(rA);
    }

    @Override
    public void use(Entity entity) {
        if(entity instanceof Player){
            entity.getStatusEffects().add(new Regen(this.duration,this.regenAmount));
        }
    }

    @Override
    public String toString() {
        return "REGEN_ITEM/" + this.name + "/" + this.rarity + "/" + this.regenAmount + "HP For " + this.duration + " turns";
    }
}
