package main.Game.GameConditions;

import main.TextConstants;

public class GameWon extends Throwable{
    private int gameWon;

    public GameWon(int a){
        this.gameWon = a;

        System.out.println(TextConstants.GAME_WON);

        if(a == 3){
            System.out.println("True Ending");
        } else if(a == 2){
            System.out.println("Good Ending");
        } else if(a == 1){
            System.out.println("Sub-Optimal Ending");
        }
    }
}
