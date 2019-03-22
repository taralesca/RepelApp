package util;

import java.util.Date;
import java.util.Objects;

public abstract class Entity {
    Date creationDate;
    Date modificationDate;

    public Entity() {

    }
    public Entity(Date creationDate, Date modificationDate) {
        this.creationDate = Objects.requireNonNull(creationDate);
        this.modificationDate = Objects.requireNonNull(modificationDate);
    }

    public abstract Boolean create();
    public abstract Boolean read();
    public abstract Boolean update();
    public abstract Boolean delete();
}
