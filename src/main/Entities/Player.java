package main.Entities;

import main.Items.Item;

import java.util.List;

public class Player extends Entity {

    protected int level = 0;
    protected int experience = 0;

    protected List<Item> inventory;

    private final int MAX_BASE_EXPERIENCE = 100;

    public Player(){
        super(50,0,5,5, "Player");

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



    public int takeDamage(int incomingDamage) {
        /*
    ARMOUR IS MEANT TO BE CALCULATED SUCH AS THE FOLLOWING SUCH THAT IT CAN NEVER EXCEED 50% PROTECTION

    actualDamage = (incomingDamage) * ((Armour % 51)/100)

    MAXIMUM FOR ALL STATS (STR, INIT) IS 100
     */
        if(incomingDamage <= 0) return 0;

        int actualDamage = incomingDamage * ((armour % 51) / 100);

        return actualDamage;
    }
}
