package com.mst;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class PrimAlgorithm {

    public static MSTAlgorithms.AlgoResult run(Graph g){
        long start = System.nanoTime();
        MSTAlgorithms.AlgoResult res = new MSTAlgorithms.AlgoResult();
        AtomicLong ops = new AtomicLong(0);
        if (g.nodes == null || g.nodes.isEmpty()) { 
            res.execution_time_ms = 0; 
            return res; 
        }

        Map<String, List<Edge>> adj = new HashMap<>();
        for (String v: g.nodes) adj.put(v, new ArrayList<>());
        for (Edge e: g.edges) {
            adj.get(e.from).add(e);
            adj.get(e.to).add(new Edge(e.to, e.from, e.weight));
        }

        Set<String> inMST = new HashSet<>();
        String startNode = g.nodes.get(0);
        inMST.add(startNode);
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        pq.addAll(adj.get(startNode));
        ops.addAndGet(adj.get(startNode).size());

        while(!pq.isEmpty() && res.mst_edges.size() < g.nodes.size()-1){
            Edge e = pq.poll(); ops.incrementAndGet();
            if(inMST.contains(e.to)) continue;
            res.mst_edges.add(new Edge(e.from, e.to, e.weight));
            res.total_cost += e.weight;
            inMST.add(e.to);
            for(Edge ne: adj.get(e.to)){
                if(!inMST.contains(ne.to)) { pq.offer(ne); ops.incrementAndGet(); }
            }
        }

        res.operations_count = ops.get();
        res.execution_time_ms = (System.nanoTime()-start)/1_000_000.0;
        return res;
    }
}
