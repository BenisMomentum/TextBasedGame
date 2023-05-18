package main.Entities;

public abstract class Entity {
    protected int hp;
    protected int armour;
    protected int strength;
    protected int initiative;
    protected String name;

    public Entity(int hp, int armour, int strength, int initiative, String name) {
        this.hp = hp;
        this.armour = armour;
        this.strength = strength;
        this.initiative = initiative;
        this.name = name;
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
        return this.name + " || HP: " + this.hp + " || ARMR: " + this.armour + " || INIT: " + this.initiative;
    }
}
