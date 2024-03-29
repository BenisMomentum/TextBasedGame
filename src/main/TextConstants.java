package main;

public class TextConstants {
    public static final String EQUALS_SEPERATOR = "=========================================";
    public static final String LOCATION_BIG_REGEX = " | ";
    public static final String LOCATION_SPLIT_REGEX = "\\s*\\|\\s*";
    public static final String EXIT_REGEX = "-";
    public static final String ITEM_REGEX = " / ";
    public static final String INVENTORY_VIEW = "=============INVENTORY VIEW=============";
    public static final String GAME_LOSE = "=============GAME OVER!=============";

    public static final String BATTLE_START = "=============BATTLE STARTED!=============";
    public static final String BATTLE_OPTIONS = "=========| ATTACK | BLOCK | USE | RUN |=========";
    public static final String BATTLE_WIN = "=============YOU WON THE BATTLE!=============";
    public static final String BATTLE_LOSE = "=============YOU LOST!=============";


    //EFFECTS DURING BATTLE:
    //Key note, replace '=' with the level/turns left

    public static final String[] EFFECT_REGEN = {"    ___    " ,
            " __|   |__" ,
            "|__  = __|" ,
            "   |___|"};

    public static final String[] EFFECT_BLEED = {"    /\\   " ,
            "  /    \\ " ,
            "  |  =  |" ,
            "  \\___/"};

    public static final String[] EFFECT_SPEED = {"\\ \\" ,
            "   \\\\" ,
            "  =//" ,
            "/ /"
    };


    public static final String STARTUP_MESSAGE = EQUALS_SEPERATOR + "\n" +
            " ________  ________  ___      ___ _______   ________   _________  ___  ___  ________  _______           ________  ________  _____ ______   _______      \n" +
            "|\\   __  \\|\\   ___ \\|\\  \\    /  /|\\  ___ \\ |\\   ___  \\|\\___   ___\\\\  \\|\\  \\|\\   __  \\|\\  ___ \\         |\\   ____\\|\\   __  \\|\\   _ \\  _   \\|\\  ___ \\     \n" +
            "\\ \\  \\|\\  \\ \\  \\_|\\ \\ \\  \\  /  / | \\   __/|\\ \\  \\\\ \\  \\|___ \\  \\_\\ \\  \\\\\\  \\ \\  \\|\\  \\ \\   __/|        \\ \\  \\___|\\ \\  \\|\\  \\ \\  \\\\\\__\\ \\  \\ \\   __/|    \n" +
            " \\ \\   __  \\ \\  \\ \\\\ \\ \\  \\/  / / \\ \\  \\_|/_\\ \\  \\\\ \\  \\   \\ \\  \\ \\ \\  \\\\\\  \\ \\   _  _\\ \\  \\_|/__       \\ \\  \\  __\\ \\   __  \\ \\  \\\\|__| \\  \\ \\  \\_|/__  \n" +
            "  \\ \\  \\ \\  \\ \\  \\_\\\\ \\ \\    / /   \\ \\  \\_|\\ \\ \\  \\\\ \\  \\   \\ \\  \\ \\ \\  \\\\\\  \\ \\  \\\\  \\\\ \\  \\_|\\ \\       \\ \\  \\|\\  \\ \\  \\ \\  \\ \\  \\    \\ \\  \\ \\  \\_|\\ \\ \n" +
            "   \\ \\__\\ \\__\\ \\_______\\ \\__/ /     \\ \\_______\\ \\__\\\\ \\__\\   \\ \\__\\ \\ \\_______\\ \\__\\\\ _\\\\ \\_______\\       \\ \\_______\\ \\__\\ \\__\\ \\__\\    \\ \\__\\ \\_______\\\n" +
            "    \\|__|\\|__|\\|_______|\\|__|/       \\|_______|\\|__| \\|__|    \\|__|  \\|_______|\\|__|\\|__|\\|_______|        \\|_______|\\|__|\\|__|\\|__|     \\|__|\\|_______|" +
            "\n" + EQUALS_SEPERATOR;

    public static final String GAME_WON = EQUALS_SEPERATOR + "\n" +
            "____    ____  ______    __    __     ____    __    ____  __  .__   __.  __  \n" +
            "\\   \\  /   / /  __  \\  |  |  |  |    \\   \\  /  \\  /   / |  | |  \\ |  | |  | \n" +
            " \\   \\/   / |  |  |  | |  |  |  |     \\   \\/    \\/   /  |  | |   \\|  | |  | \n" +
            "  \\_    _/  |  |  |  | |  |  |  |      \\            /   |  | |  . `  | |  | \n" +
            "    |  |    |  `--'  | |  `--'  |       \\    /\\    /    |  | |  |\\   | |__| \n" +
            "    |__|     \\______/   \\______/         \\__/  \\__/     |__| |__| \\__| (__) \n";


    public static final String FILE_TRACK11 = "src/main/Game/finalcut.txt";

}
