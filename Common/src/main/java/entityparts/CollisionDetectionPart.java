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
 * @author casperhasnsen
 */
public class CollisionDetectionPart implements EntityPart{

    
    private Boolean ISCollited = false;
    
    
    

    public Boolean getISCollited() {
        return ISCollited;
    }

    public void setISCollited(Boolean ISCollited) {
        this.ISCollited = ISCollited;
    }
    
    
    @Override
    public void process(GameData gameData, Entity entity) {
        
    }
    
}
