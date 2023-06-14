package main.Entities;

import main.Items.Effects.StatusEffects.StatusEffect;

public class Monster extends Entity{

    protected final int maxHp;

    public Monster(String name, int hp, int armour, int strength, int initiative) {
        super(hp, armour, strength, initiative, name);
        this.maxHp = hp;
    }

    public void attack(Player p){
        System.out.println(name + " attacks PLAYER!");
        p.takeDamage(strength);
    }

    public void takeDamage(int damage){
        if(damage <= 0) return;
        if(this.hp - damage <= 0){
            hp = 0;
            return;
        }

        hp -= damage;
    }

    public void rejuvenate(int amount){
        if(amount + hp > maxHp){
            this.hp = maxHp;
        }
        else{
            this.hp += amount;
        }
    }

    public void addEffect(StatusEffect e){
        this.statusEffects.add(e);
    }

    @Override
    public String toString() {
        String s = "MONSTER/"
                + this.name + "/" + this.hp + "/"
                + this.armour + "/" + this.strength + "/"
                + this.initiative + "/";
        for(int i = 0; i < this.statusEffects.size(); i++){

        }


        return s;
    }


}
