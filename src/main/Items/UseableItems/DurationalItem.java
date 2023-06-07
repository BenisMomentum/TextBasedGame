package main.Items.UseableItems;

import main.Items.Rarity;

public class DurationalItem extends UseableItem{

    protected int value;
    protected int duration;

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
