/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroidsplitter;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CreatedMetaDataPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.time.LocalDateTime;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author ulriksandberg
 */

@ServiceProviders(value = {
    @ServiceProvider(service = IAsteroidSplitter.class),})
public class AsteroidSplitterImplementation implements IAsteroidSplitter {

    @Override
    public void createSplitAsteroid(Entity e, World world) {
        
        PositionPart basePositionPart = e.getPart(PositionPart.class);
        LifePart baseLifePart = e.getPart(LifePart.class);
        
        float radians = basePositionPart.getRadians();
        float radius = 0;
        float speed = 5;
        
        int baseLife = baseLifePart.getLife() - 1;
        
        Entity smallAsteroid1 = new Asteroid();
        Entity smallAsteroid2 = new Asteroid();
        
        //Set image from e if such exists
        smallAsteroid1.setSprite(e.getSprite());
        smallAsteroid2.setSprite(e.getSprite());
        
        if(baseLife == 2)
        {
            speed = (float) Math.random() * 30f + 70f;
            radius = 10;
        }
        else if (baseLife == 1)
        {
            speed = (float) Math.random() * 10f + 50f;
            radius = 6;
        }
        else if (baseLife <= 0)
        {
            world.removeEntity(e);
            return;
        }
        
        smallAsteroid1.setRadius(radius);
        
        PositionPart astPositionPart1 = new PositionPart(basePositionPart.getX() + 10, basePositionPart.getY() + 10, radians - 0.5f);
        smallAsteroid1.add(new MovingPart(0, 5000, speed, 0));
        smallAsteroid1.add(astPositionPart1);
        smallAsteroid1.add(new LifePart(baseLife));
        smallAsteroid1.add(new SplitterPart(smallAsteroid1.getID())); 
        smallAsteroid1.add(new CreatedMetaDataPart(LocalDateTime.now()));
        
        world.addEntity(smallAsteroid1);
        
        smallAsteroid2.setRadius(radius);
        
        PositionPart astPositionPart2 = new PositionPart(basePositionPart.getX() + -10, basePositionPart.getY() + -10, radians + 0.5f);
        smallAsteroid2.add(new MovingPart(0, 5000, speed, 0));
        smallAsteroid2.add(astPositionPart2);
        smallAsteroid2.add(new LifePart(baseLife));
        smallAsteroid2.add(new SplitterPart(smallAsteroid2.getID()));
        smallAsteroid2.add(new CreatedMetaDataPart(LocalDateTime.now()));
        
        world.addEntity(smallAsteroid2);
        
        world.removeEntity(e);
    }
}
