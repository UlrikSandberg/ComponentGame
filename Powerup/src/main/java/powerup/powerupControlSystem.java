/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powerup;

import data.Entity;
import data.GameData;
import data.World;
import entityparts.CollisionDetectionPart;
import entityparts.PositionPart;
import entityparts.SplitterPart;
import services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;


/*
 *
 * @author casperhasnsen
 */

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class powerupControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity powerup : world.getEntities(powerup.class)) {
            
            PositionPart positionPart = powerup.getPart(PositionPart.class);
            CollisionDetectionPart splitterPart = powerup.getPart(CollisionDetectionPart.class);
            
            if (splitterPart.getISCollited()) {
                world.removeEntity(powerup);
                
            }
    }
    
    
   
    }
    
}
