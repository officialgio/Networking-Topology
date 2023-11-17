package org.network.Models;

import lombok.NoArgsConstructor;
import org.network.Topologies.Graph;

import java.io.*;
import java.util.*;

/**
 * This object proceeds with reading and populate Routers from a file.
 */
@NoArgsConstructor
public class Initializer {
    private static final String FILE_NAME = "src/config.csv";
    private static File file = new File(FILE_NAME);
    private static List<Router> trackedNeighborRouters = new ArrayList<>();
    public static List<Router> currentRouters = new ArrayList<>();
    private static Graph<Router> graph;

    public Initializer(File customFilePath) {
        file = customFilePath;
    }

    public void init() {
        // 1st: Read from file
        File file = new File(FILE_NAME);

        // If it doesn't exist then create it
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println(file.getName() + " created.");
            } catch (IOException e) {
                System.out.println("Error creating file");
                System.out.println(e);
            }
        }
        populateRouters();
        buildRoutingTable();
    }

    private static void populateRouters() {
        try {
            graph = new Graph<Router>(5);
            Map<String, List<Router>> routerMap = new HashMap<>();

            BufferedReader reader = new BufferedReader(new FileReader(file));
            System.out.println("Populating Routers...");

            List<Router> routers = new ArrayList<>();

            String contents = "";

            while ((contents = reader.readLine()) != null) {
                // Ignore 5
                if (Character.isDigit(contents.charAt(0))) continue;

                String[] parts = contents.split(": ");
                String routerName = parts[0];
                String[] neighborsInfo = parts[1].split(", ");

                // Main Router
                Router router = new Router(routerName);
                graph.addRouter(router.getName());



                List<String> neighborNames = new ArrayList<>();
                List<Integer> neighborCosts = new ArrayList<>();
                String neighborName = "";
                int cost = 0;

                // Child Routers
                Router neighborRouter = null;

                System.out.println("Adding Neighbors...");
                // Handle Neighbors
                for (String element : neighborsInfo) {
                    if (element.startsWith("(")) {
                        // This is a neighbor name, remove the opening parenthesis
                        neighborRouter = new Router();
                        neighborName = element.substring(1);
                        neighborRouter.setName(neighborName);

                        graph.addRouter(neighborRouter.getName());
                        trackedNeighborRouters.add(neighborRouter);

                        // add neighbor if it doesn't exist
                        if (!routerMap.containsKey(router.getName())) {
                            routerMap.put(router.getName(), new ArrayList<>());
                        }

//                        if (!routerMap.containsKey(neighborName)) {
//                            routerMap.put(neighborRouter.getName(), new ArrayList<>());
//                        }
                    }

                    if (element.endsWith(")")) {
                        // This is a cost, remove the closing parenthesis and convert to an integer
                        cost = Integer.parseInt(element.substring(0, element.length() - 1));
                        graph.addEdge(router, neighborRouter, cost);
//                        neighborRouter.setCost(cost);// Add the corresponding neighbor name

                        // We know the cost is the final property so just add it
//                        router.addDestination(neighborRouter);
                    }
                }
                currentRouters.add(router);
            }
            runBfsOnCurrentGraph();
            addRouterInfo();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runBfsOnCurrentGraph() {
        System.out.println("BFS starting from R1:");
        for (Router router : currentRouters) {
            graph.BFS(router);
            break;
        }
        System.out.println();
    }

    private static void addRouterInfo() {
        addDestinationsToRouters();
        addNeighborToRouters();
        addCostToRouters();
    }

    private static void addDestinationsToRouters() {
        System.out.println("Repopulating all additional possible destinations for the routers respectively...");
        rePopulateRouterDestinations();
        System.out.println("Done adding destinations...\n");
    }

    private static void addNeighborToRouters() {
        System.out.println("Repopulating all neighbors for each corresponding router respectively...");
        rePopulateRouterNeighbors();
        System.out.println("Done adding neighbors...\n");
    }

    private static void addCostToRouters() {
        System.out.println("Repopulating all costs for each corresponding router respectively...");
        rePopulateNeighborCost();
        System.out.println("Done adding costs...\n");
    }

    // Go over the current routers we've added and add any necessary destinations that were left out.
    // We've kept tracked of all neighbor routers, and we must check that first the current router is not equal
    // with any tracked router otherwise it wouldn't make sense to include it.
    // Assuming the current router (Rout) is not equal a tracked router, iterate over all Rout's destinations
    // and check that if any tracked router isn't there already. If at any point there's a neighbor router that exist
    // in the destinations then break and don't re-add  the same router.
    private static void rePopulateRouterDestinations() {
        for (Router rout : currentRouters) {
            List<String> destinations = rout.getDestinations();
            for (Router neighborRouter : trackedNeighborRouters) {
                if (!Objects.equals(rout.getName(), neighborRouter.getName())) {
                    String neighborRouterName = neighborRouter.getName();
                    boolean isPartOfDestinations = false;
                    for (String destination : destinations) {
                        String destinationName = destination;
                        if (destinationName.equals(neighborRouterName)) {
                            isPartOfDestinations = true;
                            break;
                        }
                    }
                    if (!isPartOfDestinations) {
                        rout.getDestinations().add(neighborRouter.getName());
                    }
                }
            }
        }
    }

    // For each neighbor ensure to add its adjacent neighbors respectively.
    // Obtain the index of the table that contains a router-to-index mapping.
    // Use the index of the router to get its corresponding router and add them as neighbors.
    private static void rePopulateRouterNeighbors() {
        for (Router router : currentRouters) {
            int routerIndex = graph.getRouterIndex(router);
            graph.getNeighborsFromAdjacencyList(router, routerIndex);

        }
    }

    // We will need to mutate the original neighbor list with a new version
    // that contains the same neighbors,but it now has its cost to that neighbor appended.
    private static void rePopulateNeighborCost() {
        for (Router router : currentRouters) {
            int routerIndex = graph.getRouterIndex(router);
            List<String> neighbors = router.getNeighbors();
            List<String> costFromAdjacencyList = graph.getCostFromAdjacencyList(routerIndex, neighbors);
            Collections.copy(router.getNeighbors(), costFromAdjacencyList);
        }
    }

    // When adding a router from the .csv file we're not able to add its cost because it only provides the neighbors data.
    // Therefore, we've must go over R2 or any other neighbor and find the cost for the R1 router and vice versa.
    // DEPRECATED!!! OLD WAY (Ignore this)
    private static void rePopulateCostForRouters() {
        for (Router router: currentRouters) {
            boolean matchFound = false;
            for (Router routerDestination : trackedNeighborRouters) {
                if (router.getName().equals(routerDestination.getName())) {
                    router.setCost(routerDestination.getCost());
                    matchFound = true;
                    break; // No need to continue searching
                }
            }
            if (!matchFound) {
                System.out.println("No matching neighbor found for router: " + router.getName());
            }
        }
    }

    private static void buildRoutingTable() {
        for (Router router : currentRouters) {
            Map<String, Integer> routingTable = new HashMap<>();

            // Add directly connected neighbors to the routing table with their costs
            // TODO: Directly connected will change not be -1 but instead the cost
//            for (Router neighbor : router.getNeighbors()) {
//                int cost = neighbor.getCost();
//                routingTable.put(neighbor, cost);
//            }

            // Add other destinations with initial cost '-1'
            for (String destination : router.getDestinations()) {
                final int INITIAL_COST = -1;
                routingTable.put(destination, INITIAL_COST);
            }

            // Set the routing table for the current router
            router.setRoutingTable(routingTable);
        }
    }

    public static void printRouters() {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLUE = "\u001B[34m";
        currentRouters.forEach(s -> {
            System.out.println("------------------------------------------------------");
            System.out.println(ANSI_BLUE
                    + s.getName()
                    + ANSI_RESET);
            System.out.println("{");
            System.out.println("\tNeighbours: " + s.getNeighbors());

            System.out.print("\tDestinations: ");
            System.out.print("[");
            s.getDestinations().forEach(t -> {
                System.out.print("\n\t\t" + t);
            });
            System.out.println("\n\t]");
            System.out.print("\n\tRouting Table: ");
            System.out.print("[");
            System.out.println("\n\t\tkey\t\tvalue");
            s.getRoutingTable().forEach((router, integer) -> {
                System.out.println("\t\t" + router + "\t\t" + integer);
            });
            System.out.println("\n\t]");
            System.out.println("}");
            System.out.println("------------------------------------------------------");
        });
    }
}
