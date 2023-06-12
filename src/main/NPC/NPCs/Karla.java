package main.NPC.NPCs;

import main.NPC.Alignments;
import main.NPC.Dialogue;
import main.NPC.NPC;
import main.NPC.NPCList;

public class Karla extends NPC {

    public Karla(){
        this.name = "Karla";
        this.type = NPCList.KARLA;

        this.dialogue.put(0, new Dialogue("Hey, XXXXX! Long time no see! How are you?")); //Greeting
        this.dialogue.get(0).getOptions().put("Hey Karla. I've been ok.",1);
        this.dialogue.get(0).getOptions().put("I've been great, thank you. How are you?",2);
        this.dialogue.get(0).getOptions().put("I've been alright, I just wanted to check in. [Leave].",11);


        //FIRST WAVE
        this.dialogue.put(1, new Dialogue("Now I know that's a lie, what's wrong? After that incident at your job you been holding up ok?", Alignments.GOOD));
        this.dialogue.get(1).getOptions().put("Yeah, just wanted to roam for a bit, that's all.",3);
        this.dialogue.get(1).getOptions().put("Not really, but I'll be fine.",4);

        this.dialogue.put(2, new Dialogue("Business is going up thankfully! Other than that, I've been doing fine! \n " +
                "I..uhh...sorry about your job though, shame it had to be publicized like that...", Alignments.BAD));

        this.dialogue.get(2).getOptions().put("It's alright, glad to hear that business is going up though.",5);

        //SECOND WAVE
        this.dialogue.put(3,new Dialogue("As long as you're not planning to do anything dangerous...", Alignments.GOOD));
        this.dialogue.get(3).getOptions().put("[Go Back]",6);

        this.dialogue.put(4, new Dialogue("If you say so.", Alignments.GOOD));
        this.dialogue.get(4).getOptions().put("[Go Back]",6);

        this.dialogue.put(5, new Dialogue("Thank you!", Alignments.GOOD));
        this.dialogue.get(5).getOptions().put("[Go Back]",6);

        this.dialogue.put(6, new Dialogue("Well, is there anything I can do for you?"));
        this.dialogue.get(6).getOptions().put("Not really, I just wanted to see the place. I'll see you later, Karla.",10);
        this.dialogue.get(6).getOptions().put("Actually uhh....you need any help around here? I'm in a tight spot for some work.",7);
        this.dialogue.get(6).getOptions().put("How're the others?",8);


        //THIRD WAVE
        this.dialogue.put(7,new Dialogue("Sure, you can come by tomorrow since I'm just finishing up.", Alignments.GOOD));
        this.dialogue.get(7).getOptions().put("Great! Thank you. I'll see you tomorrow then!",10);
        this.dialogue.get(7).getOptions().put("Holdon, just one more thing...",6);

        this.dialogue.put(8,new Dialogue("Haven't seen them too often, though they seem fine whenever they come in.", Alignments.GOOD));
        this.dialogue.get(8).getOptions().put("Fair...I just wanted to ask that...I'll see you later",10);
        this.dialogue.get(8).getOptions().put("Fair, just one more thing though...",6);

        this.dialogue.put(10, new Dialogue("Alright, see ya!"));
        this.dialogue.get(10).getOptions().put("Holdon, just one more thing...",6);
        this.dialogue.get(10).getOptions().put("[Leave]",-1);

        this.dialogue.put(11, new Dialogue("Alright then...see ya", Alignments.BAD));
        this.dialogue.get(11).getOptions().put("Holdon, just one more thing...",6);
        this.dialogue.get(11).getOptions().put("[Leave]",-1);

    }

}
