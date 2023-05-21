package main.Items.UseableItems;

import main.Entities.Entity;
import main.Items.Rarity;
import main.Items.RegularItem;

public class UseableItem extends RegularItem {

    /*
    Meant to be an item that is consumed/USED during battle.
     */

    public UseableItem(Rarity r, String n) {
        super(r, n);
    }

    @Override
    public void use(Entity entity) {
        System.out.println(this.name + " does something!");
    }

}
