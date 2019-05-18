/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package score;

import data.Entity;
import data.GameData;
import data.World;
import entityparts.LifePart;
import entityparts.ScorePart;
import services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Mark
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class ScoreProcessing implements IEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        
        
        for (Entity e : world.getEntities()){
            if(e.getPart(ScorePart.class) != null){
                ScorePart scorePart = e.getPart(ScorePart.class);
                scorePart.addPoints(1);
                if(scorePart.canGetPoints()){
                    gameData.setPlayerScore(scorePart.getPoints());
                }
            }                    
        }
    }
}
