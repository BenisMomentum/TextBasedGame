package main.Items;

import main.Entities.Entity;

public class Armour extends Item{
    protected int armour;

    public Armour(Rarity r, String n) {
        super(r, n);
    }

    @Override
    public void use(Entity[] entity) {

    }

    @Override
    public String toString() {
        return "ARMOUR " + this.name
                + " " + this.rarity
                + " " + this.armour
                + " ";
    }
}
