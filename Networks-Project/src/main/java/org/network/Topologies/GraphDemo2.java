package org.network.Topologies;

import org.network.Models.Router;

import java.util.*;

public class GraphDemo2 {
    private Map<Router, List<Router>> adjacencyList = new HashMap<>();

    public void addRouter(Router router) {
        adjacencyList.put(router, new ArrayList<>());
    }

    public void addEdge(Router u, Router v) {
        List<Router> sourceNodeList = this.adjacencyList.getOrDefault(u, new ArrayList<Router>());
        sourceNodeList.add(v);
        this.adjacencyList.put(u, sourceNodeList);

        List<Router> destinationNodeList = this.adjacencyList.getOrDefault(v, new ArrayList<Router>());
        destinationNodeList.add(v);
        this.adjacencyList.put(u, destinationNodeList);
    }

    public void removeRouter(Router router) {
        List<Router> neighbors = adjacencyList.get(router);
        for (Router neighbor : neighbors) {
            adjacencyList.get(neighbor).remove(router);
        }
        adjacencyList.remove(router);
    }

    public boolean hasConnection(Router source, Router destination) {
        Queue<Router> queue = new LinkedList<>();
        queue.add(source);
        Set<Router> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Router node = queue.remove();
            visited.add(node);
            List<Router> neighbors = adjacencyList.get(node);

            for (Router neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    if (neighbor == destination) {
                        return true;
                    }
                    queue.add(neighbor);
                }
            }
        }
        return false;
    }

    public void bfsTraversal(Router startRouter) {
        Set<Router> visited = new HashSet<>();
        Queue<Router> queue = new LinkedList<>();

        queue.add(startRouter);

        while (!queue.isEmpty()) {
            Router currentRouter = queue.poll();

            if (!visited.contains(currentRouter)) {
                System.out.println("Visited: " + currentRouter.getName());
                visited.add(currentRouter);

                List<Router> neighbors = adjacencyList.get(currentRouter);
                for (Router neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        GraphDemo2 graph = new GraphDemo2();

        Router router1 = new Router("R1");
        Router router2 = new Router("R2");
        Router router3 = new Router("R3");
        Router router4 = new Router("R4");

        graph.addRouter(router1);
        graph.addRouter(router2);
        graph.addRouter(router3);
        graph.addRouter(router4);

        graph.addEdge(router1, router2);
        graph.addEdge(router2, router3);
        graph.addEdge(router3, router4);

        System.out.println("Is R1 connected to R4? " + graph.hasConnection(router1, router4));


        // Perform BFS traversal from a starting router
        System.out.println("BFS Traversal from R1:");
        graph.bfsTraversal(router1);
    }
}
