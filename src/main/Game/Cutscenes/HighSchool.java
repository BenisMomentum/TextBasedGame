package main.Game.Cutscenes;

import main.Colors;
import main.Entities.Player;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HighSchool extends Cutscene{

    public HighSchool(Player player) {
        super(player);

        try{

            if(player.getAlignment() > 3){
                this.play();
                return;
            }
            this.encryptedPlay();

        } catch(InterruptedException e){

        }
    }

    private void encryptedPlay() {
        String s = "KEFub3RoZXIgZGF5IGluIGNsYXNzLi4uc291bCBkcmFpbmluZy4uLmdvZCBkYW1taXQuLi53aHkg\n" +
                "Y2FudCBpdCBiZSBvdmVyKQoKPzogUHNzdC4uLmhleSEKCig/PyBXaHkncyBzaGUgd2hpc3Blcmlu\n" +
                "ZyB0byBtZS4uLikKCj86IFllcywgeW91IQo/OiBZb3Ugd2FubmEgcHJhbmsgdGhlIHRlYWNoZXI/";

        String newS = new String(Base64.getEncoder().encode(s.getBytes()), StandardCharsets.UTF_8);

        System.out.println(newS);

        VISITED_BENCH = false;
    }

    private void play() throws InterruptedException{

        String cyan = Colors.CYAN_BRIGHT;
        String red = Colors.RED;
        String green = Colors.GREEN_BRIGHT;
        String reset = Colors.RESET;

        printSlowly(". . . . .\n",350L);

        System.out.print(green + "(Another day in class...");

        printSlowly("soul draining", 150L);
        printSlowly("...", 1500L);
        printSlowly("teacher droning", 150L);
        printSlowly("...", 1500L);
        printSlowly("god dammit why cant it be over)\n",150L);

        Thread.sleep(pause_dur);
        System.out.print(reset);
        printSlowly(cyan + "?: Psst...hey!\n" + reset, 150L);

        Thread.sleep(750L);

        System.out.println(green + "(? Why's she whispering to me...)\n" + reset);

        Thread.sleep(750L);

        printSlowly(cyan + "?: Yes you!\n" + reset, 150L);

        Thread.sleep(750L);

        printSlowly(cyan + "?: You wanna prank the teacher?\n" + reset, 150L);

        VISITED_HIGHSCHOOL = true;

    }


}
