/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.blackhole;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author ulriksandberg
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class BlackholeProcessor implements IEntityProcessingService {

    
    @Override
    public void process(GameData gameData, World world) {
        
        //Check for all entities, if they are within a certain radius pull them towards and if they hit the blackhole they die!
        
    }
}
