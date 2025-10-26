package com.mst;

import java.util.*;

public class TestUtils {
    public static Map<String, List<String>> buildAdj(List<Edge> edges){
        Map<String, List<String>> adj = new HashMap<>();
        for (Edge e: edges){
            adj.computeIfAbsent(e.from, k -> new ArrayList<>()).add(e.to);
            adj.computeIfAbsent(e.to, k -> new ArrayList<>()).add(e.from);
        }
        return adj;
    }

    public static boolean isConnected(List<String> nodes, List<Edge> edges){
        if (nodes == null || nodes.isEmpty()) return true;
        Map<String, List<String>> adj = buildAdj(edges);
        Set<String> vis = new HashSet<>();
        Deque<String> dq = new ArrayDeque<>();
        dq.add(nodes.get(0));
        vis.add(nodes.get(0));
        while(!dq.isEmpty()){
            String u = dq.poll();
            for (String v: adj.getOrDefault(u, Collections.emptyList())){
                if (!vis.contains(v)){ vis.add(v); dq.add(v); }
            }
        }
        return vis.size() == new HashSet<>(nodes).size();
    }

    public static boolean isAcyclic(List<String> nodes, List<Edge> edges){
        Map<String,String> parent = new HashMap<>();
        Map<String,Integer> rank = new HashMap<>();
        for (String v: nodes){ parent.put(v, v); rank.put(v, 0); }
        java.util.function.Function<String,String> find = new java.util.function.Function<String,String>(){
            @Override public String apply(String x){
                String p = parent.get(x);
                if (!p.equals(x)) parent.put(x, this.apply(p));
                return parent.get(x);
            }
        };
        java.util.function.BiFunction<String,String,Boolean> union = (a,b) -> {
            String ra = find.apply(a), rb = find.apply(b);
            if (ra.equals(rb)) return false;
            int rka = rank.get(ra), rkb = rank.get(rb);
            if (rka < rkb) parent.put(ra, rb);
            else if (rka > rkb) parent.put(rb, ra);
            else { parent.put(rb, ra); rank.put(ra, rka+1); }
            return true;
        };
        for (Edge e: edges){
            if (!union.apply(e.from, e.to)) return false;
        }
        return true;
    }
}
