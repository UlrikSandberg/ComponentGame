/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.sdu.mmmi.cbse.score;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ScorePart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Mark
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class ScoreProcessing implements IEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        
        
        for (Entity e : world.getEntities()){
            if(e.getPart(ScorePart.class) != null){
                ScorePart scorePart = e.getPart(ScorePart.class);
                scorePart.addPoints(1);
            }                    
        }
    }
}
