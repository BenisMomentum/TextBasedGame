package main.Items.Effects;

import main.Entities.Entity;

public interface Effect {
    void useEffect(Entity e);
    void addEffect(Entity e);
    void removeEffect(Entity e);

}
