import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.lang.Integer;

class Player extends AbstractCardOwner implements Runnable {
    int id;
    boolean victory;
    public boolean isVictory() {
        return victory;
    }
    PrintWriter output;

    Player(int playerId) throws FileNotFoundException {
        this.id = playerId;
        String writePath = "src/player" + (id+1) + "_output.txt";
        File oldFile = new File(writePath);
        oldFile.delete();
        File writeFile = new File(writePath);
        output = new PrintWriter(writeFile);

    }

    //todo asynchronous
    //todo Logging

    void grabCards(){
        //Todo check drawable status
        cards.add(Deck.draw(id));
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


    private Card preferredDiscard(){
        //todo weighted preference by Id, substituting truly random draw
        System.out.println(Deck.decks.get(id).cards.toString());
        return drawRandomCard();
    }

    Card drawDeckCard() {
        Card drawnCard = Deck.decks.get(id).cards.get(0);
        Deck.decks.get(id).cards.remove(0);
        return drawnCard;
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

    void logAction(String Message){
        output.println(Message);
        output.close();
    }

    private void announceVictory() {
        Main.log("Player "+ (id+1) +" has won the game");
        notifyAll();
        //Todo notify victory
    }

    @Override
    public void run() {

        logAction("Player " + (id+1) + ": Initial hand - " + readContents());
        synchronized (this) {
            Main.log("");

            Card drawnCard = drawDeckCard();
            Main.log("PLAYER " + (id+1) + ": Draws a " + drawnCard.getValue() + " from Deck " + (id+1));
            cards.add(drawnCard);
            if (drawnCard.getValue() == (id+1)) {
                Card unwantedCard = discardUnwantedAndGetCard();
                Main.log("PLAYER " + (id+1) + ": Discards a " + unwantedCard.getValue() + " to Deck " + (((id+1) % Main.getNumberOfPlayers())+1));
            } else {
                discard(drawnCard);
                Main.log("PLAYER " + (id+1) + ": Discards a " + drawnCard.getValue() + " to Deck " + (((id+1) % Main.getNumberOfPlayers())+1));
            }
            checkVictory();
        }
    }
}