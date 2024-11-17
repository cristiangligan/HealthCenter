package model;

public class Specialization {

    private int id;
    private String name;
    private int cost;
    public Specialization(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public Specialization(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
