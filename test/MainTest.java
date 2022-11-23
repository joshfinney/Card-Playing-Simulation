
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @org.junit.jupiter.api.BeforeAll
    public static void setupTest() throws IOException {
        Path testRoot = Path.of("test/");
        Files.createDirectories(testRoot);
    }
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Timeout(1000)
    void gameplay() throws InterruptedException, IOException {
        String[] args = {"3", "pack.txt"};
        Main.main(args);
    }

    @org.junit.jupiter.api.Test
    void generatePack() throws FileNotFoundException {
        generatePackWithNegativeNumbers();
        generatePackWithWrongRowCount();
    }

    // If the pack contains 1 or more negative values, readAndValidatePack should return an empty integer array
    void generatePackWithNegativeNumbers() throws FileNotFoundException {
        Random rand = new Random();
        int numberOfPlayers = rand.nextInt(10)+2;
        ArrayList<Integer> pack = new ArrayList<>();
        while (pack.size() < (numberOfPlayers * 8)) {
            for (int i = 1; i <= numberOfPlayers; i++) {
                if (pack.size() == (numberOfPlayers * 8)) {
                    break;
                } else {
                    if (rand.nextInt(2) == 0) {
                        pack.add(i);
                    } else {
                        pack.add(-i);
                    }
                }
            }
        }
        Collections.shuffle(pack);
        PrintWriter output = new PrintWriter("test/testNegativePack.txt");

        for (Integer integer : pack) {
            output.println(integer);
        }

        output.close();
        assertEquals(Optional.empty(),Main.readAndValidatePack(numberOfPlayers*8,"test/testNegativePack.txt"));
    }

    // If the pack doesn't contain 8n (where n is the number of players) rows, readAndValidatePack should return an empty integer array
    void generatePackWithWrongRowCount() throws FileNotFoundException {
        Random rand = new Random();
        int numberOfPlayers = rand.nextInt(10)+2;
        ArrayList<Integer> pack = new ArrayList<>();
        int rowCount = rand.nextInt(10);

        while (pack.size() < rowCount) {
            for (int i = 1; i <= numberOfPlayers; i++) {
                if (pack.size() == rowCount) {
                    break;
                } else {
                    pack.add(i);
                }
            }
        }
        Collections.shuffle(pack);
        PrintWriter output = new PrintWriter("test/testWrongRowCountPack.txt");
        for (Integer integer : pack) {
            output.println(integer);
        }
        output.close();
        assertEquals(Optional.empty(),Main.readAndValidatePack(numberOfPlayers*8,"testWrongRowCountPack.txt"));
    }
}