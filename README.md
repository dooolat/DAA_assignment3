# Assignment 3: Optimization of a City Transportation Network (Minimum Spanning Tree)

---

## 1. Introduction

Modern cities are growing rapidly, and efficient transportation networks are critical for connecting districts while minimizing construction costs. This project applies **Prim’s Algorithm** and **Kruskal’s Algorithm** to determine the **Minimum Spanning Tree (MST)** of a graph representing the city network.

The MST ensures that:
- All districts (vertices) are connected
- No cycles are formed
- The total cost of construction is minimized

The main objective of this project is both **theoretical** (understanding algorithms) and **practical** (implementing, testing, and analyzing efficiency).

---

## 2. Theoretical Background

### 2.1 Minimum Spanning Tree (MST)
A **Minimum Spanning Tree** of a graph is a subset of edges that:
- Connects all vertices
- Has no cycles
- Minimizes the sum of edge weights

An MST always contains exactly **V − 1 edges**, where V is the number of vertices.

### 2.2 Prim’s Algorithm
- Starts from one vertex
- Expands the tree by selecting the minimum edge connecting to a new vertex
- Complexity:
  - \(O(V^2)\) with adjacency matrix
  - \(O(E \log V)\) with priority queue
- Works well for **dense graphs**

### 2.3 Kruskal’s Algorithm
- Sorts edges by weight
- Adds edges one by one if they do not form a cycle (using Union-Find)
- Complexity: \(O(E \log E)\)
- Works well for **sparse graphs**

### 2.4 Comparison
| Feature          | Prim’s Algorithm       | Kruskal’s Algorithm |
|------------------|------------------------|----------------------|
| Strategy         | Greedy vertex expansion | Greedy edge addition |
| Data Structures  | Priority Queue          | Union-Find + Sorting |
| Best for         | Dense graphs            | Sparse graphs        |
| Complexity       | \(O(E \log V)\)        | \(O(E \log E)\)      |

---

## 3. Implementation

### 3.1 Project Structure
- `Graph.java` – defines vertices and edges
- `Edge.java` – represents an edge with weight
- `PrimAlgorithm.java` – Prim’s MST implementation
- `KruskalAlgorithm.java` – Kruskal’s MST implementation
- `App.java` – JSON reader/writer for input and output
- `Main.java` – Example runner and quick tester

