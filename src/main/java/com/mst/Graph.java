package com.mst;
import java.util.*;

public class Graph {
    public int vertices;
    public List<Edge> edges = new ArrayList<>();

    public Graph(int vertices) {
        this.vertices = vertices;
    }

    public void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }
}
