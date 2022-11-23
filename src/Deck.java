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

    static void dealCard(Card dealtCard, int TargetId){
        decks.get(TargetId).addCard(dealtCard);
    }
    static Card draw(int TargetId){
        return decks.get(TargetId).drawTopCard();
    }
}
