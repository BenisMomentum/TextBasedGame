package main.Game.Cutscenes;

import main.Colors;

import main.Entities.Player;
import main.Game.GameConditions.GameWon;
import main.TextConstants;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Track11 extends Cutscene{

    public Track11(Player player) throws GameWon {
        super(player);

        this.play();
    }

    private void play() throws GameWon {

        try {

            Thread.sleep(pause_dur);

            System.out.print("You're at home finally...");

            Thread.sleep(1000L);

            printSlowly("home...?\n",300L);

            Thread.sleep(pause_dur);

            System.out.print("They're going to take it any day now...");
            printSlowly(Colors.RED + "it was your fault you know?\n",300L);

            Thread.sleep(pause_dur);

            System.out.print(Colors.RESET + "You didn't HAVE to hit him...");
            printSlowly("after all...",350L);
            printSlowly(Colors.RED + "words are just words\n" + Colors.RESET,400L);

            Thread.sleep(pause_dur);

            System.out.println("Whatever, it's ok :)");

            printSlowly("I'm sure\n", 300L);

            Thread.sleep(pause_dur);

            if(player.getAlignment() == player.getMAXAlign()){
                play2(pause_dur);
            } else{
                throw new GameWon(
                        determineAlignment() //Gives either 1 or 2 depending on how good the player has been.
                );
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void play2(long pause_dur) throws InterruptedException, GameWon {
        Thread.sleep(pause_dur);

        System.out.println("\n" + "Except for one");

        Thread.sleep(1000L);

        System.out.println(" little ");

        Thread.sleep(1000L);

        System.out.println("detail :)");

        Thread.sleep(pause_dur);

        printSlowly(Colors.RED_BRIGHT +
                "You have a habit of letting them die in your arms.\n".toUpperCase(), 100L);

        Thread.sleep(pause_dur);

        printSlowly("WHY?\n",500L);

        Thread.sleep(1000L);

        System.out.println("YOUR FATHER");

        Thread.sleep(1000L);

        System.out.print("BAYO");

        Thread.sleep(1000L);

        printSlowly(".....", 1000L);

        System.out.print("\n" + "You still have that bayonet on you right?");
        printSlowly("...\n", 500L);

        Thread.sleep(pause_dur);

        System.out.println("YOU KNOW WHAT TO DO" + Colors.RESET);

        printSlowly("...", 500L);
        printSlowly("...\n", 500L);

        Thread.sleep(pause_dur + pause_dur);

        printSlowly("You may as well you know?",50L);
        printSlowly("...\n", 500L);

        Thread.sleep(pause_dur);

        printSlowly("It'll end what you see at night",50L);
        printSlowly("...\n", 500L);

        Thread.sleep(pause_dur);

        printSlowly("It's ok",50L);
        printSlowly("...", 500L);
        printSlowly("she's not gonna miss you even if she does come back :)\n",50L);

        Thread.sleep(pause_dur);

        printSlowly("So what are you waiting for?\n", 50L);

        Thread.sleep(pause_dur);

        printSlowly("Do it\n", 50L);

        Thread.sleep(pause_dur * 3L);

        printSlowly("(You know that", 50L);
        printSlowly("...", 2000L);
        printSlowly("it's right...)\n", 50L);

        Thread.sleep(pause_dur);

        System.out.println("(You know that you could have saved them both)");

        Thread.sleep(pause_dur * 3L);

        System.out.print("(You know you should bare your naked feelings");
        printSlowly("...)\n", 2000L);
        printSlowly("(You know you should tear the curtain down)\n",350L);

        Thread.sleep(pause_dur * 2L);

        printSlowly(Colors.RED + "(You hold the blade in trembling hands)\n", 350L);

        printSlowly("(You prepare to make it)\n",400L);

        System.out.println(Colors.RESET + "(-BUT)");

        Thread.sleep(pause_dur);

        printSlowly("(Just then the door rang)",100L);

        Thread.sleep(pause_dur);

        display();

        Thread.sleep(pause_dur * 4L);

        printSlowly(Colors.BLUE_BRIGHT + "(You never had the nerve to make the final cut)" + Colors.RESET,200L);

        System.out.println(Colors.RESET);

        throw new GameWon(3);
    }

    private void display() {

        try(Scanner f = new Scanner(new File(TextConstants.FILE_TRACK11));){

            while(f.hasNextLine()){
                String cut = f.nextLine();

                System.out.println(cut);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private int determineAlignment(){
        if(this.player.getAlignment() >= (player.getMAXAlign() / 2)){ //If your alignment is greater than 5, then you're gucci
            return 2;
        }
        return 1;
    }
}
