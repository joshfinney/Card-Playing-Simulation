//Simple atomic wrapper of an int value
class Card{
    private int value;
    Card(int value){
        this.value = value;
    }

    synchronized int getValue(){
        return value;
    }
}