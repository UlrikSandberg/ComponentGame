/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.blackhole;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.GravityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.NonCollidable;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SizePart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.io.File;
import java.util.HashSet;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author ulriksandberg
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class Blackholeplugin implements IGamePluginService {

    private String BlackHoleImage =  new File("").getAbsolutePath() + "/Blackhole/target/Blackhole-1.0-SNAPSHOT.jar!/assets/images/blackhole.png";
    private Blackhole[] arr = new Blackhole[3];
    
    @Override
    public void start(GameData gameData, World world) {
        
        //Spin up 4 black holes
        Blackhole hole1 = new Blackhole();
        Blackhole hole2 = new Blackhole();
        Blackhole hole3 = new Blackhole();
        
        hole1.add(new SizePart(400, 400));
        hole1.add(new PositionPart(gameData.getDisplayHeight() / 5, gameData.getDisplayWidth() / 5, 0));
        hole1.setSprite(BlackHoleImage);
        hole1.add(new NonCollidable()); 
        hole1.add(new GravityPart()); 
        
        hole2.add(new SizePart(400, 400));
        hole2.add(new PositionPart(gameData.getDisplayHeight() - 100, gameData.getDisplayWidth() / 2, 0));
        hole2.setSprite(BlackHoleImage);
        hole2.add(new NonCollidable()); 
        hole2.add(new GravityPart()); 
        
        
        hole3.add(new SizePart(400, 400));
        hole3.add(new PositionPart(gameData.getDisplayHeight() / 2, gameData.getDisplayWidth() / 2, 0));
        hole3.setSprite(BlackHoleImage);
        hole3.add(new NonCollidable()); 
        hole3.add(new GravityPart()); 
        
        world.addEntity(hole1);
        world.addEntity(hole2);
        world.addEntity(hole3);
        
        arr[0] = hole1;
        arr[1] = hole2;
        arr[2] = hole3;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for(int i = 0; i < arr.length; i++)
        {
           world.removeEntity(arr[i]);
        }
    }
}
