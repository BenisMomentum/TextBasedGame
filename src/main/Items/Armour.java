package main.Items;

import main.Entities.Entity;
import main.Entities.Player;

public class Armour extends Item{
    protected int armour;

    public Armour(Rarity r, String n) {
        super(r, n);
    }

    @Override
    public void use(Entity[] entity) {

    }
}
