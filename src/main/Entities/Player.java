package main.Entities;

import main.Items.Armour;
import main.Items.Item;
import main.Items.Rarity;
import main.Items.Weapon;
import main.TextConstants;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {

    protected int level = 0;
    protected int experience = 0;

    protected List<Item> inventory; //MAX SIZE?
    protected Weapon equipedWeapon;
    protected Armour equipedArmour;


    private final int MAX_BASE_EXPERIENCE = 100;

    public Player(){
        super(50,0,5,5, "Player");
        this.equipedWeapon = new Weapon(Rarity.COMMON,"FISTS",0);

        this.inventory = new ArrayList<>();

    }

    public Player(int hp, int armour, int strength, int initiative, String name) {
        super(hp, armour, strength, initiative, name);
    }

    public void levelUp(int recievedXP){

        int MAX_CURRENT_XP;

        while(recievedXP >=0){
            MAX_CURRENT_XP = MAX_BASE_EXPERIENCE + (30*level);
            if(experience + recievedXP >= MAX_CURRENT_XP){
                level++;
                experience = (recievedXP -= MAX_CURRENT_XP);

                this.strength += 2;
                this.hp += 10;
                this.initiative += 1;
            }else{
                experience += recievedXP;
            }
        }
    }

    //BATTLE NECESSITIES

    public int takeDamage(int incomingDamage) {
        /*
    ARMOUR IS MEANT TO BE CALCULATED SUCH AS THE FOLLOWING SUCH THAT IT CAN NEVER EXCEED 50% PROTECTION

    actualDamage = (incomingDamage) * ((Armour % 51)/100)

    MAXIMUM FOR ALL STATS (STR, INIT) IS 100
     */
        if(incomingDamage <= 0) return 0;

        double armourCalc = ((double) (armour % 51) / 100.0); //Setting a variable is much easier than re-doing that calc again for the sake of the short if-else

        int actualDamage = Math.toIntExact(Math.round(

                (double) incomingDamage * ( armourCalc== 0.0 ? 1.0 : armourCalc)

                ));
        System.out.println("You get hit for [" + actualDamage + "] DMG!");

        return actualDamage;
    }

    public String getStats(){
        String s = TextConstants.EQUALS_SEPERATOR + "\nPLAYER STATS: \n" + TextConstants.EQUALS_SEPERATOR + "\n"
                + "HEALTH - " + this.hp + "HP\n"
                + "STRENGTH - " + this.strength + "STR\n"
                + "LEVEL/EXPERIENCE - " + this.level + "LVL / " + this.experience + "XP\n"
                + "ARMOUR - " + this.armour + "/50 ARM\n"
                + "INITIATIVE - " + this.initiative + "INIT\n"
                + TextConstants.EQUALS_SEPERATOR;

        return s;
    }

    public void attack(Monster monster){

        int actualDamage = this.strength + this.equipedWeapon.getStrengthBuff();

        System.out.println("YOU attack " + monster.getName() + " for [" + actualDamage + "] DMG with [" + this.equipedWeapon.getName() + "]!");

        monster.takeDamage(actualDamage);
    }

    public boolean take(Item t){
        if(inventory.size() > 30){
            System.out.println("INVETORY is full!");
            return false;
        }
        return inventory.add(t);
    }

    public boolean switchWeapon(Weapon w){
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
}
