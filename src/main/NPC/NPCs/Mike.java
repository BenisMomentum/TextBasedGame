package main.NPC.NPCs;

import main.NPC.Dialogue;
import main.NPC.NPC;
import main.NPC.NPCList;

public class Mike extends NPC {

    public Mike(){
        this.name = "Mike";

        this.type = NPCList.MIKE;

        this.dialogue.put(0, new Dialogue("Hi, how can I help you?")); //Greeting

        this.dialogue.get(0).getOptions().put("Who are you?",1);
        this.dialogue.get(0).getOptions().put("Are there any interesting places around here?",2);
        this.dialogue.get(0).getOptions().put("Who am I?",3);
        this.dialogue.get(0).getOptions().put("LEAVE",-1);

        //FIRST WAVE
        this.dialogue.put(1, new Dialogue("I'm Mike. I own this COFFEE SHOP."));
        this.dialogue.get(1).getOptions().put("Go Back.",0);

        this.dialogue.put(2, new Dialogue("Eh, dunno. I heard that EDEN PLAZA has some interesting stuff there. \nIt should be just WEST of here."));
        this.dialogue.get(2).getOptions().put("What kind of stuff?",4);
        this.dialogue.get(2).getOptions().put("Go Back.",0);

        this.dialogue.put(3, new Dialogue("I don't know, I just work here."));
        this.dialogue.get(3).getOptions().put("Go Back.",0);

        //SECOND WAVE
        this.dialogue.put(4, new Dialogue("Food, Clothes, almost anything, really."));
        this.dialogue.get(4).getOptions().put("Go Back.",0);
    }
}
