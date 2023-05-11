package main.Game;

import main.Items.Item;
import main.Location.Location;
import main.Location.Locations;
import main.TextConstants;

import java.util.Scanner;

public class Game {

    Scanner sc = new Scanner(System.in);

    Locations locations;
    int locationID;

    public Game(){
        Locations.getInstance().loadLocations();

        if(startMessage()){
            gameLoop();
        }

        Locations.getInstance().saveLocations();
    }

    private void gameLoop() {
        this.sc = new Scanner(System.in); //Refreshing it(?)


        System.out.println("==================BEGIN==================");

        String input = "";
        locationID = 0;

        while(!input.equals("EXIT")){

            System.out.println(locations.getInstance().getLocations().get(locationID).getDescription());

            System.out.println("\n What do you do?");

            input = sc.nextLine();

            standardCommandHandler(input,locations.getInstance().getLocations().get(locationID));

        }
    }

    private boolean startMessage(){
        System.out.println(TextConstants.STARTUP_MESSAGE + "\n");

        System.out.println("START? " + TextConstants.EQUALS_SEPERATOR + " EXIT?");

        return sc.next().equals("START");
    }


    private void standardCommandHandler(String input, Location currentLoc){
        input = input.trim();
        String[] command = null;

        switch(Commands.parse(( //Checks if its a multiple command thing
                input.contains(" ") ? input.split(" ")[0] : input)
        )){
            case SCAN -> { //single param command
                if(currentLoc.getItems().size() != 0){
                    System.out.println("ITEMS FOUND!: ");

                    for(Item item : currentLoc.getItems()){
                        System.out.println(item);
                    }

                    System.out.println("\n EXITS FOUND!: ");

                    for(String s : currentLoc.getExits().keySet()){
                        System.out.println(s);
                    }
                }
                else{
                    System.out.println("No items found");
                }
            }
            case MOVE -> {
                try{
                    enterRoom(input.split(" ")[1]);

                }catch(NumberFormatException e){
                    e.printStackTrace();
                }
            }
            case EXIT -> {
                return;
            }
        }
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

}


