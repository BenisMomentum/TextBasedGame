package main.Entities.Bosses;

import main.Entities.Boss;
import main.Items.Effects.StatusEffects.Bleed;

public class Desperato extends Boss {

    public Desperato(){
        super("DESPERATO", 150, 10, 15, 20);

        //SOUL DRAIN
        this.moveList.add(p -> {
            System.out.println(name + " attacks with SOUL DRAIN ");
            p.takeDamage((strength * 2)/3);
            rejuvenate(5);
        });

        this.moveList.add(p -> {
            System.out.println(name + " attacks with MEMENTO MORI");
            p.takeDamage(strength);

            p.addStatusEffect(new Bleed(2,2));
        });

    }

}