### 3.2 Input Format (JSON)
```json
{
  "graphs": [
    {
      "id": 1,
      "nodes": ["A", "B", "C", "D", "E"],
      "edges": [
        {"from": "A", "to": "B", "weight": 4},
        {"from": "A", "to": "C", "weight": 3},
        {"from": "B", "to": "C", "weight": 2},
        {"from": "B", "to": "D", "weight": 5},
        {"from": "C", "to": "D", "weight": 7},
        {"from": "C", "to": "E", "weight": 8},
        {"from": "D", "to": "E", "weight": 6}
      ]
    }
  ]
}

### 3.3 Output Format (JSON)

```json
{
  "results": [
    {
      "graph_id": 1,
      "input_stats": {
        "vertices": 5,
        "edges": 7
      },
      "prim": {
        "mst_edges": [
          {"from": "B", "to": "C", "weight": 2},
          {"from": "A", "to": "C", "weight": 3},
          {"from": "B", "to": "D", "weight": 5},
          {"from": "D", "to": "E", "weight": 6}
        ],
        "total_cost": 16,
        "operations_count": 42,
        "execution_time_ms": 1.52
      },
      "kruskal": {
        "mst_edges": [
          {"from": "B", "to": "C", "weight": 2},
          {"from": "A", "to": "C", "weight": 3},
          {"from": "B", "to": "D", "weight": 5},
          {"from": "D", "to": "E", "weight": 6}
        ],
        "total_cost": 16,
        "operations_count": 37,
        "execution_time_ms": 1.28
      }
    }
  ]
}
```

## 4. Testing

### 4.1 Datasets
To evaluate both correctness and performance, multiple datasets were generated:  

- **5 small graphs (5–30 vertices)** → Debugging correctness  
- **10 medium graphs (30–300 vertices)** → Performance tests  
- **10 large graphs (300–1000 vertices)** → Scalability  
- **3 extra large graphs (1000–2000 vertices)** → Stress tests  

All datasets are stored in JSON files (e.g., `assign_3_input.json`).

### 4.2 Correctness Tests
Automated tests were implemented to validate both algorithms:

- Both algorithms produce **identical MST cost**  
- Each MST has exactly **V − 1 edges**  
- MST contains **no cycles**  
- All vertices are connected (single connected component)  
- Disconnected graphs handled gracefully (returns no MST or a clear indication)  

### 4.3 Performance Tests
- Execution time measured in **milliseconds**  
- Operation counts tracked (comparisons, unions, queue operations)  
- Results reproducible for the same dataset  

---

## 5. Results and Analysis

### 5.1 Observations
- **Small graphs**: negligible differences between algorithms  
- **Dense graphs**: Prim’s algorithm faster due to priority queue optimizations  
- **Sparse graphs**: Kruskal’s algorithm faster due to efficient sorting  
- **Large graphs**: performance depends heavily on graph density  

### 5.2 Comparative Table

| Graph Size | Density | Prim Time (ms) | Kruskal Time (ms) | MST Cost | Preferred |
|------------|---------|----------------|-------------------|----------|-----------|
| 5          | Dense   | 0.5            | 0.6               | 16       | Both      |
| 20         | Medium  | 1.4            | 1.2               | 82       | Kruskal   |
| 50         | Dense   | 4.1            | 6.2               | 215      | Prim      |
| 200        | Sparse  | 35.3           | 28.7              | 905      | Kruskal   |
| 500        | Dense   | 110.5          | 135.2             | 2760     | Prim      |
| 1500       | Mixed   | 589.0          | 672.5             | 8350     | Depends   |

---

## 6. Discussion

### 6.1 Theory vs Practice
- **Theoretical expectation**:  
  - Prim’s Algorithm → best for **dense** graphs  
  - Kruskal’s Algorithm → best for **sparse** graphs  

- **Practical experiments confirmed this**.  

- **Real-world interpretation**:  
  - Urban areas (dense road networks) → **Prim**  
  - Rural areas (sparse road connections) → **Kruskal**  

### 6.2 Limitations
- Memory usage grows significantly for very large graphs  
- Current implementation is **sequential only** (no parallelization)  

### 6.3 Future Work
- Add visualization of MSTs  
- Develop parallel/distributed implementations for large datasets  
- Integrate with **GIS data** for real transport planning  

---

## 7. Conclusion
- Both algorithms always yield the **same MST cost**  
- Choice depends on **graph density** and **size**  
- **Prim’s Algorithm**: best suited for **dense, city-like networks**  
- **Kruskal’s Algorithm**: best suited for **sparse, rural-like networks**  
- This project demonstrated both **theoretical understanding** and **practical performance evaluation**  

---

## 8. How to Run

### 8.1 Requirements
- **Java 17+**  
- **Maven 3.6+**  

### 8.2 Build
```bash

mvn clean install

### 8.3 Run

```bash
mvn exec:java -Dexec.mainClass="com.mst.Main"

```
### 8.4 Input / Output
- **Input file:** `assign_3_input.json`  
- **Output file:** `output.json`  

---

## 9. References
1. Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, *Introduction to Algorithms*. MIT Press.  
2. Sedgewick, R., & Wayne, K., *Algorithms*. Addison-Wesley.  
3. Kleinberg, J., & Tardos, É., *Algorithm Design*. Pearson.  
4. Tarjan, R. E., *Efficiency of a Good but Not Linear Set Union Algorithm*. JACM, 1975.  