import java.util.ArrayList;

public class AbstractCardOwner {
    protected ArrayList<Card> cards;
    AbstractCardOwner(){
        cards = new ArrayList<Card>();
    }
    synchronized Card drawTopCard(){
        return cards.remove(0);
    }
    void addCard(Card addedCard){
        cards.add(addedCard);
    }

    String readContents(){
        String output = "";
        for (Card a : cards) {
            output = output.concat(a.getValue()+" ");
        }
        return output;
    }
}
