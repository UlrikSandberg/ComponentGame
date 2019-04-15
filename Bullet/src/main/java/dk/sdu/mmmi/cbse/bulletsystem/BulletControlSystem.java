/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ProjectilePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.io.File;

import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class BulletControlSystem implements IEntityProcessingService {

    private Entity bullet;
    
    private String imageUrl = new File("").getAbsolutePath() + "/Bullet/target/Bullet-1.0-SNAPSHOT.jar!/assets/images/laserbeam.png";
    private String soundUrl = new File("").getAbsolutePath() + "/Bullet/target/Bullet-1.0-SNAPSHOT.jar!/assets/images/laserSound.mp3";
    

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getPart(ShootingPart.class) != null) {

                ShootingPart shootingPart = entity.getPart(ShootingPart.class);
                //Shoot if isShooting is true, ie. space is pressed.
                if (shootingPart.isShooting()) {
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    //Add entity radius to initial position to avoid immideate collision.
                    bullet = createBullet(positionPart.getX() + entity.getRadius(), positionPart.getY() + entity.getRadius(), positionPart.getRadians(), shootingPart.getID());
                    shootingPart.setIsShooting(false);
                    
                    world.addEntity(bullet);
                }
            }
        }

        for (Entity b : world.getEntities(Bullet.class)) {
            PositionPart ppb = b.getPart(PositionPart.class);
            MovingPart mpb = b.getPart(MovingPart.class);
            TimerPart btp = b.getPart(TimerPart.class);
            mpb.setUp(true);
            btp.reduceExpiration(gameData.getDelta());
            LifePart lpb = b.getPart(LifePart.class);
            //If duration is exceeded, remove the bullet.
            if (btp.getExpiration() < 0) {
                world.removeEntity(bullet);
            }

            ppb.process(gameData, b);
            mpb.process(gameData, b);
            btp.process(gameData, b);
            lpb.process(gameData, b);
        }
    }

    //Could potentially do some shenanigans with differing colours for differing sources.
    private Entity createBullet(float x, float y, float radians, String uuid) {
        Entity b = new Bullet();

        b.add(new PositionPart(x, y, radians));
        b.add(new MovingPart(0, 5000, 800, 0));
        b.add(new TimerPart(3));
        b.add(new LifePart(1));
        // Projectile Part only used for better collision detection     
        b.add(new ProjectilePart(uuid.toString()));
        b.setRadius(2);
        b.setSprite(imageUrl);
        b.setSpawnSound(soundUrl); 
        
        float[] colour = new float[4];
        colour[0] = 0.2f;
        colour[1] = 0.5f;
        colour[2] = 0.7f;
        colour[3] = 1.0f;

        b.setColour(colour);

        return b;
    }

    
}
