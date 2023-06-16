package main.Entities;

import main.Colors;
import main.Items.Armour;
import main.Items.Effects.StatusEffects.*;
import main.Items.Effects.WeaponEffects.LifeSteal;
import main.Items.Effects.WeaponEffects.WeaponEffectList;
import main.Items.Item;
import main.Items.Rarity;
import main.Items.UseableItems.SpeedItem;
import main.Items.Weapon;
import main.TextConstants;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {

    protected int level = 1;
    protected int experience = 0;
    protected int maxHP;

    protected List<Item> inventory; //MAX SIZE?
    protected Weapon equipedWeapon;
    protected Armour equipedArmour;

    private int alignment = 0;
    private final int MAX_ALIGN = 10;

    private final int MAX_BASE_EXPERIENCE = 100;

    public Player(){
        super(50,0,5,5, "Player");
        this.equipedWeapon = new Weapon(Rarity.COMMON,"FISTS",0);
        this.equipedArmour = new Armour(Rarity.COMMON, "NONE",0);

        this.maxHP = 50 + (10 * (level - 1));

        this.inventory = new ArrayList<>();

    }

    public Player(int hp, int armour, int strength, int initiative, String name) {
        super(hp, armour, strength, initiative, name);
    }

    public void levelUp(int recievedXP){

        int MAX_CURRENT_XP;
        experience += recievedXP;

        while(experience >=0){
            MAX_CURRENT_XP = MAX_BASE_EXPERIENCE + (30*(level - 1));

            if(experience >= MAX_CURRENT_XP){
                experience -= MAX_CURRENT_XP;

                this.strength += 2;
                this.hp += 10;
                this.maxHP += 10;
                this.initiative += 1;
                level++;
            }else{
                return;
            }
        }
    }

    //BATTLE NECESSITIES

    public void takeDamage(int incomingDamage) {
        /*
    ARMOUR IS MEANT TO BE CALCULATED SUCH AS THE FOLLOWING SUCH THAT IT CAN NEVER EXCEED 50% PROTECTION

    actualDamage = (incomingDamage) * ((Armour % 51)/100)

    MAXIMUM FOR ALL STATS (STR, INIT) IS 100
     */
        if(incomingDamage <= 0) {
            System.out.println("You get hit for [0] DMG, like a wet noodle");
            return;
        }

        int actualDamage = Entity.genericArmourCalc(this,incomingDamage);

        System.out.println("You get hit for [" + actualDamage + "] DMG!");

        this.hp -= actualDamage;
    }


    public String getStats(){
        this.updateMaxes();

        String s = TextConstants.EQUALS_SEPERATOR + "\nPLAYER STATS: \n" + TextConstants.EQUALS_SEPERATOR + "\n"
                + Colors.GREEN_BRIGHT + "HEALTH - " + this.hp + "/" + this.maxHP + "HP" + Colors.RESET + "   |   "
                + Colors.PURPLE_BOLD_BRIGHT + "LEVEL/EXPERIENCE - " + this.level + "LVL / " + this.experience + "XP" + Colors.RESET + "\n"
                + Colors.RED_BRIGHT + "STRENGTH - " + this.strength + "STR" + Colors.RESET + "   |   "
                + Colors.BLUE_BRIGHT + "ARMOUR - " + (this.armour + this.equipedArmour.getArmour())+ "/50 ARM " + Colors.RESET + "\n"
                + Colors.YELLOW_BRIGHT + "INITIATIVE - " + this.initiative + "INIT"  + Colors.RESET + "\n"
                + "WEAPON - " + this.equipedWeapon.getName() + " - " + Colors.RED_BRIGHT + this.equipedWeapon.getStrengthBuff() + "STR" + Colors.RESET + "\n"
                + "ARMOUR - " + Colors.BLUE_BRIGHT + this.equipedArmour.getName() + Colors.RESET +"\n"
                + "ALIGNTMENT - " + ((alignment > 5) ? "HONEST" : "SUB-OPTIMAL") + "\n"
                + "EFFECTS: \n" + this.getEffectString() + "\n"
                + TextConstants.EQUALS_SEPERATOR + "\n";
        return s;
    }

    private String getEffectString(){
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

    public void attack(Monster monster){

        int rawDamage = this.strength + this.equipedWeapon.getStrengthBuff();

        int actualDamage = (this.equipedWeapon.hasEffect(WeaponEffectList.PIERCE)) ? rawDamage : Entity.genericArmourCalc(monster,rawDamage);

        System.out.println("YOU attack " + monster.getName() + " for [" + actualDamage + "] DMG with [" + this.equipedWeapon.getName() + "]!");

        monster.takeDamage(actualDamage);

        if(this.equipedWeapon.hasEffect(WeaponEffectList.LIFESTEAL)){
            this.equipedWeapon.getEffect(WeaponEffectList.LIFESTEAL).useEffect(this);
        }
    }

    public boolean take(Item t){
        if(inventory.size() > 30){
            System.out.println("INVETORY is full!");
            return false;
        }

        try {
            System.out.println("Getting " + t.getName());
            System.out.print("Almost.");

            Thread.sleep(500L);
            System.out.print(".");
            Thread.sleep(500L);
            System.out.print(".");
            Thread.sleep(500L);
            System.out.println("Got it!");
            Thread.sleep(500L);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.add(t);

        return true;
    }

    public boolean equip(Weapon w){
        if(inventory.contains(w)){

            if(!equipedWeapon.equals(new Weapon(Rarity.COMMON,"FISTS",0))){
                inventory.add(equipedWeapon);
            }
             //FLAG: Potentially could cause problem where it doesn't duplicate the equiped weapon but instead your current weapon gets overwritten by the new one
            equipedWeapon = w;
            inventory.remove(w);

            return true;
        }
        return false;
    }

    public boolean equip(Armour a){
        if(inventory.contains(a)){
            if(!equipedArmour.equals(new Armour(Rarity.COMMON, "NONE",0))){ //Handles it to make sure that an item that ISNT the base is being added BACK to the inventory as it is unequipped
                inventory.add(equipedArmour);
            }
            equipedArmour = a;
            inventory.remove(a);

            equipedArmour.applyAllArmourEffects(this);

            return true;
        }
        return false;
    }

    public void heal(int amount){
        if(amount > 0){
            if(amount + this.hp <= this.getMAXHP()){
                this.hp += amount;
            }else{
                this.hp = this.getMAXHP();
            }

        }
    }


    //STANDARDS

    @Override
    public String toString() {
        return this.getStats();
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    public Weapon getEquipedWeapon() {
        return equipedWeapon;
    }

    public void setEquipedWeapon(Weapon equipedWeapon) {
        this.equipedWeapon = equipedWeapon;
    }

    public Armour getEquipedArmour() {
        return equipedArmour;
    }

    public void setEquipedArmour(Armour equipedArmour) {
        this.equipedArmour = equipedArmour;
    }

    //MAX STAT HANDLING

    private void updateMaxes(){
        //MAX HP UPDATE
        int hpDiff = this.maxHP - this.hp;//Accounts for damage recieved and doesn't provide a cheat way to keep scaling HP

        this.maxHP = 50 + (10 * (level - 1));
        this.hp = this.maxHP - hpDiff;

        //INIT HANDLING
        this.initiative = this.getREALINIT();


        //ITEM MODIFIER HANDLING
        this.equipedArmour.applyAllArmourEffects(this);

        //STATUS EFFECTS HANDLING
        for(StatusEffect e : statusEffects){
            if(e instanceof Adrenaline){
                this.initiative += e.getStrength();
            }else if(e instanceof Rage){
                this.strength += e.getStrength();
            }
        }

    }

    public int getMAXHP(){
        return this.maxHP;
    }

    public void addMAXHP(int val){this.maxHP += val;} //Capability of subtracting as well, as to avoid multiple methods.

    public void resetMAXHP(){this.maxHP = this.getMAXHP();}

    public int getREALINIT(){ //Gets the base initiative
        return 5 + this.level - 1;
    }

    public void addInit(int value){ //Initiative/Speed is not as important as the other stats so its fine if it goes into the negative
        this.initiative += value;
    }

    public void resetInit(){
        this.initiative = this.getREALINIT();
    }

    public void incAlign(){
        if(this.alignment + 1 < MAX_ALIGN){
            this.alignment++;
        }
    }

    public int getAlignment(){
        return alignment;
    }

    public int getMAXAlign(){
        return MAX_ALIGN;
    }

    public void decAlign(){
        this.alignment--;
    }

}
