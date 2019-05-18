/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import data.Entity;
import data.GameData;
import entityparts.PositionPart;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author oskar
 */
public class Blocks {
    
    private ArrayList<Block> blocks;
    
    private ArrayList<Block> generateBlocksMap(Collection<Entity> entities, GameData gameData){
        blocks = new ArrayList<>();
        
        for(Entity entity : entities){
            if(entity.getPart(PositionPart.class) != null){
                PositionPart posPart = entity.getPart(PositionPart.class);
                if(!(posPart.getX() == gameData.getPlayerPositionX() && posPart.getY() == gameData.getPlayerPositionY())){
                    this.expandBlock(entity);
                }
            }
        }
        
        
        
        return blocks;
    }
    
    private void expandBlock(Entity entity){
        this.fillLowerRow(entity);
        this.fillMiddleRow(entity);
        this.fillUpperRow(entity);
    }
    
    private void fillUpperRow(Entity entity){
        PositionPart posPart = entity.getPart(PositionPart.class);
        for(int i = 1; i < 4; i++){
            
        }
    }
    
    private void fillMiddleRow(Entity entity){
        PositionPart posPart = entity.getPart(PositionPart.class);
    }
    
    private void fillLowerRow(Entity entity){
        PositionPart posPart = entity.getPart(PositionPart.class);
    }
    
}

class Block{
    private int x, y;
    
    public Block(int x, int y){
        this.x = x;
        this.y = y;
    }
}

