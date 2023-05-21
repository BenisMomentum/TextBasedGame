package main.Items.UseableItems;

import main.Entities.Entity;
import main.Entities.Player;
import main.Items.Rarity;

public class HealingItem extends UseableItem{

    protected int healAmount;

    public HealingItem(Rarity r, String n, int healAmount) {
        super(r, n);
        this.healAmount = healAmount;
    }

    @Override
    public void use(Entity entity) {
        if(entity instanceof Player){
            ((Player) entity).heal(this.healAmount);
            System.out.println(this.name + " HEALS FOR " + this.healAmount);
        }
    }

    @Override
    public String toString() {
        return "HEAL_ITEM/" + this.name + "/" + this.rarity + "/" + this.healAmount;
    }
}
