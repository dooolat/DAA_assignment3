package com.mst;

public class Edge {
    public String from;
    public String to;
    public int weight;

    public Edge() {}

    public Edge(String from, String to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return from + " - " + to + " (" + weight + ")";
    }
}
