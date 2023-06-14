package main.Entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Boss extends Monster{

    protected interface AttackMove{
        void attack(Player p);
    }

    protected List<AttackMove> moveList = new LinkedList<AttackMove>();

    public Boss(String name, int hp, int armour, int strength, int initiative) {
        super(name, hp, armour, strength, initiative);
    }

    @Override
    public void attack(Player p) {
        Random rand = new Random();

        int move = rand.nextInt(moveList.size());

        moveList.get(move).attack(p);
    }
}

