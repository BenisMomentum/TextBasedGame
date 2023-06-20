package main.Entities;

import main.Items.Effects.StatusEffects.StatusEffect;

public class Monster extends Entity{

    protected final int maxHp;

    public Monster(String name, int hp, int armour, int strength, int initiative) {
        super(hp, armour, strength, initiative, name);
        this.maxHp = hp;
    }

    public void attack(Player p){
        //Just throws hands against the player

        System.out.println(name + " attacks PLAYER!");
        p.takeDamage(strength);
    }

    public void takeDamage(int damage){

        /*
            First, checks if damage is less than equal to 0, if it is it just returns.
            Then IF the damage is greater than the current HP, then it just sets the hp = 0;
            ELSE, it just subtracts hp from damage.

        */

        if(damage <= 0) return;
        if(this.hp - damage <= 0){
            hp = 0;
            return;
        }

        hp -= damage;
    }

    public void rejuvenate(int amount){
        //just the Player.heal function with a different name.

        if(amount + hp > maxHp){
            this.hp = maxHp;
        }
        else{
            this.hp += amount;
        }
    }

    public void addEffect(StatusEffect e){
        //Just adds a status effect to the statusEffects ArrayList

        this.statusEffects.add(e);
    }

    @Override
    public String toString() {

        // MONSTER/name/HP/ARMOUR/STR/INIT

        String s = "MONSTER/"
                + this.name + "/" + this.hp + "/"
                + this.armour + "/" + this.strength + "/"
                + this.initiative + "/";
        return s;
    }


}
