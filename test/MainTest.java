
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
        int players = new Random().nextInt(10)+2;
        generatePack(players);
        String[] args = {String.valueOf(players), "test/pack.txt"};
        Main.main(args);
    }

    static void generatePack(int players) throws IOException {
        ArrayList<Integer> pack = new ArrayList<>();

        while (pack.size() < (players * 8)) {
            for (int i = 1; i <= players; i++) {
                if (pack.size() == (players * 8)) {
                    break;
                } else {
                    pack.add(i);
                }
            }
        }

        Collections.shuffle(pack);
        PrintWriter output = new PrintWriter("test/pack.txt");

        for (Integer integer : pack) {
            output.println(integer);
        }

        output.close();
    }


    @org.junit.jupiter.api.Test
    void readAndValidatePack() throws FileNotFoundException {
        readAndValidatePackWithNegativeNumbers();
        readAndValidatePackWithWrongRowCount();
        readAndValidatePackWithMultiValues();
    }

    // If the pack contains 1 or more negative values, readAndValidatePack should return an empty integer array
    void readAndValidatePackWithNegativeNumbers() throws FileNotFoundException {
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
        System.out.println("Testing for negative card values:");
        assertEquals(Optional.empty(),Main.readAndValidatePack(numberOfPlayers*8,"test/testNegativePack.txt"));
    }

    // If the pack doesn't contain 8n (where n is the number of players) rows, readAndValidatePack should return an empty integer array
    void readAndValidatePackWithWrongRowCount() throws FileNotFoundException {
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
        System.out.println("Testing for wrong card amount:");
        assertEquals(Optional.empty(),Main.readAndValidatePack(numberOfPlayers*8,"test/testWrongRowCountPack.txt"));
    }

    // If the pack has multiple values in a row, readAndValidatePack should return an empty integer array
    void readAndValidatePackWithMultiValues() throws FileNotFoundException {
        Random rand = new Random();
        int numberOfPlayers = rand.nextInt(10)+2;
        ArrayList<Integer> pack = new ArrayList<>();
        while (pack.size() < (numberOfPlayers * 8)) {
            for (int i = 1; i <= numberOfPlayers; i++) {
                if (pack.size() == (numberOfPlayers * 8)) {
                    break;
                } else {
                    pack.add(i);
                }
            }
        }
        Collections.shuffle(pack);
        PrintWriter output = new PrintWriter("test/testMultiValuePack.txt");
        for (Integer integer : pack) {
            output.println(integer);
            if (rand.nextInt(2) == 0) {
                output.println(integer);
            } else {
                output.println(integer + " " + integer);
            }
        }
        output.close();
        System.out.println("Testing for multiple values in one line:");
        assertEquals(Optional.empty(),Main.readAndValidatePack(numberOfPlayers*8,"test/testMultiValuePack.txt"));
    }
}