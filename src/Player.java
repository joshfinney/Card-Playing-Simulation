import java.io.FileNotFoundException;
import java.util.*;
import java.lang.Integer;
import java.util.concurrent.Callable;

class Player extends AbstractCardOwner implements Callable<Boolean> {
    int next;
    boolean firstTurn;
    static ArrayList<Player> players = new ArrayList<>();

    Player(int playerId) throws FileNotFoundException {
        super(playerId, "out/logs/player" + playerId + "_output.txt");
        this.next = ((id % Main.getNumberOfPlayers())+1);
        players.add(this);
        this.firstTurn = true;
    }

    static void dealCard(Card newCard, int index){
        players.get(index).addCard(newCard);
    }
    private void discard(Card unwantedCard) {
        int opponentsDeckId = next -1;
        cards.remove(unwantedCard);
        Deck.dealCard(unwantedCard, opponentsDeckId);
    }

    Card discardUnwantedAndGetCard() {
        int randomIndex;
        while (true) {
            Random ran = new Random();
            randomIndex = ran.nextInt(cards.size());
            if (cards.get(randomIndex).getValue() != (id)) {
                Card discardedCard = cards.get(randomIndex);
                discard(discardedCard);
                return discardedCard;
            }
        }
    }

    Card drawDeckCard() {
        return Deck.draw(id-1);
    }

    void checkVictory(){
        //this will map any extant value of hand cards to the number of times it is duplicated
        HashMap<Integer, Integer> victoryCounter = new HashMap<>();
        for (Card c: cards) {
            victoryCounter.put(c.getValue(), 0);
        }
        //this counts the amount of duplicates in each ard
        for (Card c: cards) {
            int duplicateCount = victoryCounter.get(c.getValue());
            duplicateCount++;
            victoryCounter.put(c.getValue(),duplicateCount);
            //this announces to the Main class that the player has satisfied victory conditions.
            if (duplicateCount==4){
                tryVictory();
                break;
            }
        }
        //regardless, the player always checks if an opponent has gained victory first.
        getVictor();
    }

    private void tryVictory() {
        Main.endGameCheck(id);
    }

    private void getVictor() {
        int victorId = Main.getVictorId();
        if (victorId==id){
            log("has won the game");
        } else if (victorId!=0) {
            log("has been informed by "+victorId+" of its victory");
        } else return;
        log("has Exited");
        log("final Hand "+readContents());
        closeWriter();
    }

    // this return value does not matter, but it is required for the Callable Interface.
    public Boolean call() {
        if (firstTurn){
            // firstTurnAction();
            firstTurn=false;
        }
        try{
            Card drawnCard = drawDeckCard();
            log("draws a " + drawnCard.getValue() + " from Deck " + id);
            cards.add(drawnCard);
            if (drawnCard.getValue() == (id)) {
                Card unwantedCard = discardUnwantedAndGetCard();
                log("discards a " + unwantedCard.getValue() + " to Deck " + next);
            } else {
                discard(drawnCard);
                log("discards a " + drawnCard.getValue() + " to Deck " + next);
            }
            log("current hand is " + readContents());
        }
        catch (IndexOutOfBoundsException e){
            log("ran out of cards to draw");
        }
        checkVictory();
        return true;
    }

    void log(String message){
        message = "Player " + id + " " + message;
        output.println(message);
        Main.println(message);
    }

}