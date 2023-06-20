package main.Items.UseableItems;

import main.Items.Rarity;

public class DurationalItem extends UseableItem{

    protected int value;    //It will affect the coressponding value by how much? (Regen Strength, Str buff, init buff, etc.)
    protected int duration; //Every turn this effect will execute

    public DurationalItem(Rarity r, String n, int v, int d){
        super(r,n);
        this.value = Math.abs(v);
        this.duration = Math.abs(d);
    }

    public int getValue() {
        return value;
    }

    public int getDuration() {
        return duration;
    }
}
