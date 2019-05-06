package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ProjectilePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonprojectile.Projectile;
import java.io.File;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class BulletPlugin implements IGamePluginService {

    private Entity bullet;
    
    private String imageUrl = new File("").getAbsolutePath() + "/Bullet/target/Bullet-1.0-SNAPSHOT.jar!/assets/images/laserbeam.png";
    private String soundUrl = new File("").getAbsolutePath() + "/Bullet/target/Bullet-1.0-SNAPSHOT.jar!/assets/images/laserSound.mp3";
    
    @Override
    public void start(GameData gameData, World world) {
            
        world.addEntity(createBullet(0, 0, 0, imageUrl));
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            if (e.getClass() == Projectile.class) {
                world.removeEntity(e);
            }
        }
    }
    
    private Entity createBullet(float x, float y, float radians, String uuid) {
        Entity b = new Projectile();

        b.add(new PositionPart(x, y, radians));
        b.add(new MovingPart(0, 50000, 1000, 0));
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
