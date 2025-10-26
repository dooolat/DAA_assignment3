package com.mst;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import com.fasterxml.jackson.databind.*;

public class MSTAlgorithms {

    // Data wrappers for JSON
    public static class InputEnvelope { public List<Graph> graphs; }

    public static class InputStats { public int vertices; public int edges; }

    public static class AlgoResult {
        public List<Edge> mst_edges = new ArrayList<>();
        public long total_cost;
        public long operations_count;
        public double execution_time_ms;
    }

    public static class GraphResult {
        public int graph_id;
        public InputStats input_stats;
        public AlgoResult prim;
        public AlgoResult kruskal;
    }

    public static class OutputEnvelope { public List<GraphResult> results = new ArrayList<>(); }

    // Evaluate single graph
    public static GraphResult evaluateGraph(Graph g){
        GraphResult gr = new GraphResult();
        gr.graph_id = g.id;
        gr.input_stats = new InputStats();
        gr.input_stats.vertices = g.nodes==null?0:g.nodes.size();
        gr.input_stats.edges = g.edges==null?0:g.edges.size();
        gr.prim = PrimAlgorithm.run(g);
        gr.kruskal = KruskalAlgorithm.run(g);
        return gr;
    }

    // Run on JSON input files
    public static OutputEnvelope runOnFiles(List<Path> inputFiles) throws IOException {
        ObjectMapper om = new ObjectMapper();
        OutputEnvelope out = new OutputEnvelope();
        for (Path p: inputFiles){
            InputEnvelope env = om.readValue(Files.readAllBytes(p), InputEnvelope.class);
            for (Graph g: env.graphs){ out.results.add(evaluateGraph(g)); }
        }
        return out;
    }

    // Write JSON + CSV
    public static void writeOutput(OutputEnvelope out, Path jsonOut, Path csvOut) throws IOException{
        ObjectMapper om = new ObjectMapper();
        om.writerWithDefaultPrettyPrinter().writeValue(jsonOut.toFile(), out);
        try (BufferedWriter w = Files.newBufferedWriter(csvOut)){
            w.write("graph_id,vertices,edges,prim_cost,kruskal_cost,prim_ms,kruskal_ms,prim_ops,kruskal_ops\n");
            for (GraphResult r: out.results){
                w.write(String.format(Locale.US,
                        "%d,%d,%d,%d,%d,%.3f,%.3f,%d,%d\n",
                        r.graph_id,
                        r.input_stats.vertices,
                        r.input_stats.edges,
                        r.prim.total_cost,
                        r.kruskal.total_cost,
                        r.prim.execution_time_ms,
                        r.kruskal.execution_time_ms,
                        r.prim.operations_count,
                        r.kruskal.operations_count));
            }
        }
    }
}
