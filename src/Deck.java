import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Deck extends AbstractCardOwner {
    static ArrayList<Deck> decks = new ArrayList<>();

    Deck(int deckId) throws FileNotFoundException {
        super(deckId, "out/logs/deck"+deckId+"_output.txt");
        decks.add(this);
    }

    static void dealCard(Card dealtCard, int targetId){
        synchronized (decks.get(targetId)){
            decks.get(targetId).addCard(dealtCard);
        }
    }
    static Card draw(int targetId){
        synchronized (decks.get(targetId)) {
            return decks.get(targetId).drawRandomCard();
        }
    }

    static void printAll() {
        decks.forEach(((deck) -> {
            deck.log("Contains "+deck.readContents());
            deck.closeWriter();
        }));
    }

    void log(String s){
        output.println("Deck "+id+" "+s);
    }
}
