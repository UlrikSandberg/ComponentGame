/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityparts;

import data.Entity;
import data.GameData;

/**
 *
 * @author Mark
 */
public class ScorePart implements EntityPart{

    
private int points;
private boolean canGetPoints = false;

    public ScorePart(int points){
        this.points = points;
    }
    
    public ScorePart(boolean canGetPoints){
        this.canGetPoints = canGetPoints;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
    public void addPoints(int points){
        this.points += points;
    }

    public boolean canGetPoints() {
        return canGetPoints;
    }

    public void setCanGetPoints(boolean canGetPoints) {
        this.canGetPoints = canGetPoints;
    }



    @Override
    public void process(GameData gameData, Entity entity) {
        
    }
    
}
