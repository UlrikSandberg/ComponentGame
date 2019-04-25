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
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Phillip Olsen
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class AsteroidPlugin implements IGamePluginService {

    private Entity asteroid;

    String jarUrl;
    
    @Override
    public void start(GameData gameData, World world) {
        
        asteroid = createAsteroid(gameData);
        jarUrl = new File("").getAbsolutePath() + "/Asteroid/target/Asteroid-1.0-SNAPSHOT.jar!/assets/images/comet.png";
        asteroid.setSprite(jarUrl);
        world.addEntity(asteroid);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities(Asteroid.class)) {
            world.removeEntity(e);
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
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.add(new LifePart(3));
        asteroid.add(new SplitterPart(asteroid.getID()));
        asteroid.add(new CreatedMetaDataPart(LocalDateTime.now()));
        asteroid.setColour(colour);
        UUID uuid = UUID.randomUUID();
        asteroid.add(new SplitterPart(uuid.toString()));
        asteroid.setRadius(15);
        asteroid.setSprite(jarUrl);

        return (Asteroid) asteroid;
    }
}