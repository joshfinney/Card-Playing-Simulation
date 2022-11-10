import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    static Scanner Input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int numberOfPlayer;
        boolean validPack = false;
        Optional<int[]> pack = Optional.empty();
        ArrayList<Player> players = new ArrayList<Player>();

        System.out.println("Enter number of players:");
        numberOfPlayer = Input.nextInt();
        Input.nextLine();

        generatePack(numberOfPlayer);

        while (!validPack) {
            pack = readAndValidatePack(numberOfPlayer * 8);
            if (pack.isPresent()) {
                validPack = true;
            }
        }

        // Initialise players and their decks
        for (int i=0;i<numberOfPlayer;i++){
            players.add(new Player(i));
            Deck.decks.put(i,new Deck(i));
        }

        while (pack.get().length >= numberOfPlayer*2) {
            for (int i=0; i<numberOfPlayer;i++) {
                //Fill player's hand
                players.get(i).cards.add(new Card(pack.get()[0]));
                pack = Optional.of(removeFirstElement(pack.get()));

                //Fill each player's deck
                Deck.decks.get(i).dealCard(new Card(pack.get()[0]));
                pack = Optional.of(removeFirstElement(pack.get()));
            }
        }
        for (Player player : players) {
            System.out.println("PLAYER " + (player.id+1) + ": Hand - " + player.cards + ", Deck - " + Deck.decks.get(player.id).cards);
        }
    }

    public static int[] removeFirstElement(int[] arr) {
        int newArr[] = new int[arr.length - 1];
        for (int i = 1; i < arr.length; i++) {
            newArr[i-1] = arr[i];
        }
        return newArr;
    }

    static Optional<int[]> readAndValidatePack(int entries) throws FileNotFoundException {
        int[] pack = new int[entries];
        int lines = 0;
        System.out.println("Enter pack location:");
        String location = Input.nextLine();

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