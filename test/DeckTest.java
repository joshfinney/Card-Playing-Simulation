import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {
    static int numberOfPlayer;
    static Random rand = new Random();

    @org.junit.jupiter.api.BeforeEach
    // Generates a random number of decks, fills each with a random number
    public void setupTest() throws IOException {
        Deck.decks.clear();

        // Creating a random number of decks
        numberOfPlayer = rand.nextInt(5)+2;
        for (int i=0;i<numberOfPlayer;i++) {
            new Deck(i+1);
        }

        // Fill decks with random integer cards
        for (Deck deck: Deck.decks) {
            for (int i=0; i <4; i++) {
                int tempCardValue = rand.nextInt(4)+1;
                Deck.dealCard(new Card(tempCardValue),deck.id - 1);
            }
        }

    }

    // Checks if the withdrawal of cards from a deck is in the correct sequential order
    @org.junit.jupiter.api.Test
    void testDraw() {
        int chosenDeck = rand.nextInt(numberOfPlayer-1)+1;

        String actualPackContents = Deck.decks.get(chosenDeck).readContents();
        ArrayList<Card> testingPack = new ArrayList<>();

        for (int i=0;i<4;i++) {
            testingPack.add(Deck.decks.get(chosenDeck).drawTopCard());
        }

        String testingPackContents = "";
        for (Card a : testingPack) {
            testingPackContents = testingPackContents.concat(a.getValue() + " ");
        }
        System.out.println("generated content: "+testingPackContents);
        System.out.println("original content: "+actualPackContents);
        assertEquals(actualPackContents, testingPackContents);
    }

    @org.junit.jupiter.api.Test
    void testPrintAll() {

    }
}
