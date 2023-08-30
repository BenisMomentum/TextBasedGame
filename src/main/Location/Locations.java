package main.Location;

import main.Entities.Bosses.BossList;
import main.Entities.Bosses.Desperato;
import main.Entities.Bosses.TheChild;
import main.Entities.Bosses.Vulnus;
import main.Entities.Monster;
import main.Items.*;
import main.Items.Effects.ArmourEffects.ArmourEffectList;
import main.Items.Effects.ArmourEffects.Swiftness;
import main.Items.Effects.ArmourEffects.Vitality;
import main.Items.Effects.StatusEffects.StatusEffect;
import main.Items.Effects.WeaponEffects.*;
import main.Items.UseableItems.HealingItem;
import main.Items.UseableItems.RageItem;
import main.Items.UseableItems.RegenItem;
import main.Items.UseableItems.SpeedItem;
import main.NPC.NPCs.Karla;
import main.NPC.NPCs.Mike;
import main.NPC.NPC;
import main.NPC.NPCList;
import main.NPC.NPCs.Raymond;
import main.NPC.NPCs.Ves;
import main.TextConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class Locations {

    /*
        By far one of the biggest classes.

        This is a massive singleton that handles the loading of all of the external Game Data in locations.txt

        It loads locations, it loads items and their effects, loads NPCs and all Monsters.
    */

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

        int lineNum = 0;

        try(Scanner sc = new Scanner(new File(fileName))){
            //This is the format the Location will be in:
            //LOCATIONID | DESCRIPTION | EXITS | ITEMS | MONSTER`

            while(sc.hasNextLine()){
                String[] compartments = sc.nextLine().split(TextConstants.LOCATION_SPLIT_REGEX);
                //LOCATION ID AND DESCRIPTION HANDLING

                int locID = 0;

                locID = Integer.parseInt(compartments[0]); //Reads the location ID of the Location

                String name = compartments[1]; //Reads the name of the Location

                String description = compartments[2]; //Reads the documents of the Location

                //EXITS HANDLING
                String rawExits = compartments[3]; //Reads in the raw exits

                Map<String, Integer> newExits = new HashMap<>();

                for(String s : rawExits.split(" ")){
                    String[] exit = s.split("-"); //Splits it into the cardinal direction and the location ID for the next Location

                    newExits.put(exit[0],Integer.parseInt(exit[1])); //Adds them into the map.
                }

                //ITEM HANDLING
                List<Item> newItems = new ArrayList<>(); //Declares the new Items list.

                readItems(compartments[4],newItems); //Reads them in

                //MONSTER HANDLING
                Monster mons = null;

                try{
                    String[] monsParams = compartments[5].split("/");

                    //If the initial parameter after seperation is not "null" and is "MONSTER" (ignoring case

                    if(monsParams[0].equalsIgnoreCase("Monster")){
                        mons = new Monster(monsParams[1], //Name
                                Integer.parseInt(monsParams[2]), //Health
                                Integer.parseInt(monsParams[3]), //Armour
                                Integer.parseInt(monsParams[4]), //Strength
                                Integer.parseInt(monsParams[5]) // Initiative
                                //HASI is the abbreviation I just thought of
                        );
                    } else if(monsParams[0].equalsIgnoreCase("Boss")){ //If its not MONSTER but BOSS
                        String bossName = monsParams[1].toUpperCase(); //Gets the boss name from the parameter list

                        switch(BossList.valueOf(bossName)){
                            case DESPERATO -> mons = new Desperato();
                            case VULNUS -> mons = new Vulnus();
                            case THECHILD -> mons = new TheChild();
                        }
                    }
                    try{
                        if(!monsParams[6].equals("null")) readMonsterBuffs(mons,monsParams[6]); //Then reads in the status effects it has

                    }catch(IndexOutOfBoundsException e){
                    }
                }catch(IndexOutOfBoundsException e){
                    //System.out.println("No monster detected on init"); //DEBUG PURPOSES
                }

                //NPC HANDLING
                NPC npc = null; //Initalizes the npc value to null for the sake of not allocating memory unecessarily

                if(compartments.length == 7){ //Checks if the length would be long enough to be able to contain the NPC to prevent an IOOBE and NPE
                    try{

                        String npcName = compartments[6]; //Gets the name

                        if(npcName != null){ //Again, another check to make absolutely indubitably unequivocally fortnite amongus sure that the NPC IS THERE
                            switch(NPCList.parse(npcName)){ //Performs a switch case to know which NPC we're dealing with
                                case MIKE -> npc = new Mike();
                                case RAYMOND -> npc = new Raymond();
                                case KARLA -> npc = new Karla();
                                case VES -> npc = new Ves();
                                default -> {
                                }
                            }
                            //For the record, Switch cases ARE readable and much faster than an if-else chain
                        }
                    } catch(IndexOutOfBoundsException e){
                        System.out.println("NPC reading error @ line: " + lineNum);
                    }
                }
                locations.put(locID,new Location(locID,name,description,newExits,newItems,mons,npc)); //Finally stuffs the locations into the singleton array
                lineNum++;
            }

        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(NumberFormatException e){
            e.printStackTrace();
            System.out.println("Number Reading error @ line: " + lineNum);
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Array Out of Bounds Exception @ line: " + lineNum);
            e.printStackTrace();
        }

    }


    /*public void saveLocations(){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))){
            for(int i = 0; i < locations.size() - 1;i++){
                bufferedWriter.write(locations.get(i).toWriteable() + "\n");
            }
            bufferedWriter.write(locations.get(locations.size() - 1).toWriteable());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private void readItems(String input, List<Item> newItems){
        if(!input.equals("null")){ //Checks if the input is not null...I hate sneaky NPE's

            for(String s : input.split(TextConstants.ITEM_REGEX)){ // Splits the input by the Item Regex and loops through it

                String[] items = s.split("/"); //Splits it by another regex to de-compartmentalize it further

                switch (items[0]) {
                    case "WEAPON" -> {
                        Weapon w = new Weapon(
                                Rarity.parse(items[2]), //Rarity
                                items[1], //Name
                                Integer.parseInt(items[3]) //Strength Buff (Attack Value)
                        );

                        try{
                            readItemEffects(w,items[4]); //Reads the item effects as well
                        }catch(IndexOutOfBoundsException e){

                        }finally{
                            newItems.add(w); //Finally adds it, even if there's no WeaponEffects
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
                    case "HEAL_ITEM" -> newItems.add(new HealingItem(
                            Rarity.valueOf(items[2]), //Rarity
                            items[1], //Name
                            Integer.parseInt(items[3]) //HealAmount
                    ));

                    case "REGEN_ITEM" -> newItems.add(new RegenItem(
                            Rarity.valueOf(items[2]), //Rarity
                            items[1], //Name
                            Integer.parseInt(items[3]), //Strength
                            Integer.parseInt(items[4])  //Duration
                    ));

                    case "SPEED_ITEM" -> newItems.add(new SpeedItem(
                            Rarity.valueOf(items[2]), //Rarity
                            items[1],
                            Integer.parseInt(items[3]), //Strength
                            Integer.parseInt(items[4])  //Duration
                    ));

                    case "RAGE_ITEM" -> newItems.add(new RageItem(
                            Rarity.valueOf(items[2]), //Rarity
                            items[1],
                            Integer.parseInt(items[3]), //Strength
                            Integer.parseInt(items[4])  //Duration
                    ));

                    default -> { //just ITEM rather than anything special, makes a RegularItem
                        newItems.add(new RegularItem(
                                Rarity.valueOf(items[2]), //Rarity
                                items[1])); //Name
                    }
                }
            }
        }
    }

    private void readItemEffects(Item item, String input){

        String[] newInput = input.split(" "); //Splits it by a space into the less raw input
        String[] parseSrc = null; //Sets parseSrc to null beforehand

        for(String s : newInput){ //Loops over the newInput array
            parseSrc = s.split(Pattern.quote("=")); //Splits it by the '=' regex with as to get the Name and the Value

            if(item instanceof Armour){ //If the item is an Armour Piece

                switch(ArmourEffectList.parse(parseSrc[0])){ //Switches it by the effect name

                    case VITALITY -> ((Armour) item).getEffects().add(new Vitality(Integer.parseInt(parseSrc[1])));

                    case SWIFTNESS -> ((Armour) item).getEffects().add( new Swiftness(Integer.parseInt(parseSrc[1])));
                }
            } else if (item instanceof Weapon) { //If the item is a Weapon

                switch(WeaponEffectList.parse(parseSrc[0])){ //switches it by the effect name

                    case QUICKDRAW -> {
                        ((Weapon) item).getEffects().add( new QuickDraw(Integer.parseInt(parseSrc[1])));
                    }
                    case LIFESTEAL -> {
                        ((Weapon) item).getEffects().add( new LifeSteal(Integer.parseInt(parseSrc[1])));
                    }
                    case PIERCE -> {
                        ((Weapon) item).getEffects().add( new Pierce(Integer.parseInt(parseSrc[1])));
                    }
                    case EDGED -> {
                        ((Weapon) item).getEffects().add( new Edged(Integer.parseInt(parseSrc[1])));
                    }
                }
            }
        }
    }

    private void readMonsterBuffs(Monster mons, String input) {
        for(String s : input.split(" ")){
            mons.addEffect(StatusEffect.readStatus(s)); //adds every effect by reading it first
            //System.out.println(StatusEffect.readStatus(s)); //DEBUG USES ONLY
        }
    }

    public static Location getLocation(int index){
        return Locations.getInstance().locations.get(index);
    }
}
