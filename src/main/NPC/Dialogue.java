package main.NPC;

import java.util.LinkedHashMap;
import java.util.Map;

public class Dialogue {
    private final String prompt;
    private final Map<String, Integer> options;

    public Dialogue(String p){
        this.prompt = p;
        options = new LinkedHashMap<>();
    }

    public Dialogue(String p, Map<String,Integer> options){
        this.prompt = p;
        this.options = new LinkedHashMap<>(options);
    }

    public String getPrompt() {
        return prompt;
    }

    public Map<String, Integer> getOptions() {
        return options;
    }
}
