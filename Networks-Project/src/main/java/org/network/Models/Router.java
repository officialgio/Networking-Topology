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

