package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CollisionDetectionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.CreatedMetaDataPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ProjectilePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.powerupPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IPostEntityProcessingService.class),})
public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            for (Entity f : world.getEntities()) {
                if (e.getID().equals(f.getID())) {
                    continue;
                }
                   
                if (circleCollision(e, f)) {
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
                    
                    
                    // get all entities with power part (player )
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

                    world.removeEntity(f);
                }

                LifePart lpe = e.getPart(LifePart.class);
                if(lpe != null)
                {
                    if (lpe.isDead()) {
                    world.removeEntity(e);
                }
                }
                

                LifePart lpf = f.getPart(LifePart.class);
                if(lpf != null)
                {
                    if (lpf.isDead()) {
                        world.removeEntity(f);
                    }
                }
            }
        }
    }

    private boolean circleCollision(Entity e, Entity f) {
        PositionPart ep = e.getPart(PositionPart.class);
        PositionPart fp = f.getPart(PositionPart.class);

        if ((ep.getX() - fp.getX()) * (ep.getX() - fp.getX())
                + (ep.getY() - fp.getY()) * (ep.getY() - fp.getY())
                < (e.getRadius() + f.getRadius()) * (e.getRadius() + f.getRadius())) {
            return true;
        }

        return false;
    }

}
