import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class AbstractCardOwner {
    int id;
    protected ArrayList<Card> cards;
    String writePath;
    AbstractCardOwner(int id,String path) throws FileNotFoundException {
        this.writePath = path;
        this.id=id;
        cards = new ArrayList<>();
        File writeFile = new File(writePath);
        output = new PrintWriter(writeFile);
    }
    PrintWriter output;

    Card drawRandomCard(){
        int randomIndex = (int) Math.floor(Math.random()*cards.size());
        return cards.remove(randomIndex);
    }
    void addCard(Card addedCard){
        cards.add(addedCard);
    }

    String readContents(){
        String contents = "";
        for (Card a : cards) {
            contents = contents.concat(a.getValue()+" ");
        }
        return contents;
    }
    void closeWriter(){
        output.close();
    }


}
