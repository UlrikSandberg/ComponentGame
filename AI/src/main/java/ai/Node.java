/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

/**
 *
 * @author oskar
 */
public class Node {
    // gCost = g(n) (cost of path from start node to n
    // heuristic = h(n) (estimation of cost of cheapest path from n to goal
    // finalCost = f(n) (f(n) = g(n) + h(n)
    private int gCost, finalCost, heuristic;
    private int x, y; //x is row, y is column
    private boolean isBlock;
    private Node parent;
    
    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void calculateHeuristic(Node finalNode){
        this.heuristic = Math.abs(finalNode.getX() - this.getX()) +
                Math.abs(finalNode.getY() - this.getY());
    }

    public boolean checkBetterPath(Node currentNode, int cost){
        int gCost = currentNode.getgCost() + cost;
        if(gCost < this.getgCost()){
            this.setNodeData(currentNode, cost);
            return true;
        } return false;
    }

    public void setNodeData(Node currentNode, int cost){
        int gCost = currentNode.getgCost() + cost;
        this.setParent(currentNode);
        this.setgCost(gCost);
        calculateCost();
    }

    private void calculateCost(){
        this.setFinalCost(this.getgCost() + this.getHeuristic());
    }

    public boolean equals(Object object){
        Node toBeCompared = (Node) object;
        if(this.x == toBeCompared.getX() && this.y == toBeCompared.getY()) return true;
        return false;
    }

    public int getgCost() {
        return gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public int getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(int finalCost) {
        this.finalCost = finalCost;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
