package main.Items.UseableItems;

import main.Entities.Entity;
import main.Items.Rarity;
import main.Items.RegularItem;

public class UseableItem extends RegularItem {
    public UseableItem(Rarity r, String n) {
        super(r, n);
    }

    @Override
    public String toString() {
        return "USEABLE_ITEM/" + this.name + "/" + this.rarity;
    }

}
