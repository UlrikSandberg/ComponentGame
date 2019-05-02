/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author ulriksandberg
 */
public class GravityPart implements EntityPart {

    private double gravitationalPullDistance;

    public GravityPart(double gravitationalPullDistance)
    {
        this.gravitationalPullDistance = gravitationalPullDistance;
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
