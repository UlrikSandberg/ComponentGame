/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;

/**
 *
 * @author ulriksandberg
 */
public class NonEntity implements Serializable {
    
    private String id;
    private boolean isLoaded;
    private String sprite;
    
    private boolean isDrawn;

    public boolean isIsDrawn() {
        return isDrawn;
    }

    public void setIsDrawn(boolean isDrawn) {
        this.isDrawn = isDrawn;
    }
    
    private int positionX;
    private int positionY;
    
    private int width;
    private int height;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    } 
}