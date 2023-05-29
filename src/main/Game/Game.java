package main.Game;

import main.Entities.Player;
import main.Game.BattleExceptions.PlayerLostException;
import main.Game.BattleExceptions.PlayerRanException;
import main.Game.BattleExceptions.PlayerWonException;
import main.Items.Armour;
import main.Items.Item;
import main.Items.UseableItems.RegenItem;
import main.Items.UseableItems.UseableItem;
import main.Items.Weapon;
import main.Location.Location;
import main.Location.Locations;
import main.TextConstants;

import java.util.Comparator;
import java.util.Scanner;

public class Game {

    Scanner sc = new Scanner(System.in);

    Player player = new Player();


    int previousLocationID;
    int locationID;

    public Game(){
        Locations.getInstance().loadLocations(); //Loads locations from the locations.txt file that encompasses most game data regarding area.
        //Locations.getInstance().saveLocations(); //Why save the locations?

        startGame();
    }

    public void startGame(){
        if(startMessage()){
            System.out.println("TYPE \"HELP\" FOR A LIST OF COMMANDS");
            gameLoop();
        }
    }

    private void gameLoop() {
        this.sc = new Scanner(System.in); //Refreshing it(?)


        System.out.println("==================BEGIN==================");

        String input = "";

        locationID = 0;
        previousLocationID = 0;

        while(!input.equalsIgnoreCase("EXIT")){
            try{
                System.out.println(Locations.getInstance().getLocations().get(locationID).getDescription());
            } catch(IndexOutOfBoundsException | NullPointerException e){
                System.out.println("\n" + "That exit seems to be blocked upon further inspection...");

                this.locationID = previousLocationID; //Rolls back to previous location
                continue;
            }

            System.out.println("\n What do you do?");

            input = sc.nextLine();

            try{

                if(Locations.getInstance().getLocations().get(locationID).getM() != null){
                    Thread.sleep(500L);
                    System.out.println("Before you could do anything something comes up behind you...\n");

                    try{
                        Battle b = new Battle(this.player, Locations.getInstance().getLocations().get(locationID).getM(),this);
                    }catch(PlayerLostException e){
                        System.out.println(e.message);

                        Thread.sleep(500L);

                        System.exit(0);

                    }catch(PlayerWonException e){
                        System.out.println(e.message);

                        Thread.sleep(500L);

                        Locations.getInstance().getLocations().get(locationID).setM(null);

                        System.out.println("Anyways, as you were...\n");

                        Thread.sleep(500L);

                        System.out.println("LAST COMMAND: " + input);

                    } catch(PlayerRanException e){
                        locationID = previousLocationID;
                        //System.out.println("You got away safely!");
                    } finally{
                        player.getStatusEffects().clear();
                    }
                }

                standardCommandHandler(input, Locations.getInstance().getLocations().get(locationID));
            } catch(NullPointerException | IllegalArgumentException | InterruptedException e){
                System.out.println("\n" + "Incorrect Command!");
            }

        }
    }

    private boolean startMessage(){
        System.out.println(TextConstants.STARTUP_MESSAGE + "\n");

        System.out.println("START? " + TextConstants.EQUALS_SEPERATOR + " EXIT?");

        String input = sc.next();

        return input.trim().equalsIgnoreCase("START");
    }


    private void standardCommandHandler(String input, Location currentLoc) throws NullPointerException{
        //One of the only reasons it would throw an NPE is because the Commands.ordinal() function used by
        //The switch case returns null.

        input = input.trim();
        String[] command = null;
        boolean multiParamCommand = false;

        if(input.contains(" ")){
            command = input.split(" ");
            command[0] = command[0].toUpperCase();
            multiParamCommand = true;
        }else{
            input = input.toUpperCase();
        }

        switch(Commands.parse(( //Checks if its a multiple command thing
                multiParamCommand ? command[0] : input)
        )){
            case SCAN -> handleSCANCommand(currentLoc); //single param command
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
            case STATS -> System.out.println(player.getStats());
            case USE -> handleStandardUSECommand();
                    
            default -> {
                System.out.println("\n" + "Incorrect Command!");
            }
        }
    }

    private void handleStandardUSECommand() {

        this.displayPlayerUseableItems();

        while(true){
            System.out.print("Enter Item Number: ");
            int input = sc.nextInt();
            try{
                if(player.getInventory().get(input) instanceof UseableItem){
                    if(player.getInventory().get(input) instanceof RegenItem){
                        RegenItem i = (RegenItem) player.getInventory().get(input);

                        player.heal(i.getRegenAmount() * i.getDuration()); //Heals for all turns it would have been applied for
                    }else{
                        player.getInventory().get(input).use(player);
                    }
                    player.getInventory().remove(player.getInventory().get(input));
                }
                break; //FLAG: CHANGE NULL TO MONSTER LATER ON ALONG WITH CHECK FOR OFFENSIVE/DEFENSIVE USEABLE ITEM
            } catch(IndexOutOfBoundsException e){
                System.out.println("\n" + "Number does not correspond to inventory item! Try again!");
            }

        }

    }

    private void displayPlayerUseableItems() {
        System.out.println("\n" +TextConstants.INVENTORY_VIEW + "\n");

        player.getInventory().sort(Comparator.comparing(Item::getName));

        if(player.getInventory().size() != 0){
            for(int i = 0; i < player.getInventory().size(); i++){
                if(player.getInventory().get(i) instanceof UseableItem){
                    System.out.println(i + " | " + player.getInventory().get(i).getName());
                }
            }
        }else{
            System.out.println("NO USEABLE ITEMS");
        }

        System.out.println("\n" + TextConstants.EQUALS_SEPERATOR + "\n");
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

        System.out.println();

        System.out.println("\n"+"CURRENT WEAPON: " + player.getEquipedWeapon());
        System.out.println("\n"+"CURRENT ARMOUR: " + player.getEquipedArmour());

        player.getInventory().sort(Comparator.comparing(Item::getName));

        System.out.println("\n" + "ITEMS: \n");
        for(Item i : player.getInventory()){
            System.out.println(i);
        }

        System.out.println("\n" + TextConstants.EQUALS_SEPERATOR + "\n");
    }

    private void enterRoom(String direction){
        //Basically checks if the locationID is valid to be set

        direction = direction.toUpperCase();

        if(Locations.getInstance().getLocations().get(locationID).getExits().get(direction) != null){

            previousLocationID = this.locationID; //Updates previous location
            this.locationID = Locations.getInstance().getLocations().get(locationID).getExits().get(direction);

        }else{
            System.out.println("[NOT A VALID COMMAND]");
        }

        //Plans to come for room entrance checks
    }

    private void handleTakeItem(String itemName, Location location){
        for(Item i : location.getItems()){
            if(itemName.equals(i.getName())){
                this.player.take(i);
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

        for(int i = 0; i < player.getInventory().size(); i++){
            if(player.getInventory().get(i).getName().equals(itemName)
                    && player.getInventory().get(i) instanceof Weapon){
                player.equip((Weapon) player.getInventory().get(i));
                return;

            } else if(player.getInventory().get(i).getName().equals(itemName)
                    && player.getInventory().get(i) instanceof Armour){
                player.equip((Armour) player.getInventory().get(i));
                return;
            }
        }
    }

}


