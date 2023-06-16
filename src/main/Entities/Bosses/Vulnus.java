package main.Entities.Bosses;

import main.Entities.Boss;
import main.Items.Effects.StatusEffects.Adrenaline;
import main.Items.Effects.StatusEffects.Bleed;
import main.Items.Effects.StatusEffects.Regen;

public class Vulnus extends Boss {

    public Vulnus(){
        super("VULNUS", 130, 50, 5, 10);

        //SOUL DRAIN
        this.moveList.add(p -> {
            System.out.println(name + " attacks with INFERNO");
            p.takeDamage(strength - (p.getInitiative() / 10));
        });

        this.moveList.add(p -> {
            System.out.println(name + " attacks with HEADSHOT");
            p.takeDamage(strength * 4);

            p.addStatusEffect(new Bleed(2,2));
        });

        this.moveList.add(p -> {
            System.out.println(name + " uses Suppression");
            this.addStatusEffect(new Adrenaline(2,10));
            this.addStatusEffect(new Regen(3,5));
        });

    }
}
