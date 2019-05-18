/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackhole;

import Collision.BoxCollision;
import Collision.Coordinates;
import data.Entity;
import data.GameData;
import data.World;
import entityparts.GravityPart;
import entityparts.NonCollidable;
import entityparts.PositionPart;
import entityparts.SizePart;
import services.IEntityProcessingService;
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
        for(Entity hole : world.getEntities(Blackhole.class))
        {
            for(Entity e : world.getEntities())
            {
                if(e.getPart(NonCollidable.class) != null)
                {
                    continue;
                }
                
                GravityPart gPart = hole.getPart(GravityPart.class);
                PositionPart holeP = hole.getPart(PositionPart.class);
                SizePart holeS = hole.getPart(SizePart.class );
                PositionPart eP = e.getPart(PositionPart.class);
                SizePart eS = e.getPart(SizePart.class);

                double holeWidth = 40;
                double holeHeight = 40;

                if(holeS != null)
                {
                    holeWidth = holeS.getWidth();
                    holeHeight = holeS.getHeight();
                }

                double eWidth = 40;
                double eHeight = 40;

                if(eS != null)
                {
                    eWidth = eS.getWidth();
                    eHeight = eS.getHeight();
                }
                
                
                //Check for collision.
                //Foreach of rectangle f points, check if they are inside rectangle e's x and y range
                double xStart = holeP.getX() + holeWidth / 3;
                double xEnd = holeP.getX() + (holeWidth / 3) * 2;

                double yStart = holeP.getY() + holeHeight / 3;
                double yEnd = holeP.getY() + (holeHeight / 3) * 2;

                BoxCollision box = new BoxCollision(new Coordinates(eP.getX(), eP.getY()), new Coordinates(eP.getX() + eWidth, eP.getY()), new Coordinates(eP.getX(), eP.getY() + eHeight), new Coordinates(eP.getX() + eWidth, eP.getY() + eHeight));

                for(Coordinates coor : box.GetCoordinateList())
                {
                    if(coor.getX() >= xStart && coor.getX() < xEnd && coor.getY() > yStart && coor.getY() < yEnd)
                    {
                        world.removeEntity(e);
                        //Increase size of blackhole
                        holeS.setWidth(holeS.getWidth()+10);
                        holeS.setHeight(holeS.getHeight()+10);
                        gPart.setGravitationalPullDistance(gPart.getGravitationalPullDistance() + 10);
                        gPart.setGravitationFactor(gPart.getGravitationFactor()+5);
                        gPart.setGravitationalPotens(gPart.getGravitationalPotens() + 0.01);
                        
                    }
                }
            }
        }
    }
}
