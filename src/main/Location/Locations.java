package main.Location;

import main.Entities.Bosses.BossList;
import main.Entities.Bosses.Desperato;
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

                locID = Integer.parseInt(compartments[0]);

                String name = compartments[1];

                String description = compartments[2];

                //EXITS HANDLING
                String rawExits = compartments[3];

                Map<String, Integer> newExits = new HashMap<>();

                for(String s : rawExits.split(" ")){
                    String[] exit = s.split("-");

                    newExits.put(exit[0],Integer.parseInt(exit[1]));
                }

                List<Item> newItems = new ArrayList<>();

                //ITEM HANDLING
                readItems(compartments[4],newItems);

                Monster mons = null;

                //MONSTER HANDLING
                try{
                    String[] monsParams = compartments[5].split("/");

                    if(monsParams[0].equalsIgnoreCase("Monster")){
                        mons = new Monster(monsParams[1], Integer.parseInt(monsParams[2]),
                                Integer.parseInt(monsParams[3]),
                                Integer.parseInt(monsParams[4]),
                                Integer.parseInt(monsParams[5])
                        );
                    } else if(monsParams[0].equalsIgnoreCase("Boss")){
                        String bossName = monsParams[1].toUpperCase(); //Gets the boss name from the parameter list

                        switch(BossList.valueOf(bossName)){
                            case DESPERATO -> mons = new Desperato();
                        }
                    }
                    try{
                        if(!monsParams[6].equals("null")) readMonsterBuffs(mons,monsParams[6]);

                    }catch(IndexOutOfBoundsException e){
                    }
                }catch(IndexOutOfBoundsException e){
                    //System.out.println("No monster detected on init"); //DEBUG PURPOSES
                }

                NPC npc = null;

                if(compartments.length == 7){
                    try{
                        String npcName = compartments[6];
                        if(npcName != null){
                            switch(NPCList.parse(npcName)){
                                case MIKE -> {
                                    npc = new Mike();
                                }
                                case RAYMOND -> {
                                    npc = new Raymond();
                                }
                                case KARLA -> npc = new Karla();
                                case VES -> npc = new Ves();
                                default -> {
                                }
                            }
                        }
                    } catch(IndexOutOfBoundsException e){
                        System.out.println("NPC reading error @ line: " + lineNum);
                    }
                }
                locations.put(locID,new Location(locID,name,description,newExits,newItems,mons,npc));
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
                    case "HEAL_ITEM" -> newItems.add(new HealingItem(
                            Rarity.valueOf(items[2]), //Rarity
                            items[1], //Name
                            Integer.parseInt(items[3]) //HealAmount
                    ));

                    case "REGEN_ITEM" -> newItems.add(new RegenItem(
                            Rarity.valueOf(items[2]), //Rarity
                            items[1],
                            Integer.parseInt(items[3]),
                            Integer.parseInt(items[4])
                    ));

                    case "SPEED_ITEM" -> newItems.add(new SpeedItem(
                            Rarity.valueOf(items[2]), //Rarity
                            items[1],
                            Integer.parseInt(items[3]),
                            Integer.parseInt(items[4])
                    ));

                    case "RAGE_ITEM" -> newItems.add(new RageItem(
                            Rarity.valueOf(items[2]), //Rarity
                            items[1],
                            Integer.parseInt(items[3]),
                            Integer.parseInt(items[4])
                    ));

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
                    }
                    case SWIFTNESS -> {
                        ((Armour) item).getEffects().add( new Swiftness(Integer.parseInt(parseSrc[1])));
                    }
                }
            } else if (item instanceof Weapon) {

                switch(WeaponEffectList.parse(parseSrc[0])){

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
            mons.addEffect(StatusEffect.readStatus(s));
            //System.out.println(StatusEffect.readStatus(s)); //DEBUG USES ONLY
        }
    }
}
