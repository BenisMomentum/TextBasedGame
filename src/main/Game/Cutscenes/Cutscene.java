package main.Game.Cutscenes;

import main.Entities.Player;

public abstract class Cutscene {

    //The Cutscene class establishes some baselines for all the Cutscenes in the game

    //IDs and booleans for all cutscenes in the game
    public static final int TRACK11_ID = 99;
    public static final int HIGHSCHOOL_ID = 30;
    public static final int BENCH_ID = 29;

    public static boolean VISITED_BENCH = false;
    public static boolean VISITED_HIGHSCHOOL = false;

    //=======================================

    protected final long pause_dur = 1500L; //Pause duration for every Thread.sleep command

    protected final Player player; //Player for Track11's alignment check

    protected Cutscene(Player player) {
        this.player = player;
    }

    protected final void printSlowly(String input, long pauseDuration){
        try{
            for(int i = 0; i < input.length(); i++){
                System.out.print(input.charAt(i));
                Thread.sleep(pauseDuration);
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
