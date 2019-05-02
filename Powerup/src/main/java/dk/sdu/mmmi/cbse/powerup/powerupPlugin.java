/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.powerup;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.io.File;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author casperhasnsen
 */

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class powerupPlugin implements IGamePluginService {

    private Entity powerup;
    
        private String imageUrl = new File("").getAbsolutePath() + "/Powerup/target/Powerup-1.0-SNAPSHOT.jar!/assets/images/platibus.png";
    
    @Override
    public void start(GameData gameData, World world) {
        world.addEntity(CreatePowerup(imageUrl));
    }

    @Override
    public void stop(GameData gameData, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    private Entity CreatePowerup (String image) {
        
        powerup = new powerup();
        
        powerup.setSprite(image);
        powerup.add(new PositionPart(100, 100, 3));
        powerup.add(new LifePart(1));
        return powerup;
    }    
}
