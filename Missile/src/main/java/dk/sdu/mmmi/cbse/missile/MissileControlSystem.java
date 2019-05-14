/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.missile;

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
import dk.sdu.mmmi.cbse.common.services.IWeaponInterface;
import dk.sdu.mmmi.cbse.commonprojectile.Projectile;
import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author magnusm
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class), })
public class MissileControlSystem implements IEntityProcessingService {
    
    private Entity missile;
    
    private String imageurl = new File("").getAbsolutePath() + "/Missile/target/Missile-1.0-SNAPSHOT.jar!/images/assets/missile.png";
    private String soundurl = new File("").getAbsolutePath() + "/Missile/target/Missile-1.0-SNAPSHOT.jar!/images/assets/MissileSound.mp3";
    private String weaponimage = new File("").getAbsolutePath() + "/Missile/target/Missile-1.0-SNAPSHOT.jar!/images/assets/MissileTurret.jpg";
    
    private boolean isoncooldown = false;
    private LocalDateTime cooldown = LocalDateTime.now(); 

    @Override
    public void process(GameData gameData, World world) {
        
            
         for (Entity entity : world.getEntities()) {
            if (entity.getPart(ShootingPart.class) != null) {
                            
                LocalDateTime now = LocalDateTime.now();
                long millis = ChronoUnit.MILLIS.between(cooldown, now);
                if (millis < 5000 || gameData.getSelectedWeapon() != 2){
                    break;
                }
                ShootingPart shootingPart = entity.getPart(ShootingPart.class);
                //Shoot if isShooting is true, ie. space is pressed.
                if (shootingPart.isShooting()) {
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    //Add entity radius to initial position to avoid immideate collision.
                    missile = createMissile(positionPart.getX() + entity.getRadius(), positionPart.getY() + entity.getRadius(), positionPart.getRadians(), shootingPart.getID());
                    shootingPart.setIsShooting(false);
                    cooldown = LocalDateTime.now();
                    world.addEntity(missile);
                }
            }
        }

        for (Entity m : world.getEntities(Projectile.class)) {
            PositionPart ppb = m.getPart(PositionPart.class);
            MovingPart mpb = m.getPart(MovingPart.class);
            TimerPart btp = m.getPart(TimerPart.class);
            mpb.setUp(true);
            btp.reduceExpiration(gameData.getDelta());
            LifePart lpb = m.getPart(LifePart.class);
            //If duration is exceeded, remove the bullet.
            if (btp.getExpiration() < 0) {
                isoncooldown = false;
                world.removeEntity(m);
            }

            ppb.process(gameData, m);
            mpb.process(gameData, m);
            btp.process(gameData, m);
            lpb.process(gameData, m);
        }
    }
    
    private Entity createMissile(float x, float y, float radians, String uuid) {
        Entity m = new Projectile();

        m.add(new PositionPart(x, y, radians));
        m.add(new MovingPart(0, 1500, 800, 0));
        m.add(new TimerPart(5));
        m.add(new LifePart(1));
        // Projectile Part only used for better collision detection     
        m.add(new ProjectilePart(uuid.toString()));
        m.setRadius(2);
        m.setSprite(imageurl);
        m.setSpawnSound(soundurl); 
        
        float[] colour = new float[4];
        colour[0] = 0.2f;
        colour[1] = 0.5f;
        colour[2] = 0.7f;
        colour[3] = 1.0f;

        m.setColour(colour);

        return m;
    }
}

