package main;

import main.Entities.Monster;
import main.Items.*;
import main.Location.Location;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Monster m = new Monster("Lizard Warrior", 20,0,10,12);

        Weapon cringeSword = new Weapon(Rarity.EPIC, "cringeSword",10);

        List<Item> itemList = new ArrayList<>();

        itemList.add(cringeSword);

        Map<String, Integer> exits = new HashMap<>();
        exits.put("EAST",1);

        Location locale = new Location(0,"You are sitting in front of a computer learning Java",exits,itemList,m);

        exits.remove("EAST",1);
        exits.put("NORTH",3);
        exits.put("SOUTH",2);
        itemList.add(new Armour(Rarity.COMMON, "Level 2 Bulletproof Vest", 10));

        Location locale2 = new Location(1,"You are outside on the sidewalk",exits,itemList,null);

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("locationIOTest.txt"))){

            bufferedWriter.write(locale.toWriteable());
            bufferedWriter.write("\n");
            bufferedWriter.write(locale2.toWriteable());

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Location> locList = new LinkedList<>();

        System.out.println("READING...");

        try(Scanner sc = new Scanner(new File("locationIOTest.txt"))){
            //This is the format the Location will be in:
            //LOCATIONID | DESCRIPTION | EXITS | ITEMS | MONSTER

            while(sc.hasNextLine()){
                String[] compartments = sc.nextLine().split(TextConstants.LOCATION_SPLIT_REGEX);
                //LOCATION ID AND DESCRIPTION HANDLING


                int locID = 0;

                locID = Integer.parseInt(compartments[0]);

                String description = compartments[1];

                //EXITS HANDLING
                String rawExits = compartments[2];

                Map<String, Integer> newExits = new HashMap<>();

                for(String s : rawExits.split(" ")){
                    String[] exit = s.split("-");

                    newExits.put(exit[0],Integer.parseInt(exit[1]));
                }

                List<Item> newItems = new ArrayList<>();

                //ITEM HANDLING
                for(String s : compartments[3].split(TextConstants.ITEM_REGEX)){
                    String[] items = s.split("/");
                    switch (items[0]) {
                        case "WEAPON" -> {
                            newItems.add(new Weapon(
                                    Rarity.parse(items[2]),
                                    items[1],
                                    Integer.parseInt(items[3])

                            ));
                        }
                        case "ARMOUR" -> {
                            newItems.add(new Armour(
                                    Rarity.valueOf(items[2]),
                                    items[1],
                                    Integer.parseInt(items[3])

                            ));
                        }
                        default -> { //just ITEM rather than anything special, makes a RegularItem
                            newItems.add(new RegularItem(
                                    Rarity.valueOf(items[2]),
                                    items[1]));
                        }
                    }
                }

                Monster mons = null;

                //MONSTER HANDLING
                try{
                    String[] monsParams = compartments[4].split("/");

                    mons = new Monster(monsParams[1], Integer.parseInt(monsParams[2]),
                            Integer.parseInt(monsParams[3]),
                            Integer.parseInt(monsParams[4]),
                            Integer.parseInt(monsParams[5])
                    );
                }catch(IndexOutOfBoundsException e){
                    System.out.println("No monster detected on init");
                }
                locList.add(new Location(locID,description,newExits,newItems,mons));
            }

        } catch(FileNotFoundException e){e.printStackTrace();}
        catch(NumberFormatException e){e.printStackTrace();}

        System.out.println("OUTPUTTING READ CONTENT");

        if(locList.size() != 0){
            for(Location l : locList){
                System.out.println(l.toWriteable());
            }
        }
    }

}
