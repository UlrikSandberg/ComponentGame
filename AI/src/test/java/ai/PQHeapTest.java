/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import PriorityQueue.PQHeap;
import ai.Node;
import java.util.ArrayList;
import java.util.Collection;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author oskar
 */
public class PQHeapTest {
    
    Node node_1;
    Node node_2;
    Node node_3;
    Node node_4;
    Node node_5;
    Node node_6;
    ArrayList<Node> nodes;
    
    public PQHeapTest() {
        this.nodes = new ArrayList<>();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.node_1 = mock(Node.class);
        this.node_2 = mock(Node.class);
        this.node_3 = mock(Node.class);
        this.node_4 = mock(Node.class);
        this.node_5 = mock(Node.class);
        this.node_6 = mock(Node.class);
        
        when(node_1.getFinalCost()).thenReturn(100);
        when(node_2.getFinalCost()).thenReturn(90);
        when(node_3.getFinalCost()).thenReturn(80);
        when(node_4.getFinalCost()).thenReturn(70);
        when(node_5.getFinalCost()).thenReturn(60);
        when(node_6.getFinalCost()).thenReturn(50);
        
        this.nodes.add(this.node_1);
        this.nodes.add(this.node_2);
        this.nodes.add(this.node_3);
        this.nodes.add(this.node_4);
        this.nodes.add(this.node_5);
        this.nodes.add(this.node_6);
    }
    
    private PQHeap insertAll(){
        PQHeap pqHeap = new PQHeap(6);
        
        for(Node node : this.nodes) pqHeap.insert(node);
        
        return pqHeap;
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isEmpty method, of class PQHeap.
     */
    @Test
    public void testIsEmpty() {
        PQHeap pqHeap = new PQHeap(10);
        Assert.assertTrue(pqHeap.isEmpty());
        
        pqHeap = this.insertAll();
        
        Assert.assertFalse(pqHeap.isEmpty());
    }

    /**
     * Test of getHeapSize method, of class PQHeap.
     */
    @Test
    public void testGetHeapSize() {
        PQHeap pqHeap = this.insertAll();
        
        Assert.assertEquals(pqHeap.getHeapSize(), 6); // as 6 nodes is in the heap
    }

    /**
     * Test of extractMin method, of class PQHeap.
     */
    @Test
    public void testExtractMin() {
        PQHeap pqHeap = this.insertAll();
        
        Node min = mock(Node.class);
        when(min.getFinalCost()).thenReturn(Integer.MAX_VALUE);
        
        for(Node node : this.nodes){
            if(node.getFinalCost() < min.getFinalCost()) min = node;
        }
        
        Assert.assertEquals(pqHeap.extractMin(), min);
    }

    /**
     * Test of remove method, of class PQHeap.
     */
    @Test
    public void testRemove() {
        PQHeap pqHeap = this.insertAll();
        
        Assert.assertTrue(pqHeap.contains(node_1));
        pqHeap.remove(node_1);
        
        Assert.assertFalse(pqHeap.contains(node_1));
    }

    /**
     * Test of contains method, of class PQHeap.
     */
    @Test
    public void testContains() {
        PQHeap pqHeap = this.insertAll();
        
        Assert.assertTrue(pqHeap.contains(this.nodes.get(0)));
    }

    /**
     * Test of insert method, of class PQHeap.
     */
    @Test
    public void testInsert() {
        Node node = new Node(10, 10);
        
        PQHeap pqHeap = new PQHeap(10);
        
        Assert.assertFalse(pqHeap.contains(node));
        
        pqHeap.insert(node);
        
        Assert.assertTrue(pqHeap.contains(node));
    }    
}
