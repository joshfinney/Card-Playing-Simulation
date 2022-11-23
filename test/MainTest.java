import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {


    @org.junit.jupiter.api.Test
    void gameplay() throws InterruptedException, IOException {
        String[] args = {"3","pack.txt"};
        Main.main(args);
    }


}