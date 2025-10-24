# DAA Assignment 3 — Minimum Spanning Tree (Prim & Kruskal)

This project implements **Prim’s** and **Kruskal’s** algorithms to find the **Minimum Spanning Tree (MST)** for a given transportation network.

## Objective
Optimize a city’s road network to minimize construction cost while ensuring all districts remain connected.

## Features
- Reads graph data from JSON (`assign_3_input.json`)
- Implements both Prim’s and Kruskal’s algorithms
- Compares:
    - MST total cost
    - Execution time
    - Operation count
- Outputs results to `assign_3_output.json`

## How to Run
```bash
mvn compile
mvn exec:java -Dexec.mainClass=com.mst.App
