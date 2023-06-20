package main.Entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Boss extends Monster{

    protected interface AttackMove{
        void attack(Player p);
    }

    protected List<AttackMove> moveList = new LinkedList<AttackMove>();

    /*
        Bosses will have a few attacks available. Some will be defensive (Heal, buffs, etc.)
        Most will be offensive, applying some various effects and such.

        All will have a random chance to miss
    */

    public Boss(String name, int hp, int armour, int strength, int initiative) {
        super(name, hp, armour, strength, initiative);
    }

    @Override
    public void attack(Player p) {
        Random rand = new Random();

        int move = rand.nextInt(moveList.size()); //Determines random move

        if(rand.nextInt(6) != 1){ //Gives the boss a chance to miss
            moveList.get(move).attack(p);
        }else{
            System.out.println(this.name + " missed!");
        }
    }
}

