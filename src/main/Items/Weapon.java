package main.Items;

import main.Entities.Entity;
import main.Items.Effects.ArmourEffects.ArmourEffect;
import main.Items.Effects.WeaponEffects.WeaponEffect;
import main.Items.Effects.WeaponEffects.WeaponEffectList;

import java.util.ArrayList;
import java.util.List;

public class Weapon extends Item{
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
        if(effectList.size() == 0) return false;

        for(int i = 0; i < effectList.size(); i++){
            if(effectList.get(0).getType().equals(wE)) return true;
        }

        return false;
    }

    public WeaponEffect getEffect(WeaponEffectList wE){
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

    public List<WeaponEffect> getEffects() {
        return effectList;
    }
}
