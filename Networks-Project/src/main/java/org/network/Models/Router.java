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
@ToString
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

    public void addDestination(Router router) {
        destinations.add(router);
    }

    public void addNeighbor(Router u, int linkCost) {

    }


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

