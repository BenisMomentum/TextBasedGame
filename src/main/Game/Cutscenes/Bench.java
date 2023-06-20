package main.Game.Cutscenes;

import main.Colors;
import main.Entities.Player;

public class Bench extends Cutscene{

    public Bench(Player player) {
        super(player);

        try{
            this.play();
        } catch(InterruptedException e){

        }
    }

    private void play() throws InterruptedException {

        String cyan = Colors.CYAN_BRIGHT;
        String red = Colors.RED;

        printSlowly(". . . . .\n",350L);

        System.out.println(cyan + "Her: I love " + red + "w" + cyan + "atching t" + red + "hem" + cyan + " you know?" + Colors.RESET);

        Thread.sleep(pause_dur);

        System.out.print(cyan + "Her: The blue sky");

        printSlowly(". . .",300L);

        printSlowly(red + "t" + cyan + "he ships going to heave" + red + "n a" + cyan + "nd back.\n" + Colors.RESET, 100L);

        Thread.sleep(pause_dur);

        printSlowly(cyan + "Her: " + red + "W" + cyan + "hile everything " + red + "a" + cyan + "round u" + red + "s" + cyan + " burns.\n",100L);
        printSlowly(". . . . .",300L);
        System.out.println("I'd rather be " + red + "her" + cyan + "e." + Colors.RESET);

        Thread.sleep(pause_dur);

        System.out.println("\n" + cyan + "Her: With you");

    }
}
