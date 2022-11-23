import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {


    @org.junit.jupiter.api.Test
<<<<<<< Updated upstream
    void main() throws FileNotFoundException, InterruptedException {
        Main testInstance = new Main();
        Main.log("test started");
        ArrayList<Card> pack = Arrays
                .stream(Main.readAndValidatePack(24, "pack.txt").orElse(new int[24]))
                .mapToObj(Card::new)
                .collect(Collectors.toCollection(ArrayList::new));
        testInstance.gameplay(pack);
=======
    void main() throws IOException {
        String[] args = {"3","pack.txt"};
        Main.main(args);
>>>>>>> Stashed changes
    }
}