package main.Game;

import main.Colors;
import main.Entities.Player;
import main.Game.BattleExceptions.PlayerLostException;
import main.Game.BattleExceptions.PlayerRanException;
import main.Game.BattleExceptions.PlayerWonException;
import main.Game.GameConditions.GameWon;
import main.Items.Armour;
import main.Items.Item;
import main.Items.UseableItems.RegenItem;
import main.Items.UseableItems.UseableItem;
import main.Items.Weapon;
import main.Location.Location;
import main.Location.Locations;
import main.NPC.DialogueController;
import main.NPC.NPC;
import main.NPC.NPCList;
import main.TextConstants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Game {

    /*
    TODO:
    Feature Requests:

          - NPCs

     */

    Scanner sc = new Scanner(System.in);

    Player player = new Player();


    int previousLocationID;
    int locationID;
    private List<Integer> visitedLocations = new ArrayList<>();

    public Game(){
        Locations.getInstance().loadLocations(); //Loads locations from the locations.txt file that encompasses most game data regarding area.

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

            if(this.visitedLocations.size() == Locations.getInstance().getLocations().size()){
                System.out.println("\n" + Colors.RED + "It's getting late" + Colors.RESET);
            }

            System.out.println("\n What do you do?");
            input = "";
            input = sc.nextLine();

            try{

                if(Locations.getInstance().getLocations().get(locationID).getM() != null){
                    Thread.sleep(500L);
                    System.out.println("Before you could do anything something comes up behind you...\n");
                    Thread.sleep(300L);

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
                    } catch(Exception e){ //DEBUG: Remove this later as I have no idea whats going on with this anymore, this is just so that I can understand the fucking error
                        e.printStackTrace();
                    } finally {
                        player.getStatusEffects().clear();
                    }
                }

                standardCommandHandler(input, Locations.getInstance().getLocations().get(locationID));
            } catch(NullPointerException | IllegalArgumentException | InterruptedException e){
                System.out.println("\n" + "Incorrect Command!");
            } catch(GameWon e){
                System.exit(0);
            }

        }
    }

    private boolean startMessage(){
        System.out.println(TextConstants.STARTUP_MESSAGE + "\n");

        System.out.println("START? " + TextConstants.EQUALS_SEPERATOR + " EXIT?");

        String input = sc.next();

        return input.trim().equalsIgnoreCase("START");
    }


    private void standardCommandHandler(String input, Location currentLoc) throws GameWon {
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
                    handleMOVECommand(command[1]);
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
            case USE -> handleStandardUSECommand(command[1]);

            case TALK ->{
                try{
                    handleTALKCommand(command[1]);
                }catch(NullPointerException e){

                }
            }

            case DEBUG -> handleDEBUGCommand(command[1]);
                    
            default -> System.out.println("\n" + "Incorrect Command!");
        }
    }

    private void handleDEBUGCommand(String s) throws GameWon {
        int debugCode = Integer.parseInt(s.trim());

        if(debugCode == 30){
            Track11 t = new Track11(player);
        }else if(debugCode >= 0){
            this.previousLocationID = locationID;
            this.locationID = debugCode;
        }


    }

    private void handleTALKCommand(String s) {
        if(Locations.getInstance().getLocations().get(locationID).getNPC() != null){
            NPC npc = Locations.getInstance().getLocations().get(locationID).getNPC();

            DialogueController dC = new DialogueController(npc, this.player);
        }


    }

    private void handleStandardUSECommand(String inp) {

        if(inp != null){

            for(int f = 0; f < player.getInventory().size(); f++){
                if(player.getInventory().get(f).getName().equalsIgnoreCase(inp)){
                    try{
                        if(player.getInventory().get(f) instanceof UseableItem){
                            if(player.getInventory().get(f) instanceof RegenItem regenItem){

                                player.heal(regenItem.getValue() * regenItem.getDuration()); //Heals for all turns it would have been applied for
                            }else{
                                player.getInventory().get(f).use(player);
                            }
                            player.getInventory().remove(player.getInventory().get(f));
                        }
                        break; //FLAG: CHANGE NULL TO MONSTER LATER ON ALONG WITH CHECK FOR OFFENSIVE/DEFENSIVE USEABLE ITEM
                    } catch(IndexOutOfBoundsException e){
                        System.out.println("\n" + "Number does not correspond to inventory item! Try again!");
                    }
                }
            }

        } else{
            this.displayPlayerUseableItems(); //Displays all items for user to choose based on Number

            while(true){
                System.out.print("Enter Item Number: ");
                int input = new Scanner(System.in).nextInt(); //Attempts to fix a bug where apparently the main scanner registers a new command when none is present
                try{
                    if(player.getInventory().get(input) instanceof UseableItem){
                        if(player.getInventory().get(input) instanceof RegenItem regenItem){

                            player.heal(regenItem.getValue() * regenItem.getDuration()); //Heals for all turns it would have been applied for
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
                System.out.println(s + getExitDiscovery(currentLoc, s));
        }

        if(currentLoc.getNPC() != null){
            System.out.println("\n" + "People detected: ");

            System.out.println(currentLoc.getNPC().getName());
        }else{
            System.out.println("\n" + "No people detected");
        }
    }

    private String getExitDiscovery(Location currentLoc, String desiredExit) { //Meant to get the exit name if it's been discovered or not.

        Integer id = currentLoc.getExits().get(desiredExit);

        if(this.visitedLocations.contains(id)){
            return " - " + Locations.getInstance().getLocations().get(id).getName();
        }else{
            return "";
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

    private void handleMOVECommand(String direction) throws GameWon {
        //Basically checks if the locationID is valid to be set

        direction = direction.toUpperCase();

        if(Locations.getInstance().getLocations().get(locationID).getExits().get(direction) != null){

            previousLocationID = this.locationID; //Updates previous location
            this.locationID = Locations.getInstance().getLocations().get(locationID).getExits().get(direction);
            this.visitedLocations.add(this.previousLocationID); //Adds it to the list of visited locations.

            if(this.locationID == 0 && this.visitedLocations.size() == Locations.getInstance().getLocations().size()){
                Track11 t11 = new Track11(player);
            }

        }else{
            System.out.println("[NOT A VALID COMMAND]");
        }

        //Plans to come for room entrance checks
    }

    private void handleTakeItem(String itemName, Location location){
        for(Item i : location.getItems()){
            if(itemName.equalsIgnoreCase(i.getName())){
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
            if(player.getInventory().get(i).getName().equalsIgnoreCase(itemName)
                    && player.getInventory().get(i) instanceof Weapon){
                player.equip((Weapon) player.getInventory().get(i));
                return;

            } else if(player.getInventory().get(i).getName().equalsIgnoreCase(itemName)
                    && player.getInventory().get(i) instanceof Armour){
                player.equip((Armour) player.getInventory().get(i));
                return;
            }
        }
    }

}


