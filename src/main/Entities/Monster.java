package main.Entities;

public class Monster extends Entity{

    public Monster(int hp, int armour, int strength, int initiative, String name) {
        super(hp, armour, strength, initiative, name);
    }

    public void attack(Player p){
        System.out.println(name + " attacks PLAYER!");
        p.hp -= p.takeDamage(strength);
    }

    public void takeDamage(int damage){
        if(damage <= 0) return;

    }

}
