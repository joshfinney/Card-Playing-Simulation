import java.io.IOException;

class MainTest {


    @org.junit.jupiter.api.Test
    void gameplay() throws InterruptedException, IOException {
        String[] args = {"3","pack.txt"};
        Main.main(args);
    }
}