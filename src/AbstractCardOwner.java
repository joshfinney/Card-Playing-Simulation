import java.util.ArrayList;

public class AbstractCardOwner {
    protected ArrayList<Card> cards;
    AbstractCardOwner(){
        cards = new ArrayList<Card>();
    }
    synchronized Card drawRandomCard(){
        int randomIndex = (int) Math.floor(Math.random()*cards.size());
        return cards.remove(randomIndex);
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
