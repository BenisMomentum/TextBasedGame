package main;

import main.Game.Game;
import org.fusesource.jansi.AnsiConsole;


public class Main {

    public static void main(String[] args){

        AnsiConsole.systemInstall();

        Game g = new Game();

        AnsiConsole.systemUninstall();
    }

}
