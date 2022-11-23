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

    @Test
    // If the pack contains 1 or more negative values, readAndValidatePack should return an empty integer array
    void readAndValidatePackNegativeTest() throws FileNotFoundException {
        Random rand = new Random();
        int numberOfPlayers = rand.nextInt(10)+2;
        Main.generatePackWithNegativeNumbers(numberOfPlayers);
        assertEquals(Optional.empty(),Main.readAndValidatePack(numberOfPlayers*8,"testNegativePack.txt"));
    }

}