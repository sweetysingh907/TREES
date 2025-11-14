import java.util.*;

// Edge class
class Edge implements Comparable<Edge> {
    int src, dest, weight;

    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    // Sorting edges by weight
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

// Disjoint Set Union (Union-Find)
class DSU {
    int[] parent, rank;

    DSU(int n) {
        parent = new int[n];
        rank = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;  // each node is its own parent
            rank[i] = 0;
        }
    }

    int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]);  // path compression
        return parent[x];
    }

    void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] < rank[rootY])
                parent[rootX] = rootY;
            else if (rank[rootX] > rank[rootY])
                parent[rootY] = rootX;
            else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}

public class KruskalMST {

    public static void kruskalMST(int V, List<Edge> edges) {
        // Sort edges by weight
        Collections.sort(edges);

        DSU dsu = new DSU(V);
        List<Edge> mst = new ArrayList<>();

        for (Edge edge : edges) {
            int rootSrc = dsu.find(edge.src);
            int rootDest = dsu.find(edge.dest);

            // If adding this edge doesn't form a cycle
            if (rootSrc != rootDest) {
                mst.add(edge);
                dsu.union(rootSrc, rootDest);
            }

            if (mst.size() == V - 1) break;
        }

        // Print MST
        System.out.println("Minimum Spanning Tree:");
        for (Edge e : mst) {
            System.out.println(e.src + " -- " + e.dest + " == " + e.weight);
        }
    }

    public static void main(String[] args) {
        int V = 4;

        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 10));
        edges.add(new Edge(0, 2, 6));
        edges.add(new Edge(0, 3, 5));
        edges.add(new Edge(1, 3, 15));
        edges.add(new Edge(2, 3, 4));

        kruskalMST(V, edges);
    }
}
