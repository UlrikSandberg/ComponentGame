package asteroids;

import data.Entity;
import data.World;



/**
 *
 * @author corfixen
 */
public interface IAsteroidSplitter {

    void createSplitAsteroid(Entity e, World world);
}
