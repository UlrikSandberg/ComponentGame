/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityparts;

import data.Entity;
import data.GameData;

/**
 *
 * @author casperhasnsen
 */
public class powerupPart implements EntityPart {

    public Boolean getIsHit() {
        return isHit;
    }

    public void setIsHit(Boolean isHit) {
        this.isHit = isHit;
    }

    
    public Boolean isHit = false;
    
    private boolean bool = false;
   
    private long elapsedTimeMillis;
    
    @Override
    public void process(GameData gameData, Entity entity) {
        
        
        
        if (isHit) {
            
            if (false) {
            // Get current time
       long start = System.currentTimeMillis();

        elapsedTimeMillis = System.currentTimeMillis()-start;

        bool = true;
        }
          LifePart life =  entity.getPart(LifePart.class);
          MovingPart moving = entity.getPart(MovingPart.class);
          
          moving.setMaxSpeed(800);
          // Get current time
          
          
          float elapsedTimeSec = elapsedTimeMillis/1000F;
            System.out.println(elapsedTimeSec);
          if (elapsedTimeSec > 10) {
              moving.setMaxSpeed(300);
          }
        
        
        }
        isHit = false;
    }
    
}
