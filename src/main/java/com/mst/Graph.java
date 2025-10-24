package com.mst;

import java.util.*;

public class Graph {
    private List<String> nodes;
    private List<Edge> edges;

    public Graph(List<String> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
