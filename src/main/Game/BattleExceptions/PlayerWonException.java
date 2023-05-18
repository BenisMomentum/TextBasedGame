package main.Game.BattleExceptions;

import main.TextConstants;

public class PlayerWonException extends Exception {
    public final String message = TextConstants.BATTLE_WIN;

    public PlayerWonException(){}
}
