import java.util.HashMap;
import java.lang.Integer;
import java.util.Map;

class Player extends AbstractCardOwner implements Runnable {
    int id;

    Player(int playerId){
        this.id = playerId;
    }

    //todo asynchronous
    //todo Logging

    void grabCards(){
        //Todo check drawable status
        cards.add(Deck.draw(id));
    }

    void chuckCards(){
        Deck.pile(id, preferredDiscard());
    }


    private Card preferredDiscard(){
        //todo weighted preference by Id, substituting truly random draw
        return drawRandomCard();
    }

    void checkVictory(){
        HashMap<Integer, Integer> victoryCounter = new HashMap<>(Map.of());
        for (Card c: cards) {
            victoryCounter.put(c.value, 0);
        }
        for (Card c: cards) {
            int duplicateCount = victoryCounter.get(c.value);
            duplicateCount++;
            victoryCounter.put(c.value,duplicateCount);
            if (duplicateCount==4){
                announceVictory();
                return;
            }
        }
    }

    private void announceVictory(){
        Main.log("Player "+ (id+1) +" has won the game");
        //Todo notify victory
    }

    @Override
    public void run() {
        checkVictory();

    }
}