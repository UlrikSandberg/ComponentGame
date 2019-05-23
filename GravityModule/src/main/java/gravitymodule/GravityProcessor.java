/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gravitymodule;

import data.Entity;
import data.GameData;
import data.World;
import enemy.Enemy;
import entityparts.GravityPart;
import entityparts.MovingPart;
import entityparts.NonCollidable;
import entityparts.PositionPart;
import entityparts.SizePart;
import entityparts.powerupPart;
import services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author ulriksandberg
 */

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class GravityProcessor implements IEntityProcessingService {
    
    @Override
    public void process(GameData gameData, World world) {
        
        for (Entity e : world.getEntities()) {
            
            if(e.getPart(GravityPart.class) != null)
            {
                //Object with gravitational pull - Gravity part and it's location
                GravityPart gPart = e.getPart(GravityPart.class);
                PositionPart pPart = e.getPart(PositionPart.class);
                SizePart sPart = e.getPart(SizePart.class);
                
                double gravityCenterX = pPart.getX() + (sPart.getWidth() / 2);
                double gravityCenterY = pPart.getY() + (sPart.getHeight() / 2);
                
                //Check for all entities, if they are within a certain radius pull them towards and if they hit the blackhole they die!
                for (Entity f : world.getEntities()) {
                    
                    if(f.getPart(GravityPart.class) != null || f.getPart(NonCollidable.class) != null ||
                            f instanceof Enemy)
                    {
                        continue;
                    }
                    
                    PositionPart distance = f.getPart(PositionPart.class);
                    MovingPart movingPart = f.getPart(MovingPart.class);
                    
                    //Calculate the distance between the object with gravitational pull and the other entities
                    double x = Math.abs((distance.getX()) - (gravityCenterX));
                    double y = Math.abs((distance.getY()) - (gravityCenterY));
                    
                    double delta = Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));
                    
                    
                    
                    //If delta is less than gravityPart radius then the object is within the range of gravitational pull
                    if(delta < gPart.getGravitationalPullDistance())
                    {
                        
                        double newX = distance.getX();
                        double newY = distance.getY();
                        
                        if(gPart.isHasEventHorizon())
                        {
                            if(delta < gPart.getEventHorizonRadius())
                            {
                                newX = gravityCenterX;
                                newY = gravityCenterY;
                                distance.setX((float)newX);
                                distance.setY((float)newY);
                                return;
                            }
                        }
                        
                        
                        //This kinda works, but does not feel so smooth
                        //Should vary as a function of how close we are to the respective object
                        double gravitationalStrength = Math.pow(delta, gPart.getGravitationalPotens())*gPart.getGravitationFactor();
                        
                        if(gravitationalStrength > delta)
                        {
                            gravitationalStrength = delta;
                        }
                        
                        //Find the linear function describing the distance between the object and the gravitational center
                        double a = y/x;
                        
                        //Calculate the coordinate with the current linearfunction when gravitationalStrength has been applied
                        double relativeX = (gravitationalStrength)/(a+1);
                        double relativeY = relativeX * a;
                        
                        //Figure in which direction the relativeX and Y should affect the entity
                        if(distance.getX() > gravityCenterX)
                        {
                            newX -= relativeX;
                        }
                        else
                        {
                            newX += relativeX;
                        }
                        
                        if(distance.getY() > gravityCenterY)
                        {
                            newY -= relativeY;
                        }
                        else
                        {
                            newY += relativeY;
                        }
                        
                        
                        distance.setX((float)newX);
                        distance.setY((float)newY);
                    }
                }
            }
        }
    }
}
