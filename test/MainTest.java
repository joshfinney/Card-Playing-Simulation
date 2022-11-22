import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {


    @org.junit.jupiter.api.Test
    void main() throws FileNotFoundException, InterruptedException {
        Main testInstance = new Main();
        Main.log("test started");
        ArrayList<Card> pack = Arrays
                .stream(Main.readAndValidatePack(24, "pack.txt").orElse(new int[24]))
                .mapToObj(Card::new)
                .collect(Collectors.toCollection(ArrayList::new));
        testInstance.gameplay(pack);
    }
}