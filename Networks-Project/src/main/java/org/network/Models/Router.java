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
    private List<String> neighbors;
    private List<String> destinations;
    private Integer cost;
    private Map<String, Integer> routingTable;

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
    public String builder() { return new String(); }

    @Override
    public void listener() {}

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
        destinations.add(String.valueOf(router));
    }

    public void addNeighbor(String router) {
        neighbors.add(router);
    }
}

