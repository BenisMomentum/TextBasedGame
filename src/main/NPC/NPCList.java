package main.NPC;

public enum NPCList {
    MIKE("MIKE");


    NPCList(String name) {
    }

    public static NPCList parse(String name){
        name = name.toUpperCase();

        switch(name){
            case "MIKE" -> {
                return MIKE;
            }
            default -> {return null;}
        }
    }
}
