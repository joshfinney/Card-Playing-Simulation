import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {



    @org.junit.jupiter.api.Test
    void gameplay() throws FileNotFoundException {
        ArrayList<Card> pack = Arrays
                .stream(Main.readAndValidatePack(24, "pack.txt").orElse(new int[24]))
                .mapToObj(Card::new)
                .collect(Collectors.toCollection(ArrayList::new));
        Main.gameplay(pack);
    }
}