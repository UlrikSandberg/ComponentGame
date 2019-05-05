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
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonprojectile.Projectile;
import java.io.File;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author magnusm
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class MissilePlugin implements IGamePluginService {
    
    private Entity missile;
    
    //private String imageurl = new File("").getAbsolutePath() + "/Missile/target/missile-1.0-SNAPSHOT.jar!/images/assets/missile.png";
    private String soundurl = new File("").getAbsolutePath() + "/Missile/target/missile-1.0-SNAPSHOT.jar!/images/assets/MissileSound.mp3";

    @Override
    public void start(GameData gameData, World world) {
        //world.addEntity(createMissile(0,0,0,imageurl));
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity m : world.getEntities(Projectile.class)){
            world.removeEntity(m);
        }
    
    }
    
    private Entity createMissile(float x, float y, float radians, String uuid) {
        Entity m = new Projectile();

        m.add(new PositionPart(x, y, radians));
        m.add(new MovingPart(0, 5000, 800, 0));
        m.add(new TimerPart(3));
        m.add(new LifePart(1));
        // Projectile Part only used for better collision detection     
        m.add(new ProjectilePart(uuid.toString()));
        m.setRadius(2);
       // m.setSprite(imageurl);
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

