/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.obstacles;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.GravityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.NonCollidable;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SizePart;
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

    private Entity obstacle_blueStar, obstacle_deathStar, obstacle_moon, obstacle_redStar;
    
    @Override
    public void start(GameData gameData, World world) {
        
        obstacle_blueStar = createObstacle(gameData);
        obstacle_blueStar.setSprite(new File("").getAbsolutePath() + "/Obstacles/target/Obstacles-1.0-SNAPSHOT.jar!/assets/images/blue_star.png");
        world.addEntity(obstacle_blueStar);
        
        obstacle_deathStar = createObstacle(gameData);
        obstacle_deathStar.setSprite(new File("").getAbsolutePath() + "/Obstacles/target/Obstacles-1.0-SNAPSHOT.jar!/assets/images/death_star.png");
        world.addEntity(obstacle_deathStar);
        
        obstacle_moon = createObstacle(gameData);
        obstacle_moon.setSprite(new File("").getAbsolutePath() + "/Obstacles/target/Obstacles-1.0-SNAPSHOT.jar!/assets/images/moon.png");
        world.addEntity(obstacle_moon);
        
        obstacle_redStar = createObstacle(gameData);
        obstacle_redStar.setSprite(new File("").getAbsolutePath() + "/Obstacles/target/Obstacles-1.0-SNAPSHOT.jar!/assets/images/red_star.png");
    }

    @Override
    public void stop(GameData gameData, World world) {
        for(Entity obstacle : world.getEntities(Obstacle.class)){
            world.removeEntity(obstacle);
        }
    }
    
    private Entity createObstacle(GameData gameData){
        float x = new Random().nextFloat() * gameData.getDisplayWidth() / 2;
        float y = new Random().nextFloat() * gameData.getDisplayHeight() / 2;
        
        Entity obstacle = new Obstacle();
        obstacle.setRadius(Obstacle.DEFAULT_SIZE / 2);
        obstacle.add(new PositionPart(x, y, 1));
        obstacle.add(new LifePart(Integer.MAX_VALUE));
        obstacle.add(new SizePart(Obstacle.DEFAULT_SIZE, Obstacle.DEFAULT_SIZE));
        obstacle.add(new GravityPart(800)); 
        
        
        return obstacle;
    }
    
}
