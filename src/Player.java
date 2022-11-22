import java.io.FileNotFoundException;
import java.util.*;
import java.lang.Integer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Player extends AbstractCardOwner implements Runnable {
    int next;
    boolean victory;
    boolean gameEnded;
    static ArrayList<Player> players = new ArrayList<>();

    Player(int playerId) throws FileNotFoundException {
        super(playerId, "src/player" + (playerId) + "_output.txt");
        this.next = ((id % Main.getNumberOfPlayers())+1);
        players.add(this);
        this.gameEnded = false;
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
        HashMap<Integer, Integer> victoryCounter = new HashMap<>();
        for (Card c: cards) {
            victoryCounter.put(c.value, 0);
        }
        for (Card c: cards) {
            int duplicateCount = victoryCounter.get(c.value);
            duplicateCount++;
            victoryCounter.put(c.value,duplicateCount);
            if (duplicateCount==4){
                victory = true;
                tryVictory();
                break;
            }
        }
        getVictor();
    }

    private void tryVictory() {
        Main.endGame(id);
    }

    private void getVictor() {
        int victorId = Main.victorId;
        if (victorId==id){
            log("has won the game");
        } else if (victorId!=0) {
            log("has been informed by "+victorId+" of its victory");
        } else return;
        gameEnded = true;
    }


    public void run() {
        log("Alive on "+Thread.currentThread().getName());
        log("Initial Hand "+readContents());
        checkVictory();
        while (!gameEnded){
            try{
                Card drawnCard = drawDeckCard();
                log("Draws a " + drawnCard.getValue() + " from Deck " + id);
                cards.add(drawnCard);
                if (drawnCard.getValue() == (id)) {
                    Card unwantedCard = discardUnwantedAndGetCard();
                    log("Discards a " + unwantedCard.getValue() + " to Deck " + next);
                } else {
                    discard(drawnCard);
                    log("Discards a " + drawnCard.getValue() + " to Deck " + next);
                }
            }
            catch (IndexOutOfBoundsException e){
                log("ran out of cards to draw");
            }
            checkVictory();
        }
        log("Has Exited");
        log("Final Hand "+readContents());
        output.close();
    }

    void log(String message){
        message = "Player " + id + " " + message;
        output.println(message);
        Main.log(message);
    }

}