/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import data.Entity;
import data.GameData;
import data.World;
import entityparts.ControlPart;
import services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author oskar
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class AIPlugin implements IGamePluginService{

    
    
    @Override
    public void start(GameData gameData, World world) {
        for(Entity entity : world.getEntities()){
            if(entity.getPart(ControlPart.class) != null){
                ControlPart controlPart = entity.getPart(ControlPart.class);
                controlPart.setIsEnabled(true);
            }
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        for(Entity entity : world.getEntities()){
            if(entity.getPart(ControlPart.class) != null){
                ControlPart controlPart= entity.getPart(ControlPart.class);
                controlPart.setIsEnabled(false);
            }
        }
    }
    
}
