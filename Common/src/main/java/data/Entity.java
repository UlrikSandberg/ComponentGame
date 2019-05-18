package data;

import entityparts.EntityPart;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();
    private UUID parentId;

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public Map<Class, EntityPart> getParts() {
        return parts;
    }

    public void setParts(Map<Class, EntityPart> parts) {
        this.parts = parts;
    }

    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];
    private float radius;
    private float[] colour;
    private Map<Class, EntityPart> parts;
    
    private String sprite;
    private String spawnSound;
    private boolean isSpawnSoundLoaded;
    private boolean IsLoaded;
    private boolean isUsingBoxCollision = true;

    public boolean isIsUsingBoxCollision() {
        return isUsingBoxCollision;
    }

    public void setIsUsingBoxCollision(boolean isUsingBoxCollision) {
        this.isUsingBoxCollision = isUsingBoxCollision;
    }
    
    
    private boolean isSpawned;

    public boolean isIsSpawnSoundLoaded() {
        return isSpawnSoundLoaded;
    }

    public void setIsSpawnSoundLoaded(boolean isSpawnSoundLoaded) {
        this.isSpawnSoundLoaded = isSpawnSoundLoaded;
    }

    public boolean isIsSpawned() {
        return isSpawned;
    }

    public void setIsSpawned(boolean isSpawned) {
        this.isSpawned = isSpawned;
    }

    public String getSpawnSound() {
        return spawnSound;
    }

    public void setSpawnSound(String spawnSound) {
        this.spawnSound = spawnSound;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public boolean getIsLoaded() {
        return IsLoaded;
    }

    public void setIsLoaded(boolean IsLoaded) {
        this.IsLoaded = IsLoaded;
    }

    public Entity() {
        parts = new ConcurrentHashMap<>();
    }

    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }

    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }

    public void setRadius(float r) {
        this.radius = r;
    }

    public float getRadius() {
        return radius;
    }

    public String getID() {
        return ID.toString();
    }

    public float[] getShapeX() {
        return shapeX;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
    }

    public float[] getColour() {
        return this.colour;
    }

    public void setColour(float[] c) {
        this.colour = c;
    }
}