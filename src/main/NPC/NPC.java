package main.NPC;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class NPC {
    protected String name;

    protected NPCList type;

    protected boolean hasTalked;

    protected Map<Integer, Dialogue> dialogue = new LinkedHashMap<>();

    /*
    Essentially this is how its going to work:

    Dialogue
    =========
    1: option
    2: another option
    =========

    Ex. PLAYER inputs 1.

    input = sc.nextInt() [in our cases input = 1]

    The option KEYS will be stored externally as an ArrayList

    String optionKey = ArrayList.get(input);

    Integer dialogueKey = options.get(optionKey);

    String response = dialogue.get(dialogueKey);

    Alternatively, the ALIGNMENT of the dialogue will affect the Player's alignment

    granted the DATA (options and dialogue) will be stored inside the NPC,
    the rest is done externally through a dialogue handler.
     */

    public NPCList getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Map<Integer, Dialogue> getDialogue() {
        return dialogue;
    }

}
