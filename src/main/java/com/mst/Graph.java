package com.mst;

import java.util.List;
import java.util.ArrayList;

public class Graph {
    private List<String> vertices; // list of city districts
    private List<Edge> edges;      // list of roads

    // Constructor with vertices and edges
    public Graph(List<String> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    // Constructor with number of vertices
    public Graph(int numVertices) {
        this.vertices = new ArrayList<>(numVertices);
        this.edges = new ArrayList<>();
    }

    // Getters
    public List<String> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    // Get vertex count
    public int getVertexCount() {
        return vertices.size();
    }

    // Get edge count
    public int getEdgeCount() {
        return edges.size();
    }

    // Add a vertex
    public void addVertex(String v) {
        vertices.add(v);
    }

    // Add an edge
    public void addEdge(Edge e) {
        edges.add(e);
    }
}
