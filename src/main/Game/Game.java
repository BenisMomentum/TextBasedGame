package main.Game;

import main.Colors;
import main.Entities.Player;
import main.Game.BattleExceptions.PlayerLostException;
import main.Game.BattleExceptions.PlayerRanException;
import main.Game.BattleExceptions.PlayerWonException;
import main.Game.Cutscenes.Bench;
import main.Game.Cutscenes.Cutscene;
import main.Game.Cutscenes.HighSchool;
import main.Game.Cutscenes.Track11;
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
import main.TextConstants;

import java.util.*;

public class Game {


    Scanner sc = new Scanner(System.in);

    Player player = new Player();


    int previousLocationID;
    int locationID;
    private Set<Integer> visitedLocations = new LinkedHashSet<>();



    public Game(){
        Locations.getInstance().loadLocations(); //Loads locations from the locations.txt file that encompasses most game data regarding area.

        startGame();
    }

    public void startGame(){
        if(startMessage()){ //Checks for "start" as the input, if there is no other input it exits
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
                System.out.println(Locations.getInstance().getLocations().get(locationID).getDescription()); //Prints the description of the current location

            } catch(IndexOutOfBoundsException | NullPointerException e){
                System.out.println("\n" + "That exit seems to be blocked upon further inspection..."); //In case of emergency (I messed up and didn't make a location for that exit)

                this.locationID = previousLocationID; //Rolls back to previous location for safety reasons
                continue;
            }

            //In case you've discovered all locations it signals you to go home

            if(this.visitedLocations.size() == Locations.getInstance().getLocations().size()){
                System.out.println("\n" + Colors.RED + "It's getting late" + Colors.RESET);
            }

            System.out.println("\n What do you do?");
            input = "";
            input = sc.nextLine();

            try{
                //Catches you in 4k with a monster if present
                if(Locations.getLocation(locationID).getM() != null){
                    Thread.sleep(500L);
                    System.out.println("Before you could do anything something comes up behind you...\n");
                    Thread.sleep(300L);

                    try{
                        //Initiates the Battle with the given monster and the player.

                        Battle b = new Battle(this.player, Locations.getLocation(locationID).getM(),this);
                    }catch(PlayerLostException e){

                        //In case the player loses it just prints "YOU LOSE" and exits gracefully
                        System.out.println(e.message);

                        Thread.sleep(500L);

                        System.exit(0);

                    }catch(PlayerWonException e){
                        //Player wins, it goes back to normalna.
                        System.out.println(e.message);

                        Thread.sleep(500L);

                        Locations.getLocation(locationID).setM(null);

                        //Resumes standard game procedure and prints the last command you entered, as well as executes it.

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
                //Executes the last command
                standardCommandHandler(input, Locations.getLocation(locationID));
            } catch(NullPointerException | IllegalArgumentException | InterruptedException e){

                /*  In case of stupid stuff happening, it catches it and just says Incorrect command
                    NPE from the switch case
                    Interrupted from all the Thread.sleep()'s
                    IllegalArgument from the parser in the switch case. */

                System.out.println("\n" + "Incorrect Command!");
            } catch(GameWon e){
                System.exit(0);
            }

        }
    }

    private boolean startMessage(){
        System.out.println(TextConstants.STARTUP_MESSAGE + "\n");

        System.out.println("START? " + TextConstants.EQUALS_SEPERATOR + " EXIT?");

        String input = sc.next(); //If it's Start, then it continues with the game, if it's anything else, then ay.

        return input.trim().equalsIgnoreCase("START");
    }


    private void standardCommandHandler(String input, Location currentLoc) throws GameWon {
        //One of the only reasons it would throw an NPE is because the Commands.ordinal() function used by
        //The switch case returns null.

        input = input.trim(); //Cleans up the input
        String[] command = null; //In case the command has multiple parts, it sets it to null as to avoid initializing it unnecessarily.
        boolean multiParamCommand = false;

        //Handles the multiple parameter input for the game. Checks first if its actually multiple parameters

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
            case ITEMS, INVENTORY -> handleInventoryView();
            case STATS -> System.out.println(player.getStats());
            case USE -> {
                handleStandardUSECommand(multiParamCommand ? command[1] : null);
            }

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
        //Checksfor the text debug codes first, then moves to the actual numbers for teleportation

        if(s.equalsIgnoreCase("discover_all")){
            for(Location l : Locations.getInstance().getLocations().values()){
                if(!visitedLocations.contains(l.getLocationID())){
                    this.visitedLocations.add(l.getLocationID()); //Adds it to the list of visited locations if not there.
                }
            }
            return;
        }

        int debugCode = Integer.parseInt(s.trim());

        if(debugCode == Cutscene.TRACK11_ID){
            Track11 t = new Track11(player);
        }else if(debugCode >= 0 && debugCode <= 29){
            this.previousLocationID = locationID;
            this.locationID = debugCode;
        }


    }

    private void handleTALKCommand(String s) {

        /*
        *IF there is an NPC in the area and you try talking to it.
        * Checks the String to see if it matches
        * Passes the NPC to a DialogueController as well as the player for the sake of Alignment.
         */

        if(Locations.getLocation(locationID).getNPC() == null){ //Checks if its null

            System.out.println("[NO NPC THERE]");
            return;
        }


        if(!s.equalsIgnoreCase(
               Locations.getLocation(locationID).getNPC().getName()
        )) {
            System.out.println("[Enter the correct NPC name]");
            return;
        }


        NPC npc = Locations.getLocation(locationID).getNPC();

        DialogueController dC = new DialogueController(npc, this.player);

    }

    private void handleStandardUSECommand(String inp) {

        /*
        IF its a multi parameter command
            Loops through the inventory, finds the item with the name specified.
            IF its a regen item, it will heal for (regenAmount * duration)
            ELSE it will just use the item.
            Afterwards it will remove the item from inventory.

         ELSE it will display all the items for the player and make them enter the item number that corresponds to its index.

         */

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

                int input = 0;

                try{
                    input = new Scanner(System.in).nextInt();//Attempts to fix a bug where apparently the main scanner registers a new command when none is present
                }catch(InputMismatchException e){
                    System.out.println("(Enter an appropriate number:)");
                }
                try{
                    if(input == -1){
                        return;
                    } else if(player.getInventory().get(input) instanceof UseableItem){
                        if(player.getInventory().get(input) instanceof RegenItem regenItem){

                            player.heal(regenItem.getValue() * regenItem.getDuration()); //Heals for all turns it would have been applied for
                        }else{
                            player.getInventory().get(input).use(player);
                        }
                        player.getInventory().remove(player.getInventory().get(input));
                    }
                    break;
                } catch(IndexOutOfBoundsException e){
                    System.out.println("\n" + "Number does not correspond to inventory item! Try again!");
                }

            }
        }


    }

    private void displayPlayerUseableItems() {

        /*
        Displays all the useable items of the player.

        Filters it by checking if the getInventory index result is an instance of UseableItem.

        ELSE it will just print No Useable Items if the size == 0.
        */

        System.out.println("\n" +TextConstants.INVENTORY_VIEW + "\n");

        player.getInventory().sort(Comparator.comparing(Item::getName));

        System.out.println("-1 | CANCEL");
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

        /*
        First, it checks how many items are in the currentLocation
        IF there are more than 0 items in the current locale, it prints it.
        ELSE, it will just display "No items found"

        Second, it will Scan for any exits and displays it
        IF the Exit leads to a place that the player has found already,
        it will display the name of it.
        ELSE, it will just print the direction the player has to go in.

        Third and final, IF there is an NPC, it will display the name,
        ELSE it will just print "No People Found"

         */

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

    private String getExitDiscovery(Location currentLoc, String desiredExit) {

        /*
        Meant to get the exit name if it's been discovered or not.

        Does this by getting the Location ID and cross-referencing it with visitedLocations

        IF the ID is in the visitedLocations arraylist, it will be return the following:

        "DIRECTION - [LOCATION_NAME]"

        ELSE, it will just return "".
        */

        Integer id = currentLoc.getExits().get(desiredExit);

        if(this.visitedLocations.contains(id)){
            return " - " + Locations.getLocation(id).getName();
        }else{
            return "";
        }
    }

    private void handleInventoryView() {

        /*
        Prints the following:
        =========================

        CURRENT WEAPON:
        CURRENT ARMOUR:

        ITEMS:

        =========================
        */

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

        if(Locations.getLocation(locationID).getExits().get(direction) != null){

            this.move(direction);

            //Performs all cutscene checks
            performCutsceneChecks();

        }else{
            System.out.println("[NOT A VALID COMMAND]");
        }

        //Plans to come for room entrance checks
    }

    private void move(String direction) {
        previousLocationID = this.locationID; //Updates previous location
        this.locationID = Locations.getLocation(locationID).getExits().get(direction);

        this.visitedLocations.add(this.previousLocationID); //Adds it to the list of visited locations if not there.
    }

    private void performCutsceneChecks() throws GameWon {

        //Function performs the cutscene checks for the locationID

        switch(locationID){
            case(0) -> {
                if(this.visitedLocations.size() == Locations.getInstance().getLocations().size()){

                    Track11 t11 = new Track11(player); //Inits the track11 cutscene
                }
            }
            case(Cutscene.BENCH_ID) -> {
                if(!Cutscene.VISITED_BENCH){

                    Bench b = new Bench(player); //Initializes the bench cutscene
                }
            }
            case(Cutscene.HIGHSCHOOL_ID) ->{
                if(!Cutscene.VISITED_HIGHSCHOOL){

                    HighSchool h = new HighSchool(player); //Inits the highschool cutscene
                }
            }
            default -> {}
        }
    }

    private void handleTakeItem(String itemName, Location location){

        /*
        IF ItemName is "all", then it just takes every item in the Location.

        ELSE IF ItemName is valid, it will find the itemname and then take it

        ELSE, It will just print no valid item names.
        */

        if(itemName.equalsIgnoreCase("all")){
            for(int i = 0; i < location.getItems().size(); i++){
                player.take(location.getItems().get(i));
            }

            location.getItems().clear();
            return;
        }

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

        //Prints all commands with a description of what they do via Commands.useHelpCommand()

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


