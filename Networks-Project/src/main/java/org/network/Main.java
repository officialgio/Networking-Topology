// --------------------------------------------------------------------
//
// Giovanny Hernandez & Sweta Dev
//
// --------------------------------------------------------------------

package org.network;

import org.network.Models.Initializer;
import org.network.Models.Router;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<Router> routers = new ArrayList<>();

    public static void main(String[] args) {
        // Step 1
        Initializer initializer = new Initializer();
        initializer.init();
        initializer.printRouters();
        routers = Initializer.currentRouters;

        // TODO: Step 2
        for (Router router : routers) {
            router.builder();
        }

        // TODO: Step 3
    }
}
