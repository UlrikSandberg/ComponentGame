/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obstacles;

import data.Entity;
import data.GameData;
import data.World;
import entityparts.LifePart;
import entityparts.PositionPart;
import services.IEntityProcessingService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author oskar
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class ObstaclesControlSystem implements IEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        for(Entity obstacle : world.getEntities(Obstacle.class)){
            PositionPart positionPart = obstacle.getPart(PositionPart.class);
            positionPart.process(gameData, obstacle);
            LifePart part = obstacle.getPart(LifePart.class);
            part.setLife(Integer.MAX_VALUE);
        }
    }
}
