/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collision;

import collisionsystem.CollisionDetector;
import data.Entity;
import entityparts.PositionPart;
import entityparts.SizePart;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 *
 * @author oskar
 */
public class CollisionDetectorTest {
    
    public CollisionDetectorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of process method, of class CollisionDetector.
     */
    @Test
    public void testCircleCollision() {
        Entity entity_1 = this.createEntityWithRadiusAndSize(100, 100, 8, 40);
        Entity entity_2 = this.createEntityWithRadiusAndSize(10, 10, 8, 40);
        
        CollisionDetector collisionDetector = new CollisionDetector();
        
        //Assert false as they should not colide
        Assert.assertFalse(collisionDetector.Collision(entity_1, entity_2, true));
        
        PositionPart entity_1_posPart = entity_1.getPart(PositionPart.class);
        //PositionPart set to entity_2 coordinates + 7 (1 less than the radius of the object)
        entity_1_posPart.setX(17); 
        entity_1_posPart.setY(17);
        
        Assert.assertTrue(collisionDetector.Collision(entity_1, entity_2, true));
    }
    
    @Test
    public void testBoxCollision(){
        Entity entity_1 = this.createEntityWithRadiusAndSize(100, 100, 8, 40);
        Entity entity_2 = this.createEntityWithRadiusAndSize(10, 10, 8, 40);
        
        CollisionDetector collisionDetector = new CollisionDetector();
        
        //Assert false as they should not colide
        Assert.assertFalse(collisionDetector.Collision(entity_1, entity_2, false));
        
        PositionPart entity_1_posPart = entity_1.getPart(PositionPart.class);
        //PositionPart set to entity_2 coordinates + 7 (1 less than the radius of the object)
        entity_1_posPart.setX(19); 
        entity_1_posPart.setY(19);
        
        Assert.assertTrue(collisionDetector.Collision(entity_1, entity_2, false));
    }
    
    private Entity createEntityWithRadiusAndSize(float x, float y, float radius, int size){
        Entity entity = new Entity();
        entity.setRadius(radius);
        entity.add(new PositionPart(x, y, 2)); //Radians does not matter in this context
        entity.add(new SizePart(size, size));
        return entity;
    }
    
}
