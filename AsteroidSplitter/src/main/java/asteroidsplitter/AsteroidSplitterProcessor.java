/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroidsplitter;

import asteroids.Asteroid;
import asteroids.IAsteroidSplitter;
import data.Entity;
import data.GameData;
import data.World;
import entityparts.LifePart;
import entityparts.MovingPart;
import entityparts.PositionPart;
import entityparts.SplitterPart;
import services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author ulriksandberg
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class AsteroidSplitterProcessor implements IEntityProcessingService {

    private IAsteroidSplitter asteroidSplitter;
    
    public AsteroidSplitterProcessor()
    {
        asteroidSplitter = new AsteroidSplitterImplementation();
    }
    
    @Override
    public void process(GameData gameData, World world) {
        
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);
            LifePart lifePart = asteroid.getPart(LifePart.class);
            SplitterPart splitterPart = asteroid.getPart(SplitterPart.class);
            
            if(splitterPart.ShouldSplit())
            {
                asteroidSplitter.createSplitAsteroid(asteroid, world);
            }
        }
    }
}
