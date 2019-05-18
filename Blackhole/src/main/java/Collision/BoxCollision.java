/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collision;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ulriksandberg
 */
public class BoxCollision {
    private Coordinates lowerLeft;
    private Coordinates lowerRight;
    private Coordinates upperLeft;
    private Coordinates upperRight;

    public BoxCollision(Coordinates lowerLeft, Coordinates lowerRight, Coordinates upperLeft, Coordinates upperRight) {
        this.lowerLeft = lowerLeft;
        this.lowerRight = lowerRight;
        this.upperLeft = upperLeft;
        this.upperRight = upperRight;
    }

    public Coordinates getLowerLeft() {
        return lowerLeft;
    }

    public void setLowerLeft(Coordinates lowerLeft) {
        this.lowerLeft = lowerLeft;
    }

    public Coordinates getLowerRight() {
        return lowerRight;
    }

    public void setLowerRight(Coordinates lowerRight) {
        this.lowerRight = lowerRight;
    }

    public Coordinates getUpperLeft() {
        return upperLeft;
    }

    public void setUpperLeft(Coordinates upperLeft) {
        this.upperLeft = upperLeft;
    }

    public Coordinates getUpperRight() {
        return upperRight;
    }

    public void setUpperRight(Coordinates upperRight) {
        this.upperRight = upperRight;
    }

    public List<Coordinates> GetCoordinateList()
    {
        List<Coordinates> list = new ArrayList<>();
        
        list.add(lowerLeft);
        list.add(lowerRight);
        list.add(upperLeft);
        list.add(upperRight);
        
        return list;
    }
}
