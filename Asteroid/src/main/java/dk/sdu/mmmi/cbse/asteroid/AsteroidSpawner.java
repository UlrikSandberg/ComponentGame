/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CreatedMetaDataPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SizePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.TimerTask;
import java.util.UUID;

/**
 *
 * @author ulriksandberg
 */
public class AsteroidSpawner extends TimerTask {

    private GameData gameData;
    private World world;
    private String jarUrl = new File("").getAbsolutePath() + "/Asteroid/target/Asteroid-1.0-SNAPSHOT.jar!/assets/images/comet.png";
    private Random rn = new Random(10);
    private LocalDateTime then;
    
    public AsteroidSpawner(GameData gameData, World world)
    {
        this.gameData = gameData;
        this.world = world;
        then = LocalDateTime.now();
    }
    
    @Override
    public void run() {
        
        int numberOfEntities = 0;
        //Check the number of asteroids ingame, if the number has succeded a certain value dont spawn additional asteroids
        for (Entity e : world.getEntities(Asteroid.class)) {
            
            if(e.getPart(LifePart.class) != null)
            {
                LifePart lPart = e.getPart(LifePart.class);
                if(lPart.getLife() == 3)
                {
                    numberOfEntities++;
                }
            }
        }
        
        if(numberOfEntities > 20)
        {
            return;
        }
        
        //Calculate the number of asteroid to spawn
        LocalDateTime now = LocalDateTime.now();
        
        long seconds = ChronoUnit.SECONDS.between(then, now);
        
        int value = (int)(seconds / 30);
        
        for(int i = 0; i < value; i++)
        {
            Entity asteroid = createAsteroid(gameData);
            jarUrl = new File("").getAbsolutePath() + "/Asteroid/target/Asteroid-1.0-SNAPSHOT.jar!/assets/images/comet.png";
            asteroid.setSprite(jarUrl);
            world.addEntity(asteroid);
        }
    }
    
    private Asteroid createAsteroid(GameData gameData) {
        float speed = (float) Math.random() * 10f + 40f;
        float radians = 3.1415f / 2 + (float) Math.random();
        float x = gameData.getDisplayWidth() / 2 + 100;
        float y = gameData.getDisplayHeight() / 2 + 50;
        
        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 1.0f;
        colour[2] = 1.0f;
        colour[3] = 1.0f;

        Entity asteroid = new Asteroid();
        asteroid.add(new MovingPart(0, speed, speed, 0));
        asteroid.add(new PositionPart(rn.nextInt(gameData.getDisplayWidth()), rn.nextInt(gameData.getDisplayHeight()), radians));
        asteroid.add(new LifePart(3));
        asteroid.add(new SplitterPart(asteroid.getID()));
        asteroid.add(new SizePart(80, 80));
        asteroid.add(new CreatedMetaDataPart(LocalDateTime.now()));
        asteroid.setColour(colour);
        UUID uuid = UUID.randomUUID();
        asteroid.add(new SplitterPart(uuid.toString()));
        asteroid.setRadius(15);
        asteroid.setSprite(jarUrl);

        return (Asteroid) asteroid;
    }
}
