package com.mst;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

// Prim's Algorithm to find Minimum Spanning Tree (MST)
public class PrimAlgorithm {

    private Graph graph;

    public PrimAlgorithm(Graph graph) {
        this.graph = graph;
    }

    // Find MST using Prim's algorithm
    public List<Edge> findMST() {
        List<Edge> mst = new ArrayList<>();   // List of edges in MST
        if (graph.getVertices().isEmpty()) return mst;

        HashSet<String> visited = new HashSet<>(); // Tracks visited vertices
        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>( (a, b) -> Double.compare(a.getWeight(), b.getWeight()) );


        // Start from the first vertex
        String start = graph.getVertices().get(0);
        visited.add(start);
        addEdges(start, edgeQueue, visited);

        while (!edgeQueue.isEmpty() && visited.size() < graph.getVertexCount()) {
            Edge edge = edgeQueue.poll();

            // If the destination vertex is not visited yet
            if (!visited.contains(edge.getTo())) {
                mst.add(edge);
                visited.add(edge.getTo());
                addEdges(edge.getTo(), edgeQueue, visited);
            } else if (!visited.contains(edge.getFrom())) {
                // Also check the reverse direction (undirected graph)
                mst.add(edge);
                visited.add(edge.getFrom());
                addEdges(edge.getFrom(), edgeQueue, visited);
            }
        }

        return mst;
    }

    // Add all edges from the vertex to the priority queue if the other vertex is not visited
    private void addEdges(String vertex, PriorityQueue<Edge> queue, HashSet<String> visited) {
        for (Edge edge : graph.getEdges()) {
            if (edge.getFrom().equals(vertex) && !visited.contains(edge.getTo())) {
                queue.add(edge);
            } else if (edge.getTo().equals(vertex) && !visited.contains(edge.getFrom())) {
                queue.add(edge);
            }
        }
    }
}
