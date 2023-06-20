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
        this.equipedWeapon = new Weapon(Rarity.COMMON,"FISTS",0); //Base level Weapon
        this.equipedArmour = new Armour(Rarity.COMMON, "NONE",0); //Base level Armour

        this.maxHP = 50;

        this.inventory = new ArrayList<>();

    }

    public Player(int hp, int armour, int strength, int initiative, String name) {
        super(hp, armour, strength, initiative, name);
    }

    public void levelUp(int recievedXP){

        int MAX_CURRENT_XP = MAX_BASE_EXPERIENCE + (30*(level - 1)); //Sets up the Max_Current_Xp with an increment of 30 per level

        /*
        Keeps levelling up so long as there's XP above the maximum.
        */


        experience += recievedXP; //Adds the recieved into the current XP

        while(experience >=0){

            if(experience >= MAX_CURRENT_XP){

                /*
                Subtracts the MAX_CURRENT_XP from experience

                then updates the player's attributes correspondingly
                */

                experience -= MAX_CURRENT_XP;

                this.strength += 2;
                this.hp += 10;
                this.maxHP += 10;
                this.initiative += 1;
                level++;

                MAX_CURRENT_XP += 30; //Updates the MAX_CURRENT_XP
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


    public void attack(Monster monster){

        /*
            First it gets the raw damage from the equiped Weapon and your actual strength
            Then IF your weapon has piercing, it ignores the armour (as it should) and treats the actualdamage as rawdamage
            ELSE, it performs a generic Armour Calc using the Monster's armour.

            THEN IF you have LIFESTEAL, you heal for a percentage of that RAW damage (aka even if the damage as reduced, you're still healing that raw amount)
            ELSE, you just continue as normal.
        */

        int rawDamage = this.strength + this.equipedWeapon.getStrengthBuff();

        int actualDamage = (this.equipedWeapon.hasEffect(WeaponEffectList.PIERCE)) ? rawDamage : Entity.genericArmourCalc(monster,rawDamage);

        System.out.println("YOU attack " + monster.getName() + " for [" + actualDamage + "] DMG with [" + this.equipedWeapon.getName() + "]!");

        monster.takeDamage(actualDamage);

        if(this.equipedWeapon.hasEffect(WeaponEffectList.LIFESTEAL)){
            this.equipedWeapon.getEffect(WeaponEffectList.LIFESTEAL).useEffect(this);
        }
    }

    public boolean take(Item t){

        /*
        First, it checks if the inventory has enough space, IF it doesn't, then it exits.

        IF the inventory isn't full, it adds the Item into the Inventory
        */

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

        /*
            First it checks if you even have the Weapon in your inventory.

            Then, IF true, it checks if you currently have your FISTS equipped.

            IF that is true, it just equips your current weapon by overwriting FISTS

            ELSE, it adds the equipped weapon to inventory.
        */

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

        //Same as the above but with Armour

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

        //Checks if the amount is > 0.
        // Then checks if the amount + your current HP is > your MaxHP
        // If it is, just sets your HP to the max, otherwise it heals you.

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
        equipedWeapon.removeAllWeaponEffects(this);
        equipedArmour.removeAllArmourEffects(this);

        //MAX HP UPDATE
        int hpDiff = this.maxHP - this.hp;//Accounts for damage recieved and doesn't provide a cheat way to keep scaling HP

        this.maxHP = 50 + (10 * (level - 1));
        this.hp = this.maxHP - hpDiff;

        //INIT HANDLING
        this.initiative = this.getREALINIT();

        //STR HANDLING
        this.strength  = this.getREALSTR();

        //ITEM MODIFIER HANDLING

        this.equipedArmour.applyAllArmourEffects(this);
        this.equipedWeapon.applyAllWeaponEffects(this);

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

    public int getREALSTR(){
        return 5 + (2 * (level - 1)); //Essentially it calculates it with this formula -->[ BASESTR + (STR_LEVEL_UP_INCREMENT * (LEVEL - 1)); ]
    }

    public void resetSTR(){
        this.strength = getREALSTR();
    }

    public void incAlign(){
        if(this.alignment + 1 <= MAX_ALIGN){
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
