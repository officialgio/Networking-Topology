import org.junit.Test;
import org.network.Models.Initializer;

import java.io.File;

public class RouterTest {
    Initializer initOne;
    Initializer initTwo;
    Initializer initThree;


    @Test
    public void graphOne() {
        initOne = new Initializer(new File("src/config.csv"));
        initOne.init(5);
        initOne.printRouters();
    }

    @Test
    public void graphTwo() {
        initTwo = new Initializer(new File("src/config-2.csv"));
        initTwo.init(7);
        initTwo.printRouters();
    }

    @Test
    public void graphThree() {
        initThree = new Initializer(new File("src/config-3.csv"));
        initThree.init(22);
        initThree.printRouters();
    }
}
