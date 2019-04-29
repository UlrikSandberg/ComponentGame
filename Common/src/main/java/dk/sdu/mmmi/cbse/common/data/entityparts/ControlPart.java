/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author oskar
 */
public class ControlPart implements EntityPart{

    private boolean isEnabled = false;
    private boolean isShooting = false;
    private int range;
    private final static int DEFAULT_RANGE = 200;
    
    public ControlPart(){this(false);}
    
    public ControlPart(boolean isShooting){this(isShooting, DEFAULT_RANGE); }
    
    public ControlPart(boolean isShooting, int range){
        this.isShooting = isShooting;
        this.range = range;
    }
    
    public void setIsEnabled(boolean isEnabled){this.isEnabled = isEnabled;}
    public boolean getIsEnabled(){return this.isEnabled;}
    
    public void setIsShooting(boolean isShooting){this.isShooting = isShooting;}
    public boolean getIsShooting(){return this.isShooting;}
    
    
    @Override
    public void process(GameData gameData, Entity entity) {}
    
}
