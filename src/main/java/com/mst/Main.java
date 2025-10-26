package com.mst;

import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        if(args.length < 3){
            System.err.println("Usage: java com.mst.Main <output.json> <summary.csv> <input1.json> [input2.json ...]");
            return;
        }
        Path outJson = Paths.get(args[0]);
        Path outCsv  = Paths.get(args[1]);
        List<Path> inputs = new ArrayList<>();
        for(int i=2;i<args.length;i++) inputs.add(Paths.get(args[i]));
        MSTAlgorithms.OutputEnvelope out = MSTAlgorithms.runOnFiles(inputs);
        MSTAlgorithms.writeOutput(out, outJson, outCsv);

        System.out.println("Done. Results written to: " + outJson + " and " + outCsv);
    }
}
