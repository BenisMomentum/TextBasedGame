package main.NPC;

import main.Entities.Player;
import main.TextConstants;
import java.util.ArrayList;
import java.util.Scanner;

public class DialogueController {

    private NPC npc;
    private Player player;
    private Scanner sc = new Scanner(System.in);

    public DialogueController(NPC npc, Player player){

        //Initializes the Dialogue Controller and initializes the npc and player variables. Then starts the dialogue loop

        this.npc = npc;
        this.player = player;

        startDialogueLoop();
    }

    private void startDialogueLoop() {

        //Initializes the input and dialog index, as well as the options.

        String input = "";
        int dialogIndex = 0;
        ArrayList<String> options = null;

        while(dialogIndex != -1){ //While the player has not hit the leave option with the -1 value
            input = "";

            System.out.println(this.npc.name + ": " + this.npc.dialogue.get(dialogIndex).getPrompt()); //Gets the dialogue prompt
            System.out.println("\n" + TextConstants.EQUALS_SEPERATOR + "\n");

            options = new ArrayList<>(this.npc.dialogue.get(dialogIndex).getOptions().keySet()); //Gets the keys from the npc dialog options and prints them out

            for(int i = 0; i < options.size(); i++){
                System.out.println(i + " | " + options.get(i));
            }

            System.out.println(TextConstants.EQUALS_SEPERATOR + "\n" + "(Enter a number for your choice):");

            try{
                int c = Integer.parseInt(sc.nextLine().trim()); //Scans and cleans up number input

                dialogIndex = this.npc.dialogue.get(dialogIndex).getOptions().get(options.get(c)); //Extracts the index FROM the dialog option

                try{ //This is the dumbest fix for an IOOB exception, the other try catch should have caught it but oh well.

                    switch(npc.getDialogue().get(dialogIndex).getAlignment()){

                        case GOOD -> {
                            player.incAlign();
                        }
                        case BAD -> {
                            if(!npc.hasTalked){
                                player.decAlign();
                            }
                        }
                        default -> {
                        }
                    }

                } catch (IndexOutOfBoundsException e ){
                    System.out.println("\n"+ "(Enter an option!");
                }
            } catch(NumberFormatException | IndexOutOfBoundsException e){
                System.out.println("\n"+ "(Enter a valid number!)");
            }

            if(!npc.hasTalked) npc.hasTalked = true; //Sets it to true if you didn't talk to the NPC before.
        }
    }


}
