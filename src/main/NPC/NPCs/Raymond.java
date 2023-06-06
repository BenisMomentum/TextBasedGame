package main.NPC.NPCs;

import main.NPC.Dialogue;
import main.NPC.NPC;
import main.NPC.NPCList;

public class Raymond extends NPC {

    public Raymond(){
        this.name = "Raymond";
        this.type = NPCList.RAYMOND;

        this.dialogue.put(0, new Dialogue("Please don't hurt me! I know that guy jumped you but I didn't!")); //Greeting
        this.dialogue.get(0).getOptions().put("It's ok I won't hurt you",1);

        //First Wave
        this.dialogue.put(1, new Dialogue("Thank you! What are you doing here?"));
        this.dialogue.get(1).getOptions().put("I was just walking?",2);
        this.dialogue.get(1).getOptions().put("Who are you?",3);
        this.dialogue.get(1).getOptions().put("Are you happy?",4);
        this.dialogue.get(1).getOptions().put("[Leave]",-1);

        this.dialogue.put(2, new Dialogue("Well don't! It's dangerous over here, as I'm sure you know."));
        this.dialogue.get(2).getOptions().put("[Go back]",1);

        this.dialogue.put(3,new Dialogue("I'm Raymond, lost my job a while back and nobody would hire me again, hence why I'm here."));
        this.dialogue.get(3).getOptions().put("[Go back]",1);

        this.dialogue.put(4, new Dialogue("In a way yes. Why?"));
        this.dialogue.get(4).getOptions().put("Just wondering...goodbye",-1);
    }

}
