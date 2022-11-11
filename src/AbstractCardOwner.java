import java.util.ArrayList;

public class AbstractCardOwner {
    protected ArrayList<Card> cards;
    AbstractCardOwner(){
        cards = new ArrayList<>();
    }
    Card drawRandomCard(){
        int randomIndex = (int) Math.floor(Math.random()*cards.size());
        return cards.remove(randomIndex);
    }
    void addCard(Card addedCard){
        cards.add(addedCard);
    }
}
