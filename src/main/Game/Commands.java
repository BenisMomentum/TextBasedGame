package main.Game;

public enum Commands{
    SCAN("SCAN"),
    MOVE("MOVE"),
    EXIT("EXIT");

    Commands(String input){

    }

    public static Commands parse(String input){
        switch(input){
            case "SCAN" -> {
                return SCAN;
            }
            case "MOVE" -> {
                return MOVE;
            }
            case "EXIT" -> {
                return EXIT;
            }
        }
        return null;
    }
}
