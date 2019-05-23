/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package background;

import data.GameData;
import data.World;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import services.IGamePluginService;

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