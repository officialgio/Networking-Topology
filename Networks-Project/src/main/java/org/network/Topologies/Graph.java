package org.network.Topologies;

/**

          A
       /  \  \
      B   C   D
     / \ /
    E   F

 */
import org.network.Models.Router;

import java.util.*;

public class Graph<T extends Router> {
    private int V;
    private List<Router>[] adj;
    private Map<Router, Integer> routerToIndex;

    public Graph(int V) {
        this.V = V;
        adj = new ArrayList[V];
        routerToIndex = new HashMap<>();
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<Router>();
        }
    }

    public void addRouter(Router router) {
        routerToIndex.put(router, routerToIndex.size());
    }

    public void addEdge(Router v, Router w) {
        int indexV = routerToIndex.get(v);
        int indexW = routerToIndex.get(w);
        adj[indexV].add(w);
        adj[indexW].add(v);
    }


    public void BFS(Router source) {
        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();

        int sourceIndex = routerToIndex.get(source);
        visited[sourceIndex] = true;
        queue.add(sourceIndex);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            System.out.print(getRouterByIndex(currentVertex) + " ");

            for (Router neighbor : adj[currentVertex]) {
                int neighborIndex = routerToIndex.get(neighbor);
                if (!visited[neighborIndex]) {
                    visited[neighborIndex] = true;
                    queue.add(neighborIndex);
                }
            }
        }
        System.out.println();
    }

    private Router getRouterByIndex(int index) {
        for (Map.Entry<Router, Integer> entry : routerToIndex.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void repopulateRouterDestinations(Router router) {
        for (List<Router> routerList : adj) {
           for (Router currentRouter : routerList) {
               // TODO: Add destinations by looping through the current destinations
               currentRouter.addDestination(currentRouter);
           }
           break;
        }
    }


    public static void main(String[] args) {
        Graph graph = new Graph(6);
        Router router1 = new Router("R1");
        Router router2 = new Router("R2");
        Router router3 = new Router("R3");
        Router router4 = new Router("R4");
        Router router5 = new Router("R5");

        // Add as single node to keep track of index
        graph.addRouter(router1);
        graph.addRouter(router2);
        graph.addRouter(router3);
        graph.addRouter(router4);
        graph.addRouter(router5);

        // Connect edges
        graph.addEdge(router1, router2);  // A-B
        graph.addEdge(router2, router3);  // B-C
        graph.addEdge(router3, router4);  // C-D

        // BFS from R1
        System.out.println("BFS starting from R1:");
        graph.BFS(router1);

        graph.repopulateRouterDestinations(router1);
    }
}


