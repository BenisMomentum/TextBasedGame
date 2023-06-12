package main.NPC;

public enum NPCList {
    MIKE("MIKE"),
    RAYMOND("RAYMOND"),
    KARLA("KARLA"),
    VES("VES");


    NPCList(String name) {
    }

    public static NPCList parse(String name){
        name = name.toUpperCase();

        switch(name){
            case "MIKE" -> {
                return MIKE;
            }
            case "RAYMOND" ->{
                return RAYMOND;
            }
            case "KARLA" -> {
                return KARLA;
            }
            case "VES" ->{
                return VES;
            }
            default -> {return null;}
        }
    }
}
