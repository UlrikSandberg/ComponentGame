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
import dk.sdu.mmmi.cbse.common.data.entityparts.SizePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.time.LocalDateTime;
import java.util.UUID;
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
        int size = 80;
        
        int baseLife = baseLifePart.getLife() - 1;
        
        Entity smallAsteroid1 = new Asteroid();   
        Entity smallAsteroid2 = new Asteroid();
        Entity smallAsteroid3 = new Asteroid();
        Entity smallAsteroid4 = new Asteroid();
        
        //Set image from e if such exists
        smallAsteroid1.setSprite(e.getSprite());
        smallAsteroid2.setSprite(e.getSprite());
        smallAsteroid3.setSprite(e.getSprite());
        smallAsteroid4.setSprite(e.getSprite());
        
        if(baseLife == 2)
        {
            speed = (float) Math.random() * 30f + 70f;
            radius = 10;
            size = 40;
        }
        else if (baseLife == 1)
        {
            speed = (float) Math.random() * 10f + 50f;
            radius = 6;
            size = 25;
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
        smallAsteroid1.add(new SizePart(size, size));
        smallAsteroid1.add(new CreatedMetaDataPart(LocalDateTime.now()));
        
        world.addEntity(smallAsteroid1);
        
        smallAsteroid2.setRadius(radius);
        
        PositionPart astPositionPart2 = new PositionPart(basePositionPart.getX() + -10, basePositionPart.getY() + -10, radians + 0.5f);
        smallAsteroid2.add(new MovingPart(0, 5000, speed, 0));
        smallAsteroid2.add(astPositionPart2);
        smallAsteroid2.add(new LifePart(baseLife));
        smallAsteroid2.add(new SplitterPart(smallAsteroid2.getID()));
        smallAsteroid2.add(new SizePart(size, size));
        smallAsteroid2.add(new CreatedMetaDataPart(LocalDateTime.now()));
        
        world.addEntity(smallAsteroid2);
        
        smallAsteroid3.setRadius(radius);
        
        PositionPart astPositionPart3 = new PositionPart(basePositionPart.getX() + -10, basePositionPart.getY() + -10, radians + 0.25f);
        smallAsteroid3.add(new MovingPart(0, 5000, speed, 0));
        smallAsteroid3.add(astPositionPart3);
        smallAsteroid3.add(new LifePart(baseLife));
        smallAsteroid3.add(new SplitterPart(smallAsteroid3.getID()));
        smallAsteroid3.add(new SizePart(size, size));
        smallAsteroid3.add(new CreatedMetaDataPart(LocalDateTime.now()));
        
        world.addEntity(smallAsteroid3);
        
        smallAsteroid4.setRadius(radius);
        
        PositionPart astPositionPart4 = new PositionPart(basePositionPart.getX() + -10, basePositionPart.getY() + -10, radians - 0.25f);
        smallAsteroid4.add(new MovingPart(0, 5000, speed, 0));
        smallAsteroid4.add(astPositionPart4);
        smallAsteroid4.add(new LifePart(baseLife));
        smallAsteroid4.add(new SplitterPart(smallAsteroid4.getID()));
        smallAsteroid4.add(new SizePart(size, size));
        smallAsteroid4.add(new CreatedMetaDataPart(LocalDateTime.now()));
        
        world.addEntity(smallAsteroid4);
        
        world.removeEntity(e);
    }
}
