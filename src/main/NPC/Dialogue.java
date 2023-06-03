package main.NPC;

import java.util.HashMap;
import java.util.Map;

public class Dialogue {
    private final String prompt;
    private final Map<String, Integer> options;

    public Dialogue(String p){
        this.prompt = p;
        options = new HashMap<>();
    }

    public Dialogue(String p, Map<String,Integer> options){
        this.prompt = p;
        this.options = new HashMap<>(options);
    }

    public String getPrompt() {
        return prompt;
    }

    public Map<String, Integer> getOptions() {
        return options;
    }
}
