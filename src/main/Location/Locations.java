package main.Location;

import main.Entities.Monster;
import main.Items.*;
import main.Items.Effects.ArmourEffects.ArmourEffectList;
import main.Items.Effects.ArmourEffects.Swiftness;
import main.Items.Effects.ArmourEffects.Vitality;
import main.Items.Effects.Effect;
import main.Items.Effects.WeaponEffects.*;
import main.Items.UseableItems.HealingItem;
import main.Items.UseableItems.RegenItem;
import main.TextConstants;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Locations {
    private static Locations instance = new Locations();
    private static final String fileName = "locations.txt";

    private Map<Integer,Location> locations;

    public Locations(){

    }

    public static Locations getInstance() {
        return instance;
    }

    public Map<Integer,Location> getLocations() {
        return locations;
    }


    public void loadLocations(){
        locations = new LinkedHashMap<>();

        System.out.print("READING FILES.");

        try {
            Thread.sleep(500L);
            System.out.print(".");
            Thread.sleep(500L);
            System.out.print(".");
            Thread.sleep(500L);
        } catch (InterruptedException e) {

        }finally{
            System.out.println();
        }


        try(Scanner sc = new Scanner(new File(fileName))){
            //This is the format the Location will be in:
            //LOCATIONID | DESCRIPTION | EXITS | ITEMS | MONSTER`

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
                readItems(compartments[3],newItems);

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
                    //System.out.println("No monster detected on init"); //DEBUG PURPOSES
                }
                locations.put(locID,new Location(locID,description,newExits,newItems,mons));
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

    private void readItems(String input, List<Item> newItems){
        if(!input.equals("null")){
            for(String s : input.split(TextConstants.ITEM_REGEX)){
                String[] items = s.split("/");
                switch (items[0]) {
                    case "WEAPON" -> {
                        Weapon w = new Weapon(
                                Rarity.parse(items[2]), //Rarity
                                items[1], //Name
                                Integer.parseInt(items[3]) //Strength Buff (Attack Value)
                        );

                        try{
                            readItemEffects(w,items[4]);
                        }catch(IndexOutOfBoundsException e){

                        }finally{
                            newItems.add(w);
                        }
                    }
                    case "ARMOUR" -> {
                        Armour a = new Armour(
                                Rarity.valueOf(items[2]), //Rarity
                                items[1], //Name
                                Integer.parseInt(items[3]) //Armour Value
                        );

                        try{
                            readItemEffects(a,items[4]);
                        }catch(IndexOutOfBoundsException e){

                        }finally{
                            newItems.add(a);
                        }
                    }
                    case "HEAL_ITEM" ->{
                        newItems.add(new HealingItem(
                                Rarity.valueOf(items[2]), //Rarity
                                items[1], //Name
                                Integer.parseInt(items[3]) //HealAmount
                        ));
                    }

                    case "REGEN_ITEM" ->{

                        newItems.add(new RegenItem(
                                Rarity.valueOf(items[2]), //Rarity
                                items[1],
                                Integer.parseInt(items[3]),
                                Integer.parseInt(items[4])
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
    }

    private void readItemEffects(Item item, String input){
        String[] newInput = input.split(" ");
        String[] parseSrc = null;

        for(String s : newInput){
            parseSrc = s.split(Pattern.quote("="));
            if(item instanceof Armour){
                switch(ArmourEffectList.parse(parseSrc[0])){
                    case VITALITY ->{

                        ((Armour) item).getEffects().add(new Vitality(Integer.parseInt(parseSrc[1])));
                        break;
                    }
                    case SWIFTNESS -> {
                        ((Armour) item).getEffects().add( new Swiftness(Integer.parseInt(parseSrc[1])));
                        break;
                    }
                }
            } else if (item instanceof Weapon) {

                switch(WeaponEffectList.parse(parseSrc[0])){

                    case QUICKDRAW -> {
                        ((Weapon) item).getEffects().add( new QuickDraw(Integer.parseInt(parseSrc[1])));
                        break;
                    }
                    case LIFESTEAL -> {
                        ((Weapon) item).getEffects().add( new LifeSteal(Integer.parseInt(parseSrc[1])));
                        break;
                    }
                    case PIERCE -> {
                        ((Weapon) item).getEffects().add( new Pierce(Integer.parseInt(parseSrc[1])));
                        break;
                    }
                    case EDGED -> {
                        ((Weapon) item).getEffects().add( new Edged(Integer.parseInt(parseSrc[1])));
                        break;
                    }
                }
            }
        }
    }
}
