package main.Items;

import main.Entities.Entity;
import main.Entities.Player;

public class Armour extends Item{
    protected int armour;

    public Armour(Rarity r, String n) {
        super(r, n);
        this.armour = 0;
    }

    @Override
    public void use(Entity entity) {
        if(!(entity instanceof Player)){
            return;
        }

        if(entity.getArmour() == 0 &&
                this.equals(((Player) entity).getEquipedArmour())){
            entity.setArmour(this.armour);
        }
    }

    public Armour(Rarity r, String n, int armour) {
        super(r, n);
        this.armour = armour;
    }

    @Override
    public String toString() {
        return "ARMOUR/" + this.name
                + "/" + this.rarity
                + "/" + this.armour
                + "";
    }
}
