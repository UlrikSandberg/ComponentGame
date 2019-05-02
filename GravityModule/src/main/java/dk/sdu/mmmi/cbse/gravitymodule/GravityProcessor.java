/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.gravitymodule;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.GravityPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

/**
 *
 * @author ulriksandberg
 */
public class GravityProcessor implements IEntityProcessingService {
    
    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            
            if(e.getPart(GravityPart.class) != null)
            {
                GravityPart gPart = e.getPart(GravityPart.class);
                
                //Check for all entities, if they are within a certain radius pull them towards and if they hit the blackhole they die!
                for (Entity f : world.getEntities()) {
                    
                    
                    
                }
            }
        }
    }
}
