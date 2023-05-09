package main;

import main.Entities.Monster;
import main.Entities.Player;
import main.Items.Item;
import main.Items.Rarity;
import main.Items.Weapon;
import main.Location.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Monster m = new Monster(20,0,10,12,"Lizard Warrior");

        Weapon cringeSword = new Weapon(Rarity.EPIC, "cringeSword",10);

        List<Item> itemList = new ArrayList<>();

        itemList.add(cringeSword);

        Map<String, Integer> exits = new HashMap<>();
        exits.put("EAST",1);

        Location locale = new Location(0,"You are sitting in front of a computer learning Java",exits,itemList,m);

        System.out.println(locale.toWriteable());

    }
}
