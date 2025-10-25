package com.mst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Kruskal's Algorithm to find Minimum Spanning Tree (MST)
public class KruskalAlgorithm {

    private Graph graph;

    public KruskalAlgorithm(Graph graph) {
        this.graph = graph;
    }

    // Find MST using Kruskal's algorithm
    public List<Edge> findMST() {
        List<Edge> mst = new ArrayList<>();  // MST edges
        DisjointSet ds = new DisjointSet(graph.getVertexCount());

        // Sort edges by weight
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges, (a, b) -> Double.compare(a.getWeight(), b.getWeight()));

        // Iterate through sorted edges
        for (Edge edge : edges) {
            int fromIndex = graph.getVertices().indexOf(edge.getFrom());
            int toIndex = graph.getVertices().indexOf(edge.getTo());

            // If adding this edge doesn't form a cycle
            if (ds.find(fromIndex) != ds.find(toIndex)) {
                mst.add(edge);             // Add edge to MST
                ds.union(fromIndex, toIndex); // Union the sets
            }
        }
        return mst;
    }

    // Disjoint Set (Union-Find) implementation
    private static class DisjointSet {
        private int[] parent;
        private int[] rank;

        public DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;  // Each node is its own parent initially
                rank[i] = 0;    // Rank starts at 0
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) return; // Already connected

            // Union by rank
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}
