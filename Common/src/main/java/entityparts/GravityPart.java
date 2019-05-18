/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityparts;

import data.Entity;
import data.GameData;
import java.util.function.Function;

/**
 *
 * @author ulriksandberg
 */
public class GravityPart implements EntityPart {

    private double gravitationalPullDistance;
    private double eventHorizonRadius = 20;
    private boolean hasEventHorizon = false;

    private double gravitationalPotens = -0.99;
    private double gravitationFactor = 500;

    public double getGravitationalPotens() {
        return gravitationalPotens;
    }

    public void setGravitationalPotens(double gravitationalPotens) {
        this.gravitationalPotens = gravitationalPotens;
    }

    public double getGravitationFactor() {
        return gravitationFactor;
    }

    public void setGravitationFactor(double gravitationFactor) {
        this.gravitationFactor = gravitationFactor;
    }
    
    public GravityPart(double gravitationalPullDistance)
    {
        this.gravitationalPullDistance = gravitationalPullDistance;
    }

    public double getEventHorizonRadius() {
        return eventHorizonRadius;
    }

    public void setEventHorizonRadius(double eventHorizonRadius) {
        this.eventHorizonRadius = eventHorizonRadius;
    }

    public boolean isHasEventHorizon() {
        return hasEventHorizon;
    }

    public void setHasEventHorizon(boolean hasEventHorizon) {
        this.hasEventHorizon = hasEventHorizon;
    }
    
    
    public double getGravitationalPullDistance() {
        return gravitationalPullDistance;
    }

    public void setGravitationalPullDistance(double gravitationalPullDistance) {
        this.gravitationalPullDistance = gravitationalPullDistance;
    }
    
    
    
    @Override
    public void process(GameData gameData, Entity entity) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
