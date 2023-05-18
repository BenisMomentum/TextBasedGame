package main.Game.BattleExceptions;

import main.TextConstants;

public class PlayerLostException extends Exception{
    public final String message = TextConstants.BATTLE_LOSE;

    public PlayerLostException(){

    }
}
