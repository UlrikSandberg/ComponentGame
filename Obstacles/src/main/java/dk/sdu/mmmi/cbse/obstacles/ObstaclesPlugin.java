/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.obstacles;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.io.File;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;


/**
 *
 * @author oskar
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class ObstaclesPlugin implements IGamePluginService{

    private Entity obstacle;
    
    @Override
    public void start(GameData gameData, World world) {
        obstacle = createObstacle(gameData);
        obstacle.setSprite(new File("").getAbsolutePath() + "/Obstacles/target/Obstacles-1.0-SNAPSHOT.jar!/assets/images/blue_star.png");
        world.addEntity(obstacle);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(obstacle);
    }
    
    private Entity createObstacle(GameData gameData){
        float x = new Random().nextFloat() * gameData.getDisplayWidth();
        float y = new Random().nextFloat() * gameData.getDisplayHeight();
        
        Entity obstacle = new Obstacle();
        obstacle.setRadius(Obstacle.DEFAULT_SIZE / 2);
        obstacle.add(new PositionPart(x, y, 1));
        
        return obstacle;
    }
    
}
