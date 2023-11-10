package org.network.Models;

import lombok.NoArgsConstructor;
import org.network.Topologies.Graph;
import org.network.Topologies.GraphDemo2;

import java.io.*;
import java.util.*;

/**
 * This object proceeds with reading and populate Routers from a file.
 */
@NoArgsConstructor
public class Initializer {
    private static final String FILE_NAME = "src/config.csv";
    private static File file = new File(FILE_NAME);
    private static List<Router> trackedRouters = new ArrayList<>();

    public static List<Router> currentRouters = new ArrayList<>();


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
    }

    private static void populateRouters() {
        try {
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

                        trackedRouters.add(neighborRouter);

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
                        neighborRouter.setCost(cost);// Add the corresponding neighbor name

                        // We know the cost is the final property so just add it
                        router.addDestination(neighborRouter);
                    }
                }
                currentRouters.add(router);
                // TODO: Add to a graph instead???
                // TODO: Ensure to continue to add the rest of the routers adjacent to one another.
            }
            System.out.println("Repopulating any additional neighbors for destinations...");
            repopulateRouterDestinations();
            System.out.println("Done adding neighbors...\n");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Go over the current routers we've added and add any necessary destinations that were left out.
    // We've kept tracked of all neighbor routers, and we must check that first the current router is not equal
    // with any tracked router otherwise it wouldn't make sense to include it.
    // Assuming the current router (Rout) is not equal a tracked router, iterate over all Rout's destinations
    // and check that if any tracked router isn't there already. If at any point there's a neighbor router that exist
    // in the destinations then break and don't re-add  the same router.
    private static void repopulateRouterDestinations() {
        for (Router rout : currentRouters) {
            List<Router> destinations = rout.getDestinations();
            for (Router neighborRouter : trackedRouters) {
                // Check if the neighborRouter is not equal to the current router
                if (!Objects.equals(rout.getName(), neighborRouter.getName())) {
                    String neighborRouterName = neighborRouter.getName();
                    boolean isPartOfDestinations = false;
                    for (Router destination : destinations) {
                        String destinationName = destination.getName();
                        // Check if the neighborRouter's name is in the destinations
                        if (destinationName.equals(neighborRouterName)) {
                            isPartOfDestinations = true;
                            break;
                        }
                    }
                    // If the neighborRouter is not part of the destinations, add it
                    if (!isPartOfDestinations) {
                        rout.getDestinations().add(neighborRouter);
                    }
                }
            }
        }
    }
}
