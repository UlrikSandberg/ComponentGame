package ai;

import ai.Node;
import java.util.*;

public class AStar {
    private static int DEFAULT_HV_COST = 10;
    private static int DEFAULT_DIAGONAL_COST = 14;
    private static int DEFAULT_ENTITY_SIZE = 40;
    private int entitySize;
    private int hvCost, diagonalCost;
    private Node[][] searchArea;
    private PriorityQueue<Node> openList;
    private Set<Node> closedSet;
    private Node initialNode, finalNode;

    public AStar(int x, int y, Node initialNode, Node finalNode, int hvCost, int diagonalCost, int entitySize){
        this.hvCost = hvCost;
        this.entitySize = entitySize;
        this.diagonalCost = diagonalCost;
        this.setInitialNode(initialNode);
        this.setFinalNode(finalNode);
        this.searchArea = new Node[(x - 40)/this.entitySize][(y - 40)/this.entitySize];
        this.openList = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node0, Node node1) {
                return Integer.compare(node0.getFinalCost(), node1.getFinalCost());
            }
        });
        this.setNodes();
        this.closedSet = new HashSet<>();
    }

    public AStar(int x, int y, Node initialNode, Node finalNode){
        this(x, y, initialNode, finalNode, DEFAULT_HV_COST, DEFAULT_DIAGONAL_COST, DEFAULT_ENTITY_SIZE);
    }

    private void setNodes(){
        for(int i = 0; i < searchArea.length; i++){
            for(int j = 0; j < searchArea[0].length; j++){
                Node node = new Node(i, j);
                node.calculateHeuristic(this.getFinalNode());
                this.searchArea[i][j] = node;
            }
        }
    }

    public void setBlocks(Collection<Node> blocks){

        for(Node block : blocks){
            for(int i = 0; i < searchArea.length; i++){
                for(int j = 0; j < searchArea[i].length; j++){
                    if(searchArea[i][j].getY() == block.getY() / this.entitySize &&
                            searchArea[i][j].getX() == block.getX() / this.entitySize){
                        searchArea[i][j].setBlock(true);
                    }
                }
            }
        }
    }


    public List<Node> findPath(){
        this.openList.add(this.initialNode);
        while(!isEmpty(this.openList)){
            Node currentNode = openList.poll();
            this.closedSet.add(currentNode);
            if(isFinalNode(currentNode)){
                return this.getPath(currentNode);
            } else{
                this.addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<Node>();
    }

    private List<Node> getPath(Node currentNode){
        List<Node> path = new ArrayList<>();
        path.add(currentNode);
        Node parent;
        while((parent = currentNode.getParent()) != null){
            path.add(0, parent);
            currentNode = parent;
        }

        for(Node node : path){
            node.setY(node.getY() * this.entitySize);
            node.setX(node.getX() * this.entitySize);
        }
        return path;
    }

    private void addAdjacentNodes(Node currentNode){
        addAdjacentUpperRow(currentNode);
        addAdjacentMiddleRow(currentNode);
        addAdjacentLowerRow(currentNode);
    }

    private void addAdjacentLowerRow(Node currentNode){
        int row = currentNode.getX();
        int col = currentNode.getY();
        int lowerRow = row+ 1;
        if (lowerRow < getSearchArea().length){
            if (col - 1 >= 0) checkNode(currentNode, col - 1, lowerRow, this.getDiagonalCost());
            if (col + 1 < this.getSearchArea()[0].length) checkNode(currentNode, col + 1, lowerRow, getDiagonalCost());
            checkNode(currentNode, col, lowerRow, getHvCost());
        }
    }

    private void addAdjacentMiddleRow(Node currentNode) {
        int row = currentNode.getX();
        int col = currentNode.getY();
        int middleRow = row;
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, middleRow, getHvCost());
        }
        if (col + 1 < getSearchArea()[0].length) {
            checkNode(currentNode, col + 1, middleRow, getHvCost());
        }
    }

    private void addAdjacentUpperRow(Node currentNode) {
        int row = currentNode.getX();
        int col = currentNode.getY();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
            }
            if (col + 1 < getSearchArea()[0].length) {
                checkNode(currentNode, col + 1, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
            }
            checkNode(currentNode, col, upperRow, getHvCost());
        }
    }

    private void checkNode(Node currentNode, int col, int row, int cost){
        try{
            Node adjacentNode = getSearchArea()[row][col];
            if (!adjacentNode.isBlock() && !getClosedSet().contains(adjacentNode)) {
                if (!getOpenList().contains(adjacentNode)) {
                    adjacentNode.setNodeData(currentNode, cost);
                    getOpenList().add(adjacentNode);
                } else {
                    boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                    if (changed) {
                        // Remove and Add the changed node, so that the PriorityQueue can sort again its
                        // contents with the modified "finalCost" value of the modified node
                        getOpenList().remove(adjacentNode);
                        getOpenList().add(adjacentNode);
                    }
                }
            }
        }catch(ArrayIndexOutOfBoundsException ex){
            //System.out.println(ex);
        }catch(IndexOutOfBoundsException ex){
            //System.out.println(ex);
        }
    }

    private void setBlock(int x, int y){this.searchArea[x][y].setBlock(true);}

    private boolean isFinalNode(Node currentNode){return currentNode.equals(finalNode); }

    private boolean isEmpty(PriorityQueue<Node> openList) {return openList.size() == 0;}

    public Node[][] getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(Node[][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public void setOpenList(PriorityQueue<Node> openList) {
        this.openList = openList;
    }

    public Set<Node> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(Set<Node> closedSet) {
        this.closedSet = closedSet;
    }

    public Node getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(Node initialNode) {
        initialNode.setX(initialNode.getX() / this.entitySize);
        initialNode.setY(initialNode.getY() / this.entitySize);
        this.initialNode = initialNode;
    }

    public Node getFinalNode() {
        return finalNode;
    }

    public void setFinalNode(Node finalNode) {
        finalNode.setX(finalNode.getX() / this.entitySize);
        finalNode.setY(finalNode.getY() / this.entitySize);
        this.finalNode = finalNode;
    }

    public int getHvCost() {
        return hvCost;
    }

    public void setHvCost(int hvCost) {
        this.hvCost = hvCost;
    }

    public int getDiagonalCost() {
        return diagonalCost;
    }

    public void setDiagonalCost(int diagonalCost) {
        this.diagonalCost = diagonalCost;
    }
}