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

    static void pile(int playerId, Card dealtCard){
        int target = (playerId>deckCount)?playerId:1;
        decks.get(target).dealCard(dealtCard);
    }
}
