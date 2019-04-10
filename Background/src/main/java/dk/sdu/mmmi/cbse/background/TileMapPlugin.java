/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.background;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author ulriksandberg
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class TileMapPlugin implements IGamePluginService{

    private Tile[] map;
    
    @Override
    public void start(GameData gameData, World world) {
        
        map = new TileMap(gameData.getDisplayWidth(), gameData.getDisplayHeight()).getMap();
        
        for(int i = 0; i < map.length; i++)
        {
            world.addNonEntity(map[i]);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        
        for(int i = 0; i < map.length; i++)
        {
            world.removeNonEntity(map[i]); 
        }
    }
}