package org.ds;

import java.util.ArrayList;

public class BFSTreeNode<T> {

    private BFSTreeNode<T> parent;
    private ArrayList<BFSTreeNode<T>> children;
    private T data;

    public BFSTreeNode(T data){
        parent = null;
        children = new ArrayList<BFSTreeNode<T>>();
        this.data = data;
    }

    public BFSTreeNode(T data, BFSTreeNode<T> parent){
        this.parent = parent;
        children = new ArrayList<BFSTreeNode<T>>();
        this.data = data;
    }

    public BFSTreeNode<T> getParent(BFSTreeNode<T> node){
        return this.parent;
    }

    public void setParent(BFSTreeNode<T> parent){
        this.parent = parent;
    }

    public int getChildCount(){
        return this.children.size();
    }

    public ArrayList<BFSTreeNode<T>> getChildren(){
        return this.children;
    }

    public void addChild(BFSTreeNode<T> child){
        this.children.add(child);
    }

    public T getData(){
        return this.data;
    }

    public boolean isRoot(){
        return (this.parent == null);
    }
}
