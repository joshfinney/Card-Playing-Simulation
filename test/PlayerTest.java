import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    static Player testPlayer;
    static Deck testDeck;
    @org.junit.jupiter.api.BeforeAll
    public static void setupTest() throws IOException {
        Path logsRoot = Paths.get("out/logs/");
        Files.createDirectories(logsRoot);
        Main.setPlayers(1);
        testPlayer = new Player(1);
        testDeck = new Deck(1);
    }

    @org.junit.jupiter.api.BeforeEach
    public void clearCards(){
        testPlayer.cards=new ArrayList<>();
    }

    @org.junit.jupiter.api.Test
    void discardUnwantedAndGetCard() {
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(2));
        testPlayer.addCard(new Card(3));
        testPlayer.addCard(new Card(4));
        testPlayer.addCard(new Card(4354));
        testPlayer.discardUnwantedAndGetCard();
        testPlayer.discardUnwantedAndGetCard();
        testPlayer.discardUnwantedAndGetCard();
        testPlayer.discardUnwantedAndGetCard();
        assertEquals(testPlayer.cards.get(0).getValue(),1);
    }

    @org.junit.jupiter.api.Test
    void checkVictory() {
        testPlayer.addCard(new Card(4));
        testPlayer.addCard(new Card(4));
        testPlayer.addCard(new Card(4));
        testPlayer.addCard(new Card(4));
        testPlayer.checkVictory();
        Main.resetVictor();
        assertEquals(Main.getVictorId(),1);
        testPlayer.cards.remove(0);
        testPlayer.addCard(new Card(3));
        testPlayer.checkVictory();
        assertEquals(Main.getVictorId(),0);
    }
}