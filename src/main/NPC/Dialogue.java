package main.NPC;

import java.util.LinkedHashMap;
import java.util.Map;

public class Dialogue {
    private final String prompt;
    private final Map<String, Integer> options;
    private final Alignments alignment;

    public Dialogue(String p){
        this.prompt = p;
        options = new LinkedHashMap<>();
        this.alignment = Alignments.NEUTRAL;
    }

    public Dialogue(String p, Alignments a){
        this.prompt = p;
        options = new LinkedHashMap<>();
        this.alignment = a;
    }

    public Dialogue(String p, Map<String,Integer> options){
        this.prompt = p;
        this.options = new LinkedHashMap<>(options);
        this.alignment = Alignments.NEUTRAL;
    }

    public Dialogue(String p, Map<String,Integer> options, Alignments a){
        this.prompt = p;
        this.options = new LinkedHashMap<>(options);
        this.alignment = a;
    }

    public String getPrompt() {
        return prompt;
    }

    public Map<String, Integer> getOptions() {
        return options;
    }

    public Alignments getAlignment() {
        return alignment;
    }
}
