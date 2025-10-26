package com.mst;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class KruskalAlgorithm {

    // Disjoint Set Union helper
    static class DSU {
        private final Map<String,String> parent = new HashMap<>();
        private final Map<String,Integer> rank = new HashMap<>();
        private final AtomicLong ops;
        public DSU(Collection<String> nodes, AtomicLong ops) {
            this.ops = ops;
            for (String v: nodes) { parent.put(v,v); rank.put(v,0); }
        }
        public String find(String x){
            ops.incrementAndGet();
            if(!parent.get(x).equals(x)) parent.put(x, find(parent.get(x)));
            return parent.get(x);
        }
        public boolean union(String a, String b){
            ops.incrementAndGet();
            String ra = find(a), rb = find(b);
            if(ra.equals(rb)) return false;
            int rka = rank.get(ra), rkb = rank.get(rb);
            if(rka < rkb) parent.put(ra, rb);
            else if(rka > rkb) parent.put(rb, ra);
            else { parent.put(rb, ra); rank.put(ra, rka+1); }
            return true;
        }
    }

    public static MSTAlgorithms.AlgoResult run(Graph g){
        long start = System.nanoTime();
        MSTAlgorithms.AlgoResult res = new MSTAlgorithms.AlgoResult();
        AtomicLong ops = new AtomicLong(0);
        if (g.nodes==null || g.nodes.isEmpty()) { res.execution_time_ms = 0; return res; }

        List<Edge> edges = new ArrayList<>(g.edges);
        edges.sort(Comparator.comparingInt(e -> e.weight));
        ops.addAndGet(edges.size());

        DSU dsu = new DSU(g.nodes, ops);
        for(Edge e: edges){
            if(dsu.union(e.from, e.to)){
                res.mst_edges.add(new Edge(e.from, e.to, e.weight));
                res.total_cost += e.weight;
                if(res.mst_edges.size() == g.nodes.size()-1) break;
            }
        }
        res.operations_count = ops.get();
        res.execution_time_ms = (System.nanoTime()-start)/1_000_000.0;
        return res;
    }
}
