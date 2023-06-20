package main.Items;

import main.Entities.Entity;
import main.Entities.Player;
import main.Items.Effects.ArmourEffects.ArmourEffect;
import main.Items.Effects.WeaponEffects.WeaponEffect;
import main.Items.Effects.WeaponEffects.WeaponEffectList;

import java.util.ArrayList;
import java.util.List;

public class Weapon extends Item{

    /*
        Weapons meant to be used by the player
    */

    protected int strengthBuff;
    protected List<WeaponEffect> effectList = new ArrayList<>();

    public Weapon(Rarity r, String n, int sBuff){
        super(r,n);
        this.strengthBuff = sBuff;
    }

    @Override
    public void use(Entity entity) {

    }

    public boolean hasEffect(WeaponEffectList wE){

        /*
            First it checks if the size == 0, if it is it just returns O(1)[?]
            ELSE, it loops through all the effects and see if the type equals the type provided.
            Returns true if it is, returns false if it isn't.
        */

        if(effectList.size() == 0) return false;

        for(int i = 0; i < effectList.size(); i++){
            if(effectList.get(0).getType().equals(wE)) return true;
        }

        return false;
    }

    public WeaponEffect getEffect(WeaponEffectList wE){

        /*
            First it checks if the size == 0, if it is it just returns O(1)[?]
            If it is more than 0, then it grabs the effect based on the list, rather than having to search manually.
        */

        if(effectList.size() == 0) return null;

        for(WeaponEffect w : this.effectList){
            if(w.getType().equals(wE)) return w;
        }
        return null;
    }

    public int getStrengthBuff() {
        return strengthBuff;
    }

    @Override
    public String toString() {
        String s = "WEAPON/" + this.name
                + "/" + this.rarity
                + "/" + this.strengthBuff
                + "/Effects: ";

        for(WeaponEffect aE : this.effectList){
            s += aE.toString() + " ";
        }

        return s;
    }

    public void applyAllWeaponEffects(Player player){ //Applies all armour effects to the player (Vitality and Swiftness)
        if(player.getEquipedWeapon().equals(this)){ //Checks first if its equipped tho
            for(WeaponEffect aE : player.getEquipedWeapon().effectList){
                aE.addEffect(player);
            }
        }
    }

    public void removeAllWeaponEffects(Player player){ //Removes all armour effects to the player (Vitality and Swiftness)
        if(player.getEquipedWeapon().equals(this)){ //Checks first if its equipped tho
            for(WeaponEffect aE : player.getEquipedWeapon().effectList){
                aE.removeEffect(player);
            }
        }
    }

    public List<WeaponEffect> getEffects() {
        return effectList;
    }
}
