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

        System.out.println(pack.toString());
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

        for (int i = 0; i < pack.size(); i++) {
            System.out.println(i);
            output.println(pack.get(i));
        }

        output.close();
    }

    public static void log(String s){
        System.out.println(s);
    }
}