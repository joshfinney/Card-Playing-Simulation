import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.lang.Integer;

class Player extends AbstractCardOwner implements Runnable {
    int id;
    int displayId;
    int targetDeck;
    boolean victory;
    boolean firstHand;
    public boolean isVictory() {
        return victory;
    }
    PrintWriter output;

    Player(int playerId) throws FileNotFoundException {
        this.id = playerId;
        this.displayId = id+1;
        this.targetDeck = ((displayId % Main.getNumberOfPlayers())+1);
        this.firstHand = true;
        String writePath = "src/player" + (id+1) + "_output.txt";
        File oldFile = new File(writePath);
        oldFile.delete();
        File writeFile = new File(writePath);
        output = new PrintWriter(writeFile);

    }

    void closeWriter(){
        output.close();
    }

    private void discard(Card unwantedCard) {
        int opponentsDeckId = (id + 1) % Main.getNumberOfPlayers();
        cards.remove(unwantedCard);
        Deck.decks.get(opponentsDeckId).cards.add(unwantedCard);
    }

    Card discardUnwantedAndGetCard() {
        ArrayList<Card> currentDeck = Deck.decks.get(id).cards;
        int randomIndex = 0;

        while (true) {
            Random ran = new Random();
            randomIndex = ran.nextInt(cards.size());
            if (cards.get(randomIndex).getValue() != (id+1)) {
                Card discardedCard = cards.get(randomIndex);
                discard(discardedCard);
                return discardedCard;
            }
        }
    }


    Card drawDeckCard() {
        return Deck.draw(id);
    }

    void checkVictory(){
        HashMap<Integer, Integer> victoryCounter = new HashMap<Integer, Integer>();
        for (Card c: cards) {
            victoryCounter.put(c.value, 0);
        }
        for (Card c: cards) {
            int duplicateCount = victoryCounter.get(c.value);
            duplicateCount++;
            victoryCounter.put(c.value,duplicateCount);
            if (duplicateCount==4){
                victory = true;
                announceVictory();
                return;
            }
        }
    }

    void logAction(String message){
        message = "Player " + displayId + ":"+ message;
        output.println(message);
        Main.log(message);
    }

    private void announceVictory() {
        logAction(" has won the game");
        Main.gameEnded = true;
    }

    public void run() {
        if (firstHand) {
            checkVictory();
            firstHand=false;
        }
        String threadName = Thread.currentThread().getName();
        logAction(" Alive on "+threadName);
        logAction(" Initial hand - " + readContents());

        Card drawnCard = drawDeckCard();
        logAction(" Draws a " + drawnCard.getValue() + " from Deck " + displayId);
        cards.add(drawnCard);
        if (drawnCard.getValue() == (id+1)) {
            Card unwantedCard = discardUnwantedAndGetCard();
            logAction(" Discards a " + unwantedCard.getValue() + " to Deck " + targetDeck);
        } else {
            discard(drawnCard);
            logAction(" Discards a " + drawnCard.getValue() + " to Deck " + targetDeck);
        }
        checkVictory();
    }

}