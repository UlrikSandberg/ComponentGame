package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CollisionDetectionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.CreatedMetaDataPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.NonCollidable;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ProjectilePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SizePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.powerupPart;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import dk.sdu.mmmi.cbse.commonprojectile.Projectile;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IPostEntityProcessingService.class),})
public class CollisionDetector implements IPostEntityProcessingService {

    
    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            
            CollidingParties collidingParties = CollidingParties.Unknown;
            
            if(e.getPart(NonCollidable.class) != null)
            {
                continue;
            }
            
            for (Entity f : world.getEntities()) {
                if (e.getID().equals(f.getID()) || f.getPart(NonCollidable.class) != null) {
                    continue;
                }
                
                //*********** SECTION - BULLET NOT COLLODING WITH THE ENTITY THAT SPAWNED THEM
                if (Collision(e, f, false)) {
                    
                    //Collision was detected...
                    collidingParties = DetermineCollidingParties(e, f);
                    
                    if(collidingParties.equals(collidingParties.BulletAndBullet))
                    {
                        continue;
                    }
                    
                    if(collidingParties.equals(collidingParties.EnemyAndBullet))
                    {
                        world.removeEntity(f);
                    }
                    
                    if(collidingParties.equals(collidingParties.BulletAndEnemy))
                    {
                        world.removeEntity(e); 
                    }
                    
                    //Remove player if collision is between enemy and player
                    if(collidingParties.equals(CollidingParties.EnemyAndPlayer))
                    {
                        world.removeEntity(f);
                        continue;
                    }
                    else if(collidingParties.equals(CollidingParties.PlayerAndEnemy))
                    {
                        world.removeEntity(e); 
                        continue;
                    }
                    
                    // avoid bullets damaging the entity that created said bullet
                    if (e.getPart(ProjectilePart.class) != null) {
                        ProjectilePart epp = e.getPart(ProjectilePart.class);
                        if (f.getPart(ShootingPart.class) != null) {
                            ShootingPart fsp = f.getPart(ShootingPart.class);
                            if (epp.getID().equals(fsp.getID())) {
                                continue;
                            }
                        }
                    }
                    // avoid bullets damaging the entity that created said bullet
                    if (f.getPart(ProjectilePart.class) != null) {
                        ProjectilePart fpp = f.getPart(ProjectilePart.class);
                        if (e.getPart(ShootingPart.class) != null) {
                            ShootingPart esp = e.getPart(ShootingPart.class);
                            if (fpp.getID().equals(esp.getID())) {
                                continue;
                            }
                        }
                    }
                    
                //********** SECTION - END : BULLET  NOT COLLIDING WITH THE ENTITY THAT SPAWNED THEM    
                    
                //********** SECTION - CHECK IF THE PLAYER HAS COLLIDED WITH THE  POWERUP
                    // get all entities with power part (player)
                    if (e.getPart(powerupPart.class) != null) {
                        
                       // player == player
                        if (f.getPart(powerupPart.class) != null) {
                            continue;
                        }
                        
                        // Get 
                        if (f.getPart(CollisionDetectionPart.class) !=null) {
                            CollisionDetectionPart collisionDetector = f.getPart(CollisionDetectionPart.class);
                            collisionDetector.setISCollited(Boolean.TRUE);
                            powerupPart pw = e.getPart(powerupPart.class);
                            pw.setIsHit(Boolean.TRUE);
                        }
                    }
                
                //********** SECTION - END : CHECK IF THE PLAYER HAS COLLIDED WITH THE  POWERUP
                    
                
                //********** SECTION - ASTEROID COLLISION DETECTION
                
                    //Check if collision is between player and asteroid
                    if(collidingParties.equals(CollidingParties.AsteroidAndPlayer))
                    {
                        world.removeEntity(f);
                        continue;
                    }
                    else if(collidingParties.equals(CollidingParties.PlayerAndAsteroid))
                    {
                        world.removeEntity(e);
                        continue;
                    }
                
                    // in case it's an asteroid, let it split
                    if (f.getPart(SplitterPart.class) != null) {
                        
                        //Check if the collision was with another asteroid
                        if(e.getPart(SplitterPart.class) != null)
                        {
                            continue;
                        }
                        
                        //Check if the entity has recently been created
                        CreatedMetaDataPart metaData = f.getPart(CreatedMetaDataPart.class);
                        
                        if(metaData != null) //Check that we are not checking collisions for just created entities
                        {
                            LocalDateTime now = LocalDateTime.now();
                            
                            long millis = ChronoUnit.MILLIS.between(metaData.getDate(), now);
                            
                            if(millis < 1000)
                            {
                                continue;
                            }
                        }
                        
                        SplitterPart fsp = f.getPart(SplitterPart.class);

                        // in case an asteroid collides with its children
                        if (e.getPart(SplitterPart.class) != null) {
                            SplitterPart esp = e.getPart(SplitterPart.class);
                            if (fsp.getID().equals(esp.getID())) {
                                continue;
                            }
                        }
                        
                        // if its the first time the asteroid has been processed, let it split
                        if (!fsp.hasSplit()) {
                            fsp.setShouldSplit(true);
                            continue;
                        }
                    }
                    
                    // in case it's an asteroid, let it split
                    if (e.getPart(SplitterPart.class) != null) {
                        SplitterPart esp = e.getPart(SplitterPart.class);

                        // in case an asteroid collides with its children
                        if (f.getPart(SplitterPart.class) != null) {
                            SplitterPart fsp = f.getPart(SplitterPart.class);
                            if (esp.getID().equals(fsp.getID())) {
                                continue;
                            }
                        }
                        // if its the first time the asteroid has been processed, let it split
                        if (!esp.hasSplit()) {
                            esp.setShouldSplit(true);
                            continue;
                        }
                    }
                    
                    if(e.getPart(powerupPart.class) != null)
                    {
                        world.removeEntity(e);
                    }
                    
                    if(f.getPart(powerupPart.class) != null)
                    {
                        world.removeEntity(f);
                    }
                    
                    LifePart elp = e.getPart(LifePart.class);
                    LifePart flp = f.getPart(LifePart.class);
                    
                    elp.setIsHit(true);
                    flp.setIsHit(true); 
                }
                
                //********** SECTION - END : ASTEROID COLLISION DETECTION
                
                LifePart lpe = e.getPart(LifePart.class);
                
                if(lpe != null)
                {
                    if (lpe.getLife() < 1) {
                        world.removeEntity(e);
                    }
                }

                LifePart lpf = f.getPart(LifePart.class);
                
                if(lpf != null)
                {
                    if (lpf.getLife() < 1) {
                        world.removeEntity(f);
                    }
                }
            }
        }
    }

    private boolean Collision(Entity e, Entity f, boolean isCircleCollision) {
        
        PositionPart ep = e.getPart(PositionPart.class);
        SizePart eS = e.getPart(SizePart.class );
        PositionPart fp = f.getPart(PositionPart.class);
        SizePart fS = f.getPart(SizePart.class);
        
        double fWidth = 40;
        double fHeight = 40;

        if(fS != null)
        {
            fWidth = fS.getWidth();
            fHeight = fS.getHeight();
        }
        
        double eWidth = 40;
        double eHeight = 40;
        
        if(eS != null)
        {
            eWidth = eS.getWidth();
            eHeight = eS.getHeight();
        }
        
        if(isCircleCollision)
        {
            
            double eRadius = 30;
            double fRadius = 30;
        
            if(e.getPart(SizePart.class) != null)
            {
                eRadius = eS.getWidth() / 2;
                eRadius -= 10;
            }

            if(f.getPart(SizePart.class) != null)
            {
                fRadius = fS.getWidth() / 2;
                fRadius -= 10;
            }

            double eX = ep.getX() + eRadius / 2;
            double eY = ep.getY() + eRadius / 2;

            double fX = fp.getX() + fRadius / 2;
            double fY = fp.getY() + fRadius / 2;


            double x = Math.abs(eX - (fX));
            double y = Math.abs(eY - (fY));
        
            //Get distance between e and f in a straightline
            double distance = Math.sqrt((x*x)+(y*y));

            if(eRadius + fRadius > distance)
            {
                return true;
            }

            return false;
        }
        else
        {
            //Foreach of rectangle f points, check if they are inside rectangle e's x and y range
            double xStart = ep.getX() + (eWidth / 5);
            double xEnd = ep.getX() + eWidth - (eWidth / 5);
            
            double yStart = ep.getY() + (eHeight / 5);
            double yEnd = ep.getY() + eHeight - (eHeight / 5);
            
            BoxCollision box = new BoxCollision(new Coordinates(fp.getX(), fp.getY()), new Coordinates(fp.getX() + fWidth, fp.getY()), new Coordinates(fp.getX(), fp.getY() + fHeight), new Coordinates(fp.getX() + fWidth, fp.getY() + fHeight));
            
            for(Coordinates coor : box.GetCoordinateList())
            {
                if(coor.getX() >= xStart && coor.getX() < xEnd && coor.getY() > yStart && coor.getY() < yEnd)
                {
                    return true;
                }
            }
            
            return false;
        }
    }
    
    private CollidingParties DetermineCollidingParties(Entity e, Entity f)
    {
        boolean isEInstanceOfEnemy = e instanceof Enemy;
        boolean isEInstanceOfAsteroid = e instanceof Asteroid;
        boolean isEInstanceOfProjectile =  e instanceof Projectile;
        boolean isEInstanceOfPlayer = e instanceof Player;
        
        boolean isFInstanceOfEnemy = f instanceof Enemy;
        boolean isFInstanceOfAsteroid  = f instanceof Asteroid;
        boolean isFInstanceOfProjectile = f instanceof Projectile;
        boolean isFInstanceOfPlayer = f instanceof Player;
        
        if(isEInstanceOfEnemy)
        {
            if(isFInstanceOfEnemy)
            {
                return CollidingParties.EnemyAndEnemy;
            }
            if(isFInstanceOfAsteroid)
            {
                return CollidingParties.EnemyAndAsteroid;
            }
            if(isFInstanceOfProjectile)
            {
                return CollidingParties.EnemyAndBullet;
            }
            if(isFInstanceOfPlayer)
            {
                return CollidingParties.EnemyAndPlayer;
            }
        }
        if(isEInstanceOfAsteroid)
        {
           if(isFInstanceOfEnemy)
            {
                return CollidingParties.AsteroidAndEnemy;
            }
            if(isFInstanceOfAsteroid)
            {
                return CollidingParties.AsteroidAndAsteroid;
            }
            if(isFInstanceOfProjectile)
            {
                return CollidingParties.AsteroidAndBullet;
            }
            if(isFInstanceOfPlayer)
            {
                return CollidingParties.AsteroidAndPlayer;
            } 
        }
        if(isEInstanceOfProjectile)
        {
           if(isFInstanceOfEnemy)
            {
                return CollidingParties.BulletAndEnemy;
            }
            if(isFInstanceOfAsteroid)
            {
                return CollidingParties.BulletAndAsteroid;
            }
            if(isFInstanceOfProjectile)
            {
                return CollidingParties.BulletAndBullet;
            }
            if(isFInstanceOfPlayer)
            {
                return CollidingParties.BulletAndPlayer;
            } 
        }
        if(isEInstanceOfPlayer)
        {
           if(isFInstanceOfEnemy)
            {
                return CollidingParties.PlayerAndEnemy;
            }
            if(isFInstanceOfAsteroid)
            {
                return CollidingParties.PlayerAndAsteroid;
            }
            if(isFInstanceOfProjectile)
            {
                return CollidingParties.PlayerAndBullet;
            }
            if(isFInstanceOfPlayer)
            {
                return CollidingParties.PlayerAndPlayer;
            } 
        }
        
        return CollidingParties.Unknown;
    }
}
