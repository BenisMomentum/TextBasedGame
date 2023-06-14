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
        this.npc = npc;
        this.player = player;

        startDialogueLoop();
    }

    private void startDialogueLoop() {
        String input = "";
        int dialogIndex = 0;
        ArrayList<String> options = null;

        while(dialogIndex != -1){
            input = "";

            System.out.println(this.npc.name + ": " + this.npc.dialogue.get(dialogIndex).getPrompt());
            System.out.println("\n" + TextConstants.EQUALS_SEPERATOR + "\n");

            options = new ArrayList<>(this.npc.dialogue.get(dialogIndex).getOptions().keySet()); //Gets the keys from the npc dialog options and prints them out

            for(int i = 0; i < options.size(); i++){
                System.out.println(i + " | " + options.get(i));
            }

            System.out.println(TextConstants.EQUALS_SEPERATOR + "\n" + "(Enter a number for your choice):");

            try{
                int c = Integer.parseInt(sc.nextLine().trim());

                dialogIndex = this.npc.dialogue.get(dialogIndex).getOptions().get(options.get(c)); //Extracts the index FROM the dialog option

                switch(npc.getDialogue().get(dialogIndex).getAlignment()){

                    case GOOD -> {
                        player.incAlign();
                    }
                    case BAD -> {
                        player.decAlign();
                    }
                    default -> {
                    }
                }
            } catch(NumberFormatException e){
                System.out.println("\n"+ "(Enter a number!)");
            }

        }
    }


}
