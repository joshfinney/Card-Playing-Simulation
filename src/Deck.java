import java.util.ArrayList;
import java.util.Dictionary;

public class Deck extends AbstractCardOwner {
    int id;
    static Dictionary<Integer, Deck> decks;
    static int deckCount=0;
    ArrayList<Card> contents;

    Deck(int deckId){
        id = deckId;
        decks.put(id, this);
        deckCount++;
    }

    void dealCard(Card dealtCard){
        addCard(dealtCard);
    }

    static Card draw(int PlayerId){
        int target = PlayerId-1;//SPECIFICATION: PlayerIds start from 1, while deckIds start from 0
        return decks.get(target).drawRandomCard();
    }

    static void pile(int playerId, Card dealtCard){
        int target = (playerId>deckCount)?playerId:0;
        decks.get(target).dealCard(dealtCard);
    }



}
