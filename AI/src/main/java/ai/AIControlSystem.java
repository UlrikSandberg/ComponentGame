/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import commonprojectile.Projectile;
import data.Entity;
import data.GameData;
import data.World;
import entityparts.ControlPart;
import entityparts.MovingPart;
import entityparts.PositionPart;
import entityparts.ShootingPart;
import entityparts.SizePart;
import services.IEntityProcessingService;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author oskar
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class AIControlSystem implements IEntityProcessingService{
    
    private static int GAME_WIDT = 8000;
    private static int GAME_HEIGHT = 5000;
    int i = 1;
    
    @Override
    public void process(GameData gameData, World world){
        Node finalNode = new Node((int)gameData.getPlayerPositionX(), 
                (int)gameData.getPlayerPositionY());
        
        ArrayList<Node> blocks = new ArrayList<>();
        
        for(Entity entity : world.getEntities()){
            if(entity.getPart(ControlPart.class) != null){
                if(entity.getPart(PositionPart.class) != null){
                    if(!(entity instanceof Projectile)){
                    PositionPart posPart = entity.getPart(PositionPart.class);
                    
                    //System.out.println("X: " + posPart.getX() + ", Y: " + posPart.getY());
                    Node initialNode = new Node((int)posPart.getX(), (int)posPart.getY());
                    
                    AStar aStar = new AStar(GAME_WIDT, GAME_HEIGHT, initialNode, finalNode);
                    aStar.setBlocks(world.getEntities(), gameData);
                    List<Node> path = aStar.findPath();
                    
                    
                    if((Math.abs((initialNode.getX()) - (finalNode.getX() * 40)) < 300) &&
                            (Math.abs((initialNode.getY()) - (finalNode.getY() * 40)) < 300)){
                        shoot(posPart, finalNode, entity, gameData);
                    }
                    
                    if((Math.abs(posPart.getX() - gameData.getPlayerPositionX()) < 100) &&
                            (Math.abs(posPart.getY() - gameData.getPlayerPositionY()) < 100)){
                        finalChase(entity, gameData);
                    }else if(!path.isEmpty()){
                        if(path.size() > 1){
                            
                        
                        move(posPart, path.get(1));
                        }
                    }else{
                        move(posPart, new Node((int)gameData.getPlayerPositionX(), (int)gameData.getPlayerPositionY()));
                    }
                }
                }
            }
        }
    }
    
    public void finalChase(Entity aiControlled, GameData gameData){
        PositionPart aiPos = aiControlled.getPart(PositionPart.class);
        
        float aiX = aiPos.getX();
        float aiY = aiPos.getY();
        
        if(aiX < gameData.getPlayerPositionX()) aiPos.setX(aiX + 3);
        if(aiX > gameData.getPlayerPositionX()) aiPos.setX(aiX - 3);
        if(aiY < gameData.getPlayerPositionY()) aiPos.setY(aiY + 3);
        if(aiY > gameData.getPlayerPositionY()) aiPos.setY(aiY - 3);
    }
    
    public void shoot(PositionPart initialNodePos, Node finalNode, Entity aiControlled, 
            GameData gameData){
        float angle = (float) Math.atan2(Math.abs(finalNode.getY() - initialNodePos.getY()), 
                Math.abs(finalNode.getX() - initialNodePos.getX())); 
        
        
        initialNodePos.setRadians(angle * 10);
        initialNodePos.process(gameData, aiControlled);
        
        ShootingPart shootingPart = aiControlled.getPart(ShootingPart.class);
        
        if(shootingPart != null){
        shootingPart.setIsShooting(true);
        shootingPart.process(gameData, aiControlled);
        }
    }
    
    public void move(PositionPart pos, Node nextStep){
        int px = (int)pos.getX();
        int py = (int)pos.getY();
        
        int gx = nextStep.getX();
        int gy = nextStep.getY();
        
        if(px < gx) pos.setX(pos.getX() + 3);
        if(px > gx) pos.setX(pos.getX() - 3);
        if(py < gy) pos.setY(pos.getY() + 3);
        if(py > gy) pos.setY(pos.getY() - 3);
        
        float y = nextStep.getY() - pos.getY();
        float x = nextStep.getX() - pos.getX();
        float angleInDegrees = (float)Math.atan2(y, x);
        float radians = (float)Math.toRadians(Math.abs(angleInDegrees));
        pos.setRadians(radians);

    }
    
    public void movementOutsideReach(MovingPart movingPart){
                Random rand = new Random();

                float rng = rand.nextFloat();

                if (rng > 0.1f && rng < 0.9f) {
                    movingPart.setUp(true);
                }

                if (rng < 0.2f) {
                    movingPart.setLeft(true);
                }

                if (rng > 0.8f) {
                    movingPart.setRight(true);
                }
    }
    
    
    
    
}
