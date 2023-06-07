package main.Items.UseableItems;

import main.Entities.Entity;
import main.Entities.Player;
import main.Items.Effects.StatusEffects.Adrenaline;
import main.Items.Effects.StatusEffects.Rage;
import main.Items.Rarity;

public class RageItem extends DurationalItem{

    public RageItem(Rarity r, String n, int v, int d) {
        super(r, n, v, d);
    }

    @Override
    public void use(Entity entity) {
        if(entity instanceof Player){
            entity.getStatusEffects().add(new Rage(super.duration,super.value));
        }
    }

    @Override
    public String toString() {
        return "RAGE_ITEM/" + this.name + "/" + this.rarity + "/" + this.value + "INIT For " + this.duration + " turns";
    }
}
