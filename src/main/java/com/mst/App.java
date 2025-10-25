package com.mst;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class App {

    public static void main(String[] args) {
        String inputFile = "assign_3_input.json";
        String outputFile = "output.json";

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonInput = gson.fromJson(new FileReader(inputFile), JsonObject.class);
            JsonArray graphsArray = jsonInput.getAsJsonArray("graphs");

            JsonArray resultsArray = new JsonArray();

            for (JsonElement graphElement : graphsArray) {
                JsonObject graphObj = graphElement.getAsJsonObject();
                int graphId = graphObj.get("id").getAsInt();

                // Create Graph
                List<String> nodes = new ArrayList<>();
                for (JsonElement node : graphObj.getAsJsonArray("nodes")) {
                    nodes.add(node.getAsString());
                }

                List<Edge> edges = new ArrayList<>();
                for (JsonElement edgeEl : graphObj.getAsJsonArray("edges")) {
                    JsonObject e = edgeEl.getAsJsonObject();
                    edges.add(new Edge(e.get("from").getAsString(), e.get("to").getAsString(), e.get("weight").getAsInt()));
                }

                // Correct constructor
                Graph graph = new Graph(nodes, edges);

                // Run Prim
                long startPrim = System.nanoTime();
                PrimAlgorithm primAlg = new PrimAlgorithm(graph);
                List<Edge> primMST = primAlg.findMST();
                long endPrim = System.nanoTime();

                double primCost = primMST.stream().mapToDouble(Edge::getWeight).sum();

                // Run Kruskal
                long startKruskal = System.nanoTime();
                KruskalAlgorithm kruskalAlg = new KruskalAlgorithm(graph);
                List<Edge> kruskalMST = kruskalAlg.findMST();
                long endKruskal = System.nanoTime();

                double kruskalCost = kruskalMST.stream().mapToDouble(Edge::getWeight).sum();

                // Prepare JSON output for this graph
                JsonObject resultObj = new JsonObject();
                resultObj.addProperty("graph_id", graphId);

                JsonObject inputStats = new JsonObject();
                inputStats.addProperty("vertices", graph.getVertexCount());
                inputStats.addProperty("edges", graph.getEdgeCount());
                resultObj.add("input_stats", inputStats);

                // Prim results
                JsonObject primJson = new JsonObject();
                primJson.add("mst_edges", edgesToJsonArray(primMST));
                primJson.addProperty("total_cost", primCost);
                primJson.addProperty("operations_count", primMST.size());
                primJson.addProperty("execution_time_ms", (endPrim - startPrim) / 1_000_000.0);
                resultObj.add("prim", primJson);

                // Kruskal results
                JsonObject kruskalJson = new JsonObject();
                kruskalJson.add("mst_edges", edgesToJsonArray(kruskalMST));
                kruskalJson.addProperty("total_cost", kruskalCost);
                kruskalJson.addProperty("operations_count", kruskalMST.size());
                kruskalJson.addProperty("execution_time_ms", (endKruskal - startKruskal) / 1_000_000.0);
                resultObj.add("kruskal", kruskalJson);

                resultsArray.add(resultObj);
            }

            JsonObject outputJson = new JsonObject();
            outputJson.add("results", resultsArray);

            try (FileWriter writer = new FileWriter(outputFile)) {
                gson.toJson(outputJson, writer);
            }

            System.out.println("MST computation completed. Results saved to " + outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JsonArray edgesToJsonArray(List<Edge> edges) {
        JsonArray arr = new JsonArray();
        for (Edge e : edges) {
            JsonObject obj = new JsonObject();
            obj.addProperty("from", e.getFrom());
            obj.addProperty("to", e.getTo());
            obj.addProperty("weight", e.getWeight());
            arr.add(obj);
        }
        return arr;
    }
}
