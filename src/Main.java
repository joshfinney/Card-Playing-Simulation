import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static Scanner Input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int numberOfPlayer;
        boolean validPack = false;
        ArrayList<Card> pack = new ArrayList<>();

        System.out.println("Enter number of players:");
        numberOfPlayer = Input.nextInt();
        Input.nextLine();

        generatePack(numberOfPlayer);

        while (!validPack) {
            var tempPack = getPack(numberOfPlayer * 8);
            if (tempPack.isPresent()) {
                validPack = true;
                pack = Arrays
                        .stream(tempPack.orElse(new int[numberOfPlayer * 8]))
                        .mapToObj(Card::new)
                        .collect(Collectors.toCollection(ArrayList::new));
            }
        }
        mainGame(numberOfPlayer, pack);
    }
    static void mainGame(int numberOfPlayer, ArrayList<Card> pack){
        ArrayList<Player> players = new ArrayList<Player>();

        // Initialise players and their decks
            for (int i=0;i<numberOfPlayer;i++){
            players.add(new Player(i));
            Deck.decks.put(i,new Deck(i));
        }

        for (int i=0; i<4; i++){
            for (int j=0; j<numberOfPlayer;j++) {

                //Fill player's hand
                players.get(j).addCard(pack.remove(0));
                //Fill each player's deck
                Deck.decks.get(j).dealCard(pack.remove(0));
            }
        }
        for (Player player : players) {
            System.out.println("PLAYER " + (player.id+1) + ": Hand - " + player.readContents() + ", Deck - " + Deck.decks.get(player.id).readContents());
        }

        for (Player player : players) {
            player.run();
        }
    }

    static Optional<int[]> getPack(int entries) throws FileNotFoundException{
        System.out.println("Enter pack location:");
        String location = Input.nextLine();
        return readAndValidatePack(location, entries);
    }
    static Optional<int[]> readAndValidatePack(String location, int entries) throws FileNotFoundException{
        int[] pack = new int[entries];
        int lines = 0;
        File file = new File("src/" + location);
        Scanner input = new Scanner(file);

        while (input.hasNextLine()) {
            input.nextLine();
            lines++;
        }

        input.close();

        if (lines != entries) {
            // Not enough cards in file
            System.out.println("Wrong number of cards, the pack requires " + entries + " cards to start.");
            return Optional.empty();
        }

        if (file.exists() && file.isFile() && file.canRead()) {
            input = new Scanner(file);
            for (int i = 0; i < entries; i++) {
                pack[i] = input.nextInt();
            }
        } else {
            // Error with file
            System.out.println("File not found");
            return Optional.empty();
        }

        for (int i = 0; i < entries; i++) {
            if (pack[i] == 0) {
                // 0 value
                System.out.println("There is a card with 0 value.");
                return Optional.empty();
            } else if (pack[i] < 0) {
                // Negative value
                System.out.println("There is a card with a negative value.");
                return Optional.empty();
            }
        }

        return Optional.of(pack);
    }

    static void generatePack(int players) throws FileNotFoundException {
        ArrayList<Integer> pack = new ArrayList<>();

        while (pack.size() < (players * 8)) {
            for (int i = 1; i <= players; i++) {
                if (pack.size() == (players * 8)) {
                    break;
                } else {
                    pack.add(i);
                }
            }
        }

        Collections.shuffle(pack);

        PrintWriter output = new PrintWriter("src/pack.txt");

        for (Integer integer : pack) {
            output.println(integer);
        }

        output.close();
    }

    public static void log(String s){
        System.out.println(s);
    }
}