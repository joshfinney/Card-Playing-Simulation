import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    static Scanner Input;
    static int numberOfPlayer;
    static boolean gameEnded = false;
    static int victorId = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        Input = new Scanner(System.in);
        boolean validPack = false;
        ArrayList<Card> pack = new ArrayList<>();
        System.out.println("________________________");
        System.out.println("SET UP");
        System.out.println("Enter number of players:");
        if (args.length>0 && args[0] != null) {
            log("Testing provided value: "+args[0]);
            numberOfPlayer = Integer.parseInt(args[0]);
        }
        else {numberOfPlayer = Input.nextInt();
        Input.nextLine();
        }
        generatePack(numberOfPlayer);
        int argIndex = 1;
        while (!validPack) {
            Optional<int[]> tempPack;
            if (argIndex<args.length){
                log("Provided file location: "+args[argIndex]);
                tempPack = readAndValidatePack(numberOfPlayer*8,args[argIndex]);
                argIndex++;
            }
            else tempPack = readAndValidatePack(numberOfPlayer * 8, "");
            if (tempPack.isPresent()) {
                validPack = true;
                pack = Arrays
                        .stream(tempPack.orElse(new int[numberOfPlayer * 8]))
                        .mapToObj(Card::new)
                        .collect(Collectors.toCollection(ArrayList::new));
            }
        }

        for (int i=1;i<=numberOfPlayer;i++){
            new Player(i);
            new Deck(i);
        }
        for (int i=0; i<4; i++){
            for (int j=0; j<numberOfPlayer;j++) {
                //Fill player's hand
                Player.dealCard(pack.remove(0),j);
                //Fill each player's deck
                Deck.dealCard(pack.remove(0),j);
            }
        }

        System.out.println("");
        System.out.println("________________________");
        System.out.println("INITIAL CARDS");
        for (Player player : Player.players) {
            System.out.println("Player " + (player.id) + ": Hand - " + player.readContents() + ", Deck - " + Deck.decks.get(player.id - 1).readContents());
        }
        System.out.println("");
        System.out.println("________________________");
        System.out.println("GAMEPLAY");

        // Start of game
        ExecutorService te = Executors.newCachedThreadPool();
        while (victorId==0){
            te.invokeAll(Player.players);
            System.out.println("");
        }
        te.shutdown();
        //await process finish
        try {
            if (!te.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)){
                te.shutdownNow();
            }
        } catch (InterruptedException e) {
            Main.log(e.getMessage());
        }
        Deck.printAll();
        //All output writers should be closed.
    }
    public synchronized static int endGameCheck(int id) {
        if (victorId ==0){
            gameEnded=true;
            victorId = id;
        }
        return victorId;
    }

    static Optional<int[]> readAndValidatePack(int entries, String location) throws FileNotFoundException {
        int[] pack = new int[entries];
        int lines = 0;
        if (location.isEmpty()) {
            System.out.println("Enter pack location:");
            location = (Input.nextLine());
        }

        String path;

        if (location.contains("test")) {
            path = "out/test/";
        } else {
            path = "src/";
        }

        File file = new File(path);

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

    // Used for testing purposes
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

    // Used for testing purposes
    public static void generatePackWithNegativeNumbers(int players) throws FileNotFoundException {
        ArrayList<Integer> pack = new ArrayList<>();
        Random rand = new Random();

        while (pack.size() < (players * 8)) {
            for (int i = 1; i <= players; i++) {
                if (pack.size() == (players * 8)) {
                    break;
                } else {
                    if (rand.nextInt(2) == 0) {
                        pack.add(i);
                    } else {
                        pack.add(-i);
                    }
                }
            }
        }

        Collections.shuffle(pack);

        PrintWriter output = new PrintWriter("src/testNegativePack.txt");

        for (Integer integer : pack) {
            output.println(integer);
        }

        output.close();
    }

    // Used for testing purposes
    public static void generatePackWithWrongRowCount(int players) throws FileNotFoundException {
        ArrayList<Integer> pack = new ArrayList<>();
        Random rand = new Random();
        int rowCount = rand.nextInt(10);

        while (pack.size() < rowCount) {
            for (int i = 1; i <= players; i++) {
                if (pack.size() == rowCount) {
                    break;
                } else {
                    pack.add(i);
                }
            }
        }

        Collections.shuffle(pack);

        PrintWriter output = new PrintWriter("src/testWrongRowCountPack.txt");

        for (Integer integer : pack) {
            output.println(integer);
        }

        output.close();
    }

    public static int getNumberOfPlayers() {
        return numberOfPlayer;
    }

    public static void log(String s){
        System.out.println(s);
    }
}