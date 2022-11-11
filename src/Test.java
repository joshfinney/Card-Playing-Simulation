import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        Main.mainGame(3, Arrays
                .stream(Main.readAndValidatePack("pack.txt", 24).orElseThrow())
                .mapToObj(Card::new)
                .collect(Collectors.toCollection(ArrayList::new)));
    }

}
