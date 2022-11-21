import java.util.HashMap;
import java.util.Map;

public class Deck extends AbstractCardOwner {
    int id;
    static Map<Integer, Deck> decks = new HashMap<>();
    static int deckCount=0;

    Deck(int deckId){
        id = deckId;
        decks.put(id, this);
        deckCount++;
    }

    void dealCard(Card dealtCard){
        addCard(dealtCard);
    }

    static Card draw(int PlayerId){
        return decks.get(PlayerId).drawRandomCard();
    }
}
