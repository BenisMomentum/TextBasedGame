package main.Items;

import main.Entities.Monster;
import main.Entities.Player;

public abstract class Item {
    protected Rarity rarity;
    protected String name;

    public Item(Rarity r, String n){
        this.rarity = r;
        this.name = n;
    }

    public abstract void use();
    public abstract void use(Player p);
    public abstract void use(Monster m);

}

enum Rarity{
    COMMON(50.0),
    UNCOMMON(25.0),
    RARE(12.5),
    EPIC(5.0),
    LEGENDARY(1.5);


    Rarity(double v) {
    }
}
