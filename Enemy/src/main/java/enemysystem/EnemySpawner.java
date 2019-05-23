/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enemysystem;

import data.Entity;
import data.GameData;
import data.World;
import entityparts.ControlPart;
import entityparts.LifePart;
import entityparts.MovingPart;
import entityparts.PositionPart;
import entityparts.ShootingPart;
import enemy.Enemy;
import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.TimerTask;

/**
 *
 * @author magnusm
 */
public class EnemySpawner extends TimerTask {   
    
    private GameData gameData; 
    private World world; 
    private Random rn = new Random();
    private LocalDateTime then;
    private Entity enemy;
    
    
    public EnemySpawner(GameData gameData, World world){
        this.gameData = gameData;
        this.world = world;
        this.then = LocalDateTime.now();
    }

    @Override
    public void run() {
        
        int numberOfEnemies = 0;
        
        for(Entity e : world.getEntities(Enemy.class))
        {
            numberOfEnemies++;
        }
        
        if(numberOfEnemies > 3)
        {
            return;
        }
        
        LocalDateTime now = LocalDateTime.now();
        
        long seconds = ChronoUnit.SECONDS.between(then, now);
        
        int value = (int)(seconds/45);
        
        for(int i = 0; i<value; i++){
            enemy = createEnemyShip(gameData);
            world.addEntity(enemy);
        }
    }
    
    private Entity createEnemyShip(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 150;
        float maxSpeed = 200;
        float rotationSpeed = 5;
        float x = new Random().nextFloat() * gameData.getDisplayWidth();
        float y = new Random().nextFloat() * gameData.getDisplayHeight();
        float radians = 3.1415f / 2;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 0.0f;
        colour[2] = 0.0f;
        colour[3] = 1.0f;

        Entity enemyShip = new Enemy();
        enemyShip.setRadius(8);
        enemyShip.setColour(colour);
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new LifePart(3));
        enemyShip.add(new ControlPart(true));
        enemyShip.add(new ShootingPart("fromEnemy"));
        enemyShip.setSprite(new File("").getAbsolutePath() + "/Enemy/target/Enemy-1.0-SNAPSHOT.jar!/assets/images/ufoAbove.png");
        ControlPart controlPart = enemyShip.getPart(ControlPart.class);
        controlPart.setIsEnabled(true);

        return enemyShip;
}
}
