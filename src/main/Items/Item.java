package main.Items;

import main.Entities.Entity;

public abstract class Item<T extends Entity> {
    protected Rarity rarity;
    protected String name;

    public Item(Rarity r, String n){
        this.rarity = r;
        this.name = n;
    }

    public abstract void use(T... entity); //FLAG: Heap pollution possible
    //NOTE: Trying to find a better way to be able to use one method for multiple

    public Rarity getRarity() {
        return rarity;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ITEM " + this.name
                + " " + this.rarity
                + " ";
    }
}

