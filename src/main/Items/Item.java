package main.Items;

import main.Entities.Entity;
import main.Entities.Monster;
import main.Entities.Player;

public abstract class Item<T extends Entity> {
    protected Rarity rarity;
    protected String name;

    public Item(Rarity r, String n){
        this.rarity = r;
        this.name = n;
    }

    public abstract void use(T... entity); //FLAG: Heap pollution possible
    //NOTE: Trying to find a better way to be able to use one method for multiple

}

