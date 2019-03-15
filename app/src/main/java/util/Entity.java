package util;

import java.util.Date;

public abstract class Entity {
    Date creationDate;
    Date modificationDate;

    public abstract Boolean create();
    public abstract Boolean read();
    public abstract Boolean update();
    public abstract Boolean delete();
}
