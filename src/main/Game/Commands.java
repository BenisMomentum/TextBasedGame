package main.Game;

import main.TextConstants;

public enum Commands{
    SCAN("SCAN"),
    MOVE("MOVE"),
    EXIT("EXIT"),
    TAKE("TAKE"),
    HELP("HELP"),
    INVENTORY("INVENTORY"),
    EQUIP("EQUIP");

    Commands(String input){

    }

    public static Commands parse(String input){
       /* switch(input){
            case "SCAN" -> {
                return SCAN;
            }
            case "MOVE" -> {
                return MOVE;
            }
            case "TAKE" -> {
                return TAKE;
            }
            case "HELP" -> {
                return HELP;
            }
            case "EXIT" -> {
                return EXIT;
            }
        }
        return null;*/
        return Commands.valueOf(input);
    }

    public static void useHelpCommand() {
        System.out.println("\n" + TextConstants.EQUALS_SEPERATOR);

        System.out.println("\n" + "(All these commands can be run in lowercase)\n");

        System.out.println("LIST OF COMMANDS: \n");
        System.out.println("SCAN | Scans the area for exits or items.");
        System.out.println("MOVE [DIRECTION] | Any cardinal direction which is available.");
        System.out.println("INVENTORY | Gives you a view of your Inventory");
        System.out.println("EQUIP [ITEM NAME] | Equips Item if its in your inventory");
        System.out.println("TAKE [ITEM NAME] | Grabs an item from the room");
        System.out.println("EXIT | Quit the game");
        System.out.println("HELP | The command you just used.");

        System.out.println("\n" + TextConstants.EQUALS_SEPERATOR);
    }
}
