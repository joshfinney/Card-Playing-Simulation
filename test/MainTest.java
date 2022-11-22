import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {


    @org.junit.jupiter.api.Test
    void gameplay() throws IOException {
        String[] args = {"3","pack.txt"};
        Main.main(args);
    }
}