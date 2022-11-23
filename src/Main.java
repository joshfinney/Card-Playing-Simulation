import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    static Scanner Input;
    static int numberOfPlayer;
    static int victorId = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        Input = new Scanner(System.in);
        boolean validPack = false;
        // generate directories outside of environment
        Path logsRoot = Paths.get("out/logs/");
        Files.createDirectories(logsRoot);
        ArrayList<Card> pack = new ArrayList<>();
        println("________________________");
        println("SET UP");
        println("Enter number of players:");
        if (args.length>0 && args[0] != null) {
            println("Testing provided value: "+args[0]);
            numberOfPlayer = Integer.parseInt(args[0]);
        }
        else {numberOfPlayer = Input.nextInt();
        Input.nextLine();
        }
        //testing purpose randomization
        //generatePack(numberOfPlayer);
        int argIndex = 1;
        while (!validPack) {
            Optional<int[]> tempPack;
            if (argIndex<args.length){
                println("Provided file location: "+args[argIndex]);
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
        //Player and Decks are numbered by positive integers starting at 1
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

        println();
        println("________________________");
        println("INITIAL CARDS");
        for (Player player : Player.players) {
            println("Player " + (player.id) + ": Hand - " + player.readContents() + ", Deck - " + Deck.decks.get(player.id - 1).readContents());
        }
        println();
        println("________________________");
        println("GAMEPLAY");

        // Start of game
        ExecutorService te = Executors.newCachedThreadPool();
        while (victorId==0){
            // Call and wait for all players to execute one round of play
            te.invokeAll(Player.players);
            println("");
        }
        te.shutdown();
        //await for processes to finish
        try {
            if (!te.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)){
                te.shutdownNow();
                println("FATAL ERROR TIMEOUT");
            }
        } catch (InterruptedException e) {
            println(e.getMessage());
        }
        Deck.printAll();
    }
    public synchronized static void endGameCheck(int id) {
        if (victorId ==0){
            victorId = id;
        }
    }

    static Optional<int[]> readAndValidatePack(int entries, String location) {
        int[] pack = new int[entries];
        int lines = 0;
        if (location.isEmpty()) {
            println("Enter pack location:");
            location = (Input.nextLine());
        }

        try {
            File file = new File(location);
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                input.nextLine();
                lines++;
            }
            input.close();


            if (lines != entries) {
                // Not enough cards in file
                println("Wrong number of cards, the pack requires " + entries + " cards to start. The input pack has " + lines + " cards. ");
                return Optional.empty();
            }

            if (file.exists() && file.isFile() && file.canRead()) {
                input = new Scanner(file);
                for (int i = 0; i < entries; i++) {
                    pack[i] = input.nextInt();
                }
            } else {
                // Error with file
                println("File not found");
                return Optional.empty();
            }

            for (int i = 0; i < entries; i++) {
                if (pack[i] == 0) {
                    // 0 value
                    println("There is a card with 0 value.");
                    return Optional.empty();
                } else if (pack[i] < 0) {
                    // Negative value
                    println("There is a card with a negative value.");
                    return Optional.empty();
                }
            }
        }
        catch (FileNotFoundException e){
            println("There is no card file to read from");
            return Optional.empty();
        }
        return Optional.of(pack);
    }


    public static int getNumberOfPlayers() {
        return numberOfPlayer;
    }

    public static void println(String s){
        System.out.println(s);
    }
    public static void println(){
        System.out.println();
    }
}