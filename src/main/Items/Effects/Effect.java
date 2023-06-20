package main.Items.Effects;

import main.Entities.Entity;

public interface Effect {

    /*
    * Setups up the common functions for the Effect
    * Should be self explanatory but:
    *
    * UseEffect - for ACTIVE effects like Lifesteal/Applying Bleed
    * ADD/REMOVE EFFECT - For PASSIVE effects like Vitality and Swiftness
    */

    void useEffect(Entity e);
    void addEffect(Entity e);
    void removeEffect(Entity e);

}
