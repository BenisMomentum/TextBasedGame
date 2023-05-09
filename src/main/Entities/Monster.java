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
        if(damage - hp <= 0){
            hp = 0;
            return;
        }

        hp -= damage;
    }

    @Override
    public String toString() {
        return "MONSTER/"
                + this.name + "/" + this.hp + "/"
                + this.strength + "/" + this.armour + "/"
                + this.initiative;
    }
}
