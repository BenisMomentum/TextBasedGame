package main.NPC.NPCs;

import main.NPC.Alignments;
import main.NPC.Dialogue;
import main.NPC.NPC;
import main.NPC.NPCList;

public class Ves extends NPC {

    public Ves(){
        this.name = "Ves";
        this.type = NPCList.RAYMOND;

        this.dialogue.put(0, new Dialogue("Oh, hey! Haven't seen you in a while")); //Greeting
        this.dialogue.get(0).getOptions().put("Hey, Ves.",1);

        //First wave

        this.dialogue.put(1, new Dialogue("How've you been?", Alignments.GOOD));
        this.dialogue.get(1).getOptions().put("I don't want to talk about it",2);
        this.dialogue.get(1).getOptions().put("I'm alright",3);

        this.dialogue.put(2, new Dialogue("Oh...alright I guess.",Alignments.GOOD));
        this.dialogue.get(2).getOptions().put("What about you?",4);
        this.dialogue.get(2).getOptions().put("Yeah, sorry...",5);

        this.dialogue.put(3, new Dialogue("Nice. Good to hear", Alignments.BAD));
        this.dialogue.get(3).getOptions().put("What about you?",4);

        //Second wave

        this.dialogue.put(4, new Dialogue("I've been good, overall just working, today I finally got a chance to relax so I came here.",Alignments.GOOD));
        this.dialogue.get(4).getOptions().put("Sounds nice.",6);

        this.dialogue.put(5, new Dialogue("It's alright, I can understand.",Alignments.GOOD));
        this.dialogue.get(5).getOptions().put("What about you?",4);
        this.dialogue.get(5).getOptions().put("Sounds nice.",6);

        this.dialogue.put(6,new Dialogue("So what's going on?"));
        this.dialogue.get(6).getOptions().put("Nothing much really, just wanted to roam the area for a bit",7);
        this.dialogue.get(6).getOptions().put("Just browsing",8);
        this.dialogue.get(6).getOptions().put("I just wanted to see the place for a bit, I'm gonna head out.",11);

        //Third wave

        this.dialogue.put(7, new Dialogue("Ah ok. Any reason?", Alignments.GOOD));
        this.dialogue.get(7).getOptions().put("Not...really",9);
        this.dialogue.get(7).getOptions().put("Just browsing",8);

        this.dialogue.put(8, new Dialogue("Fair enough",Alignments.BAD));
        this.dialogue.get(8).getOptions().put("Anyways, I'll be...going now. I was just passing through",10);

        this.dialogue.put(9, new Dialogue("Huh...alright", Alignments.GOOD));
        this.dialogue.get(9).getOptions().put("I'll be...going now.",11);

        this.dialogue.put(10, new Dialogue("Alright....see ya I guess", Alignments.BAD));
        this.dialogue.get(10).getOptions().put("[Leave]",-1);

        this.dialogue.put(11, new Dialogue("Alright, see ya.", Alignments.GOOD));
        this.dialogue.get(11).getOptions().put("[Leave]",-1);

    }

}
