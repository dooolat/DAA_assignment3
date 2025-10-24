package com.mst;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class App {

    // Data structure to hold one graph from JSON
    static class InputGraph {
        int id;
        List<String> nodes;
        List<EdgeData> edges;
    }

    // Edge format for JSON reading
    static class EdgeData {
        String from;
        String to;
        int weight;
    }

    // Result format for output JSON
    static class Result {
        int graph_id;
        Map<String, Integer> input_stats;
        Map<String, Object> prim;
        Map<String, Object> kruskal;
    }

    public static void main(String[] args) {
        String inputFile = "assign_3_input.json";
        String outputFile = "assign_3_output.json";

        try {
            // Load input JSON
            List<InputGraph> graphs = loadGraphs(inputFile);
            List<Result> results = new ArrayList<>();

            for (InputGraph g : graphs) {
                // Convert JSON graph to internal Graph object
                Graph graph = convertToGraph(g);

                // Run Prim’s algorithm
                long startPrim = System.nanoTime();
                int primCost = MSTAlgorithms.primMST(graph);
                long endPrim = System.nanoTime();
                double primTime = (endPrim - startPrim) / 1_000_000.0;

                // Run Kruskal’s algorithm
                long startKruskal = System.nanoTime();
                int kruskalCost = MSTAlgorithms.kruskalMST(graph);
                long endKruskal = System.nanoTime();
                double kruskalTime = (endKruskal - startKruskal) / 1_000_000.0;

                // Build output result
                Result r = new Result();
                r.graph_id = g.id;
                r.input_stats = Map.of(
                        "vertices", graph.vertices,
                        "edges", graph.edges.size()
                );

                r.prim = Map.of(
                        "total_cost", primCost,
                        "execution_time_ms", primTime,
                        "operations_count", 0 // optional: to be implemented later
                );

                r.kruskal = Map.of(
                        "total_cost", kruskalCost,
                        "execution_time_ms", kruskalTime,
                        "operations_count", 0
                );

                results.add(r);
            }

            // Save all results to JSON
            saveResults(outputFile, results);
            System.out.println("✅ Results saved to " + outputFile);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Load all graphs from a JSON file
    private static List<InputGraph> loadGraphs(String filename) throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filename)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            Type listType = new TypeToken<List<InputGraph>>() {}.getType();
            return gson.fromJson(jsonObject.get("graphs"), listType);
        }
    }

    // Save results to a JSON file
    private static void saveResults(String filename, List<Result> results) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> output = Map.of("results", results);
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(output, writer);
        }
    }

    // Convert input graph to internal Graph format
    private static Graph convertToGraph(InputGraph inputGraph) {
        int n = inputGraph.nodes.size();
        Graph g = new Graph(n);
        Map<String, Integer> indexMap = new HashMap<>();

        // Assign integer index to each node
        for (int i = 0; i < n; i++) {
            indexMap.put(inputGraph.nodes.get(i), i);
        }

        // Add edges
        for (EdgeData e : inputGraph.edges) {
            int src = indexMap.get(e.from);
            int dest = indexMap.get(e.to);
            g.addEdge(src, dest, e.weight);
        }
        return g;
    }
}
