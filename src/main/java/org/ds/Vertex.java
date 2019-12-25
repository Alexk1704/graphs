package org.ds;


public class Vertex {
    private int id;
    private int flag;
    private int distance;

    public Vertex(Vertex copy){
        this(copy.id);
    }

    public Vertex(final int id){
        this.id = id;
        this.flag = 0;
        this.distance = 0; // well, should be infinity...
    }

    public void setId(final int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public int getFlag(){
        return this.flag;
    }

    public void setFlag(int flag){
        this.flag = flag;
    }

    public int getDistance(){
        return this.distance;
    }

    public void setDistance(int distance){
        this.distance = distance;
    }
}
