package main.Items.UseableItems;

import main.Entities.Entity;
import main.Entities.Player;
import main.Items.Effects.StatusEffects.Adrenaline;
import main.Items.Effects.StatusEffects.Regen;
import main.Items.Rarity;

public class SpeedItem extends DurationalItem{

    public SpeedItem(Rarity r, String n, int speedbuff, int turns) {
        super(r, n,speedbuff,turns);
    }

    @Override
    public void use(Entity entity) {
        if(entity instanceof Player){ //Checks if its a Player
            entity.getStatusEffects().add(new Adrenaline(super.duration,super.value)); //Add the Adrenaline status
        }
    }

    @Override
    public String toString() {
        return "SPEED_ITEM/" + this.name + "/" + this.rarity + "/" + this.value + "INIT For " + this.duration + " turns";
    }
}
