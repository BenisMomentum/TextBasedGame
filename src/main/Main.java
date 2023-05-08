package main;

import main.Entities.Monster;
import main.Entities.Player;

public class Main {

    public static void main(String[] args) {

        Monster m = new Monster(20,0,10,12,"Lizard Warrior");

        Player p = new Player();

        System.out.println(p.getStats());

        m.attack(p);

        System.out.println(p.getStats());


    }
}
