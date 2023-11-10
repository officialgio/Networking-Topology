package org.network.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Router implements IRouter {

    private String name;
    private List<Router> neighbors;
    private List<Router> destinations;
    private Integer cost;
    private Map<Router, RouterInfo> routingTable;

    public Router(String name) {
        this.name = name;
        neighbors = new ArrayList<>();
        cost = 0;
        destinations = new ArrayList<>();
        routingTable = new HashMap<>();
    }

    @Override
    public void displayTable() {

    }

    @Override
    public void builder() {

    }

    @Override
    public void listener() {

    }

    @Override
    public void changeCost() {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Router: ").append(name).append("\n");
        sb.append("Cost: ").append(cost).append("\n");

        sb.append("Neighbors: [");
        for (Router neighbor : neighbors) {
            sb.append(neighbor.getName()).append(", ");
        }
        if (!neighbors.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove the last ", " if there are neighbors
        }
        sb.append("]\n");
        sb.append("Destinations: [");
        for (Router destination : destinations) {
            sb.append(destination.getName()).append(", ");
        }
        if (!destinations.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove the last ", " if there are destinations
        }
        sb.append("]\n");

        sb.append("Routing Table:\n");
        for (Router destination : routingTable.keySet()) {
            RouterInfo routerInfo = routingTable.get(destination);
            sb.append("  Destination: ").append(destination.getName());
            sb.append(", Cost: ").append(routerInfo.getCost());
            sb.append("\n");
        }
        return sb.toString();
    }

    public void addDestination(Router router) {
        destinations.add(router);
    }

    public void addNeighbor(Router u, int linkCost) {

    }



    // Ignore this class
    @Getter
    private class RouterInfo {
        private Router destination;
        private Router neighbor;
        private int cost;

        public RouterInfo(Router destination, Router neighbor, int cost) {
            this.destination = destination;
            this.neighbor = neighbor;
            this.cost = cost;
        }
    }
}

