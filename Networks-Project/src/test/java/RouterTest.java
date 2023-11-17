import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.network.Models.Initializer;

import java.io.File;

public class RouterTest {
    Initializer initOne;
    Initializer initTwo;
    Initializer initThree;


    @Test
    public void graphOne() {
        initOne = new Initializer(new File("src/config.csv"));
        initOne.init();
        initOne.printRouters();
    }

    @Test
    public void graphTwo() {
        initTwo = new Initializer(new File("src/config-2.csv"));
        initTwo.init();
        initTwo.printRouters();
    }
}
