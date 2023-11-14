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
        // 1st: Read from file
        Initializer initializer = new Initializer();
        initializer.init();
        initializer.printRouters();
    }
}
