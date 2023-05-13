package main.Items;

import main.Entities.Entity;
import main.Entities.Monster;

public class Weapon extends Item{
    protected int strengthBuff;

    public Weapon(Rarity r, String n, int sBuff){
        super(r,n);
        this.strengthBuff = sBuff;
    }

    @Override
    public void use(Entity entity) {

    }

    public int getStrengthBuff() {
        return strengthBuff;
    }

    @Override
    public String toString() {
        return "WEAPON/" + this.name
                + "/" + this.rarity
                + "/" + this.strengthBuff
                + "";
    }
}
