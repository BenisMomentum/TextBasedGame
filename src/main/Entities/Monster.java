package main.Entities;

public class Monster extends Entity{

    public Monster(int hp, int armour, int strength, int initiative, String name) {
        super(hp, armour, strength, initiative, name);
    }

    public void attack(Player p){
        System.out.println(name + " attacks player!");
        p.hp -= p.takeDamage(strength);
    }

}
