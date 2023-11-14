package org.network.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class Router implements IRouter {

    private String name;
    private List<Router> neighbors;
    private List<Router> destinations;
    private Integer cost;
    private Map<Router, Integer> routingTable;

    public Router(String name) {
        this.name = name;
        neighbors = new ArrayList<>();
        cost = 0;
        destinations = new ArrayList<>();
        routingTable = new HashMap<>();
    }

    @Override
    public void displayTable() {
        name = "";
    }


    // TODO: Finish creating string object that'll be sent to the rest of the routers.
    @Override
    public String builder() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);

        // Get the neighbor name and cost
        for (Router router : neighbors) {
            String name1 = router.getName();
            Integer cost1 = router.getCost();
            builder.append(name1);
            builder.append(String.valueOf(cost1));
        }
        return new String(builder);
    }

    // TODO: Add functionality to listen to any packets sent by any router.
    // TODO: Compute the shortest path (i.e update routing table)
    @Override
    public void listener() {

    }

    @Override
    public void changeCost() {

    }

    @Override
    public String toString() {
        return "\n\t\tRouter{" +
                "name='" + name + '\'' +
                ", neighbors=" + neighbors +
                ", destinations=" + destinations +
                ", cost=" + cost +
                ", routingTable=" + routingTable +
                "}" + "";
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

