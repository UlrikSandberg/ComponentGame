package entityparts;

import data.Entity;
import data.GameData;

public class ShootingPart implements EntityPart {

    private boolean isShooting;
    private String ID;

    public ShootingPart(String id) {
        this.ID = id;
    }

    public boolean isShooting() {
        return this.isShooting;
    }

    public void setIsShooting(boolean b) {
        this.isShooting = b;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
