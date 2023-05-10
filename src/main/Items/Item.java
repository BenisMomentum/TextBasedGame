package main.Items;

import main.Entities.Entity;

public abstract class Item{
    protected Rarity rarity;
    protected String name;

    public Item(Rarity r, String n){
        this.rarity = r;
        this.name = n;
    }

    public abstract void use(Entity entity); //FLAG: Heap pollution possible
    //NOTE: Found a better way, just use Entity

    public Rarity getRarity() {
        return rarity;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ITEM/" + this.name
                + "/" + this.rarity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (rarity != item.rarity) return false;
        return name.equals(item.name);
    }

    @Override
    public int hashCode() {
        int result = rarity.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}

