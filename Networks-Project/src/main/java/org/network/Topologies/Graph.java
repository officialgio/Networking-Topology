package org.network.Topologies;


import org.network.Models.Router;

import java.util.*;

/**
 * Custom Graph dedicated for Routing.
 */
public class Graph<T extends  Router> {
    private int V;
    private List<Edge>[] adj;
    private Map<String, Integer> routerToIndex;

    public Graph(int V) {
        this.V = V;
        adj = new ArrayList[V];
        routerToIndex = new HashMap<>();
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<>();
        }
    }

    // Edge class to represent edges with weights
    private static class Edge {
        Router destination;
        int weight;

        Edge(Router destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    public void addRouter(String router) {
        if (routerToIndex.containsKey(router)) {
            return; // Router already exists
        }
        routerToIndex.put(router, routerToIndex.size());
    }

    public void addEdge(Router v, Router w, int weight) {
        int indexV = routerToIndex.get(v.getName());
        int indexW = routerToIndex.get(w.getName());

        // Check for duplicate edges
        for (Edge edge : adj[indexV]) {
            if (Objects.equals(edge.destination.getName(), w.getName())) {
                return; // Edge already exists
            }
        }

        adj[indexV].add(new Edge(w, weight));
        adj[indexW].add(new Edge(v, weight));
    }

    private String getRouterByIndex(int index) {
        for (Map.Entry<String, Integer> entry : routerToIndex.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return null;
    }

    public int getRouterIndex(Router router) {
        return routerToIndex.get(router.getName());
    }

    public void getNeighborsFromAdjacencyList(Router router, int indexV) {
        for (Edge edge : adj[indexV]) {
            router.addNeighbor(edge.destination.getName());
        }
    }

    public List<String> getCostFromAdjacencyList(int indexV,  List<String> neighbors) {
        List<String> modifiedNeighborsWithCost = new ArrayList<>();
        for (Edge edge : adj[indexV]) {
            int weight = edge.weight;
            for (String neighbor : neighbors) {
                if (neighbor.equals(edge.destination.getName())) {
                    neighbor = String.format("%s (%d)", neighbor, weight);
                    modifiedNeighborsWithCost.add(neighbor);
                }
            }
        }
        return modifiedNeighborsWithCost;
    }

    public void BFS(Router source) {
        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();

        int sourceIndex = routerToIndex.get(source.getName());
        visited[sourceIndex] = true;
        queue.add(sourceIndex);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            System.out.print(getRouterByIndex(currentVertex) + " ");

            for (Edge edge : adj[currentVertex]) {
                int neighborIndex = routerToIndex.get(edge.destination.getName());
                if (!visited[neighborIndex]) {
                    visited[neighborIndex] = true;
                    queue.add(neighborIndex);
                }
            }
        }
        System.out.println();
    }
}
