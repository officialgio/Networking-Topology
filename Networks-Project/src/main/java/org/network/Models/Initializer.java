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

    public static List<Router> currentRouters = new ArrayList<>();

    private GraphDemo2 graph = new GraphDemo2();

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
            Graph graph = new Graph();
            Map<String, List<Router>> routerMap = new HashMap<>();

            BufferedReader reader = new BufferedReader(new FileReader(file));
            System.out.println("Populating Routers...");

            List<Router> routers = new ArrayList<>();

            String contents = "";

            while ((contents = reader.readLine()) != null) {
                // Ignore 5
                if (Character.isDigit(contents.charAt(0)))
                    continue;

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

                System.out.println("Adding Neighbors");
                // Handle Neighbors
                for (String element : neighborsInfo) {
                    if (element.startsWith("(")) {
                        neighborRouter = new Router();
                        // This is a neighbor name, remove the opening parenthesis
                        neighborName = element.substring(1);
                        neighborRouter.setName(neighborName);

                        // add neighbor if it doesn't exist
                        if (!routerMap.containsKey(router.getName())) {
                            routerMap.put(router.getName(), new ArrayList<>());
                        }
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
                // TODO: Add to a graph instead?
            }
            System.out.println("Done adding neighbors");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
