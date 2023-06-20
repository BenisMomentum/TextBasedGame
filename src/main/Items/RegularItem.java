package main.Items;

import main.Entities.Entity;

public class RegularItem extends Item{

    //Standard item, cant be used. (Idk why this is here.)

    public RegularItem(Rarity r, String n) {
        super(r, n);
    }

    @Override
    public void use(Entity entity) {
        System.out.println("Cannot USE " + this.name);
    }
}
