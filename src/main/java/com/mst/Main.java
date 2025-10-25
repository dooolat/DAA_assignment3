package com.mst;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge("A", "B", 1));
        edges.add(new Edge("B", "C", 2));
        edges.add(new Edge("C", "D", 3));
        edges.add(new Edge("A", "D", 4));

        Graph g = new Graph(nodes, edges);

        PrimAlgorithm prim = new PrimAlgorithm(g);
        KruskalAlgorithm kruskal = new KruskalAlgorithm(g);

        List<Edge> primResult = prim.findMST();
        List<Edge> kruskalResult = kruskal.findMST();

        double primTotal = primResult.stream().mapToDouble(Edge::getWeight).sum();
        double kruskalTotal = kruskalResult.stream().mapToDouble(Edge::getWeight).sum();

        System.out.println("Prim MST:");
        primResult.forEach(e -> System.out.println(e));
        System.out.println("Prim total cost: " + primTotal + "\n");

        System.out.println("Kruskal MST:");
        kruskalResult.forEach(e -> System.out.println(e));
        System.out.println("Kruskal total cost: " + kruskalTotal);
    }
}
