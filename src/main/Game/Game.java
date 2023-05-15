package main.Game;

import main.Entities.Player;
import main.Items.Armour;
import main.Items.Item;
import main.Items.Weapon;
import main.Location.Location;
import main.Location.Locations;
import main.TextConstants;
import org.w3c.dom.ls.LSOutput;

import java.util.Comparator;
import java.util.Scanner;

public class Game {

    Scanner sc = new Scanner(System.in);

    Player p = new Player();

    //Locations locations;
    int locationID;

    public Game(){
        Locations.getInstance().loadLocations();

        if(startMessage()){
            System.out.println("TYPE \"HELP\" FOR A LIST OF COMMANDS");
            gameLoop();
        }

        //Locations.getInstance().saveLocations(); //Why save the locations?
    }

    private void gameLoop() {
        this.sc = new Scanner(System.in); //Refreshing it(?)


        System.out.println("==================BEGIN==================");

        String input = "";
        locationID = 0;

        while(!input.equals("EXIT")){

            System.out.println(Locations.getInstance().getLocations().get(locationID).getDescription());

            System.out.println("\n What do you do?");

            input = sc.nextLine();

            try{
                standardCommandHandler(input, Locations.getInstance().getLocations().get(locationID));
            } catch(NullPointerException e){
                System.out.println("\n" + "Incorrect Command!");
            }

        }
    }

    private boolean startMessage(){
        System.out.println(TextConstants.STARTUP_MESSAGE + "\n");

        System.out.println("START? " + TextConstants.EQUALS_SEPERATOR + " EXIT?");

        return sc.next().equals("START");
    }


    private void standardCommandHandler(String input, Location currentLoc) throws NullPointerException{
        //One of the only reasons it would throw an NPE is because the Commands.ordinal() function used by
        //The switch case returns null.

        input = input.trim();
        String[] command = null;
        boolean multiParamCommand = false;

        if(input.contains(" ")){
            command = input.split(" ");
            multiParamCommand = true;
        }

        switch(Commands.parse(( //Checks if its a multiple command thing
                multiParamCommand ? command[0] : input)
        )){
            case SCAN -> { //single param command
                handleSCANCommand(currentLoc);
            }
            case MOVE -> {
                try{
                    enterRoom(command[1]);

                }catch(NumberFormatException e){
                    e.printStackTrace();
                }catch(NullPointerException e){
                    System.out.println("Specify a cardinal direction as follow: [NORTH,SOUTH,EAST,WEST]");
                }
            }
            case EXIT -> {
                return;
            }
            case TAKE -> {
                if(currentLoc.getItems().size() == 0){
                    System.out.println("No Items in this area!");
                }else if(command == null){
                    System.out.println("No item name specified!");
                }else{
                    handleTakeItem(command[1],currentLoc);
                }
            }
            
            case HELP -> handleHELPCommand();

            case EQUIP -> {
                if(command == null){
                    System.out.println("No item name specified!");
                }else{
                    handleEQUIPCommand(command[1]);
                }
            }
            
            case INVENTORY -> handleInvetoryView();
                    
            default -> {
                System.out.println("\n" + "Incorrect Command!");
            }
        }
    }

    private void handleSCANCommand(Location currentLoc) {
        if(currentLoc.getItems().size() != 0){
            System.out.println("ITEMS FOUND!: ");

            for(Item item : currentLoc.getItems()){
                System.out.println(item);
            }
        }
        else{
            System.out.println("No items found");
        }

        System.out.println("\n EXITS FOUND!: ");

        for(String s : currentLoc.getExits().keySet()){
                System.out.println(s);
        }

    }

    private void handleInvetoryView() {

        System.out.println("\n" + TextConstants.INVENTORY_VIEW);

        System.out.println("\n"+"CURRENT WEAPON: " + p.getEquipedWeapon());
        System.out.println("\n"+"CURRENT ARMOUR: " + p.getEquipedArmour());

        p.getInventory().sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

        System.out.println("\n" + "ITEMS: \n");
        for(Item i : p.getInventory()){
            System.out.println(i);
        }

        System.out.println("\n" + TextConstants.EQUALS_SEPERATOR + "\n");
    }

    private void enterRoom(String direction){
        //Basically checks if the locationID is valid to be set

        if(Locations.getInstance().getLocations().get(locationID).getExits().get(direction) != null){
            this.locationID = Locations.getInstance().getLocations().get(locationID).getExits().get(direction);
        }else{
            System.out.println("[NOT A VALID COMMAND]");
        }

        //Plans to come for room entrance checks
    }

    private void handleTakeItem(String itemName, Location location){
        for(Item i : location.getItems()){
            if(itemName.equals(i.getName())){
                this.p.take(i);
                location.getItems().remove(i);
                return;
            }
        }
        System.out.println("NO Valid items with that name!");
    }

    private void handleHELPCommand(){
        Commands.useHelpCommand();
    }

    private void handleEQUIPCommand(String itemName){

        /*
        Essentially scans for the correct item that needs to be equipped.
        Checks if its a piece of Armour or Weaponry (Instance of Armour or Weapon)
        Then calls the equip function correspondingly.
         */

        for(int i = 0; i < p.getInventory().size(); i++){
            if(p.getInventory().get(i).getName().equals(itemName)
                    && p.getInventory().get(i) instanceof Weapon){
                p.equip((Weapon) p.getInventory().get(i));
                return;

            } else if(p.getInventory().get(i).getName().equals(itemName)
                    && p.getInventory().get(i) instanceof Armour){
                p.equip((Armour) p.getInventory().get(i));
                return;
            }
        }
    }

}


