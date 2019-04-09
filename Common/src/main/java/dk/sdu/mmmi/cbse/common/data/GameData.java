package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.events.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameData {

    private float delta;
    private int displayWidth;
    private int displayHeight;
    private final GameKeys keys = new GameKeys();
    private List<Event> events = new CopyOnWriteArrayList<>();
    private float playerPositionY;
    private float playerPositionX;
    private float playerSpeed;
    private float radians;

    public void addEvent(Event e) {
        events.add(e);
    }

    public void removeEvent(Event e) {
        events.remove(e);
    }

    public List<Event> getEvents() {
        return events;
    }

    public GameKeys getKeys() {
        return keys;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public float getDelta() {
        return delta;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public <E extends Event> List<Event> getEvents(Class<E> type, String sourceID) {
        List<Event> r = new ArrayList();
        for (Event event : events) {
            if (event.getClass().equals(type) && event.getSource().getID().equals(sourceID)) {
                r.add(event);
            }
        }

        return r;
    }
    
    public float getPlayerPositionX() {
        return this.playerPositionX;
    }
    
    public float getPlayerPositionY() {
        return this.playerPositionY;
    }
    
    public void setPlayerPositionX(float playerPositionX) {
        this.playerPositionX = playerPositionX;
    }
    
    public void setPlayerPositionY(float playerPositionY) {
        this.playerPositionY = playerPositionY;
    }
    
    public void setSpeed (float speed) {
        this.playerSpeed = speed;
    }
    
    public float getSpeed () {
        return this.playerSpeed;
    }
    
    public float getRadians() {
        return this.radians;
    }
    
    public void setRadians(float radians) {
        this.radians = radians;
    }
}
