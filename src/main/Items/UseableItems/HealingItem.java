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
        if(entity instanceof Player){ //Checks if the entity is indeed a player
            ((Player) entity).heal(this.healAmount); //Heals them for that amount
            System.out.println(this.name + " HEALS FOR " + this.healAmount);
        }
    }

    @Override
    public String toString() {
        return "HEAL_ITEM/" + this.name + "/" + this.rarity + "/" + this.healAmount;
    }
}
