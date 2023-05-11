package main.Location;

import main.Entities.Monster;
import main.Items.*;
import main.TextConstants;

import java.io.*;
import java.util.*;

public class Locations {
    private static Locations instance = new Locations();
    private static final String fileName = "locations.txt";

    private List<Location> locations;

    public Locations(){

    }

    public static Locations getInstance() {
        return instance;
    }

    public List<Location> getLocations() {
        return locations;
    }


    public void loadLocations(){
        locations = new LinkedList<>();

        System.out.println("READING...");

        try(Scanner sc = new Scanner(new File(fileName))){
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
                if(!compartments[3].equals("null")){
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
                locations.add(new Location(locID,description,newExits,newItems,mons));
            }

        } catch(FileNotFoundException e){e.printStackTrace();}
        catch(NumberFormatException e){e.printStackTrace();}
    }

    public void saveLocations(){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))){
            for(int i = 0; i < locations.size() - 1;i++){
                bufferedWriter.write(locations.get(i).toWriteable() + "\n");
            }
            bufferedWriter.write(locations.get(locations.size() - 1).toWriteable());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
