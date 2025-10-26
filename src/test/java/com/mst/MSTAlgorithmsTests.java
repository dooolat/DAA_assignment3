package com.mst;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.*;
import java.util.*;

public class MSTAlgorithmsTests {

    static Path RES(String name){
        return Paths.get("src","main","resources", name);
    }

    static MSTAlgorithms.OutputEnvelope runOne(String file) throws Exception {
        List<Path> files = List.of(RES(file));
        return MSTAlgorithms.runOnFiles(files);
    }

    @Test
    void costsAreEqual_PrimVsKruskal_onSmall() throws Exception {
        MSTAlgorithms.OutputEnvelope out = runOne("assign_3_input_small.json");
        for (var r : out.results){
            assertEquals(r.prim.total_cost, r.kruskal.total_cost, "MST total cost mismatch for graph " + r.graph_id);
        }
    }

    @Test
    void mstHasVMinus1Edges_andAcyclic_andConnected_onSmall() throws Exception {
        MSTAlgorithms.OutputEnvelope out = runOne("assign_3_input_small.json");
        for (var r : out.results){
            int V = r.input_stats.vertices;
            if (V == 0) continue;
            assertEquals(V-1, r.prim.mst_edges.size(), "Prim edges count != V-1 for graph " + r.graph_id);
            assertEquals(V-1, r.kruskal.mst_edges.size(), "Kruskal edges count != V-1 for graph " + r.graph_id);

            // Connectivity + acyclicity
            assertTrue(TestUtils.isConnected(collectNodes(r), r.prim.mst_edges), "Prim MST not connected for graph " + r.graph_id);
            assertTrue(TestUtils.isConnected(collectNodes(r), r.kruskal.mst_edges), "Kruskal MST not connected for graph " + r.graph_id);
            assertTrue(TestUtils.isAcyclic(collectNodes(r), r.prim.mst_edges), "Prim MST has a cycle for graph " + r.graph_id);
            assertTrue(TestUtils.isAcyclic(collectNodes(r), r.kruskal.mst_edges), "Kruskal MST has a cycle for graph " + r.graph_id);
        }
    }

    @Test
    void nonNegativeTimeAndOps_andReproducible_onSmall() throws Exception {
        MSTAlgorithms.OutputEnvelope out1 = runOne("assign_3_input_small.json");
        MSTAlgorithms.OutputEnvelope out2 = runOne("assign_3_input_small.json");
        assertEquals(out1.results.size(), out2.results.size());

        for (int i=0;i<out1.results.size();i++){
            var r1 = out1.results.get(i);
            var r2 = out2.results.get(i);

            assertTrue(r1.prim.execution_time_ms >= 0, "Prim time negative");
            assertTrue(r1.kruskal.execution_time_ms >= 0, "Kruskal time negative");
            assertTrue(r1.prim.operations_count >= 0, "Prim ops negative");
            assertTrue(r1.kruskal.operations_count >= 0, "Kruskal ops negative");

            // Reproducibility (deterministic algorithms → identical MST cost and edge multiset sizes)
            assertEquals(r1.prim.total_cost, r2.prim.total_cost);
            assertEquals(r1.kruskal.total_cost, r2.kruskal.total_cost);
            assertEquals(r1.prim.mst_edges.size(), r2.prim.mst_edges.size());
            assertEquals(r1.kruskal.mst_edges.size(), r2.kruskal.mst_edges.size());
        }
    }

    // Helper to collect nodes from GraphResult by reconstructing from MST edges (fallback if needed)
    static List<String> collectNodes(MSTAlgorithms.GraphResult r){
        // We'll approximate nodes as unique endpoints present in either MST (works since inputs are connected)
        Set<String> s = new HashSet<>();
        for (var e : r.prim.mst_edges){ s.add(e.from); s.add(e.to); }
        for (var e : r.kruskal.mst_edges){ s.add(e.from); s.add(e.to); }
        return new ArrayList<>(s);
    }
}
