package main.Entities.Bosses;

import main.Entities.Boss;
import main.Items.Effects.StatusEffects.Adrenaline;
import main.Items.Effects.StatusEffects.Rage;
import main.Items.Effects.StatusEffects.Regen;

public class TheChild extends Boss {
    public TheChild(){
        super("THECHILD", 300, 20, 10, 5);

        //SOUL DRAIN
        this.moveList.add(p -> {
            System.out.println(name + " attacks with ANZIO");
            p.takeDamage(strength + 5);

            addStatusEffect(new Adrenaline(1,1));
        });

        this.moveList.add(p -> {
            System.out.println(name + " uses YOUTH");

            this.rejuvenate(15);
        });

        this.moveList.add(p -> {
            System.out.println(name + " inflicts BRAIN DAMAGE");

            p.takeDamage(2);

            p.addStatusEffect(new Adrenaline(1,-10));
            p.addStatusEffect(new Rage(1,-5));

        });

        this.moveList.add( p ->{

            System.out.println(name + " uses WALL");

            this.addStatusEffect(new Regen(1,5));

            this.armour += 3;

        });

    }
}
