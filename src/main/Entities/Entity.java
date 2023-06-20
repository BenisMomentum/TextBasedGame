package main.Entities;

import main.Items.Effects.StatusEffects.*;
import main.TextConstants;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    protected int hp;
    protected int armour;
    protected int strength;
    protected int initiative;
    protected String name;
    protected List<StatusEffect> statusEffects;

    public Entity(int hp, int armour, int strength, int initiative, String name) {
        this.hp = hp;
        this.armour = armour;
        this.strength = strength;
        this.initiative = initiative;
        this.name = name;
        this.statusEffects = new ArrayList<>();
    }

    public static  int genericArmourCalc(Entity e, int incomingDamage){
        double armourCalc = (1.0 - (((double) (e.getArmour() % 51) )/ 100.0)); //Setting a variable is much easier than re-doing that calc again for the sake of the short if-else

        int actualDamage = Math.toIntExact(Math.round(

                (double) incomingDamage * ( armourCalc== 0.0 ? 1.0 : armourCalc)

        ));
        return actualDamage;
    }


    public int getArmour() {
        return armour;
    }

    public void setArmour(int armour) {
        this.armour = Math.min(armour, 50);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ENTITY/\""
                + this.name + "/\"" + this.hp + "/"
                + this.strength + "/" + this.armour + "/"
                + this.initiative;
    }

    public String getStats(){

        // MonsterName || HP || ARMR || INIT
        // Then it displays Status Effects as well

        StringBuilder s = new StringBuilder(this.name + " || HP: " + this.hp + " || ARMR: " + this.armour + " || INIT: " + this.initiative + "\n");
        s.append("Status Effects \\/ \n");

        if(this.statusEffects.size() != 0){
            for(int i = 0; i < statusEffects.size(); i++){
                s.append(statusEffects.get(i).toHumanReadable()).append("\n");
            }
        }

        return s.toString();
    }

    public List<StatusEffect> getStatusEffects() {
        return statusEffects;
    }

    public StatusEffect getStatusByType(StatusEffectList type){
        for(StatusEffect e : statusEffects){
            if(type.equals(e.getType())){
                return e;
            }
        }
        return null;
    }

    public boolean addStatusEffect(StatusEffect e){
        /*
        * Essentially adding the same effect should double the efficacy and refresh the duration.
        * AKA if Bleed (4 stren, 2 duration) has 1 duration left, and bleed is applied again
        * Bleed will turn into the new duration, from either the new or the old duration
        * Whichever is higher.
        * */

        if(getStatusByType(e.getType()) != null){
            StatusEffect oldEff = getStatusByType(e.getType());

            int d = Math.max(e.getDuration(), oldEff.getDuration());
            int s = oldEff.getStrength() + e.getStrength();

            statusEffects.remove(oldEff);

            switch(e.getType()){
                case ADRENALINE -> statusEffects.add(new Adrenaline(d,s));
                case BLEED -> statusEffects.add(new Bleed(d,s));
                case RAGE -> statusEffects.add(new Rage(d,s));
                case REGEN -> statusEffects.add(new Regen(d,s));
            }

        }else{
            statusEffects.add(e);
        }

        return true;
    }

    protected String getEffectString(){
        if(this.statusEffects.size() == 0) return "";

        String s = "";
        int loopLen = Math.max(TextConstants.EFFECT_BLEED.length, TextConstants.EFFECT_REGEN.length);

        /*
                PSEUDOCODE:

                eff1[0] \t eff2[0] \t ....
                eff1[1] \t eff2[1] \t ....

                ^ What I want.

                THEORY:

                for every effect{
                    switch(effect class){
                        case BLEED:
                           IF it contains '=' -> s+= bleed_eff[i].replace("=", eff.strength)
                           ELSE -> s +=
                           break;
                        case REGEN:
                           IF it contains '=' -> s+= bleed_eff[i].replace("=", eff.strength)
                           ELSE -> s +=


                    }
                }


                /*
                    Essentially what this does is that it prints out the various effects in an effect below the player stats

         */



        for(int i = 0; i < loopLen; i++){
            for(StatusEffect sE : this.statusEffects){
                if(sE instanceof Regen){
                    s += (TextConstants.EFFECT_REGEN[i].contains("="))
                            ? TextConstants.EFFECT_REGEN[i].replace("=",String.valueOf(sE.getStrength()))
                            : TextConstants.EFFECT_REGEN[i];
                } else if(sE instanceof Bleed){
                    s += (TextConstants.EFFECT_BLEED[i].contains("="))
                            ? TextConstants.EFFECT_BLEED[i].replace("=",String.valueOf(sE.getStrength()))
                            : TextConstants.EFFECT_BLEED[i];
                } else if(sE instanceof Adrenaline){
                    s+= (TextConstants.EFFECT_SPEED[i].contains("="))
                            ? TextConstants.EFFECT_SPEED[i].replace("=",String.valueOf(sE.getStrength()))
                            : TextConstants.EFFECT_SPEED[i];

                }
                s += "\t";
            }
            s += "\n";
        }
        return s;
    }

}
