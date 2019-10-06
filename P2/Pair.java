//This class is for maintaining paired item for problem 1 and 2

public class Pair {
    //Declare necessary variables and methods
    private int[] pair = new int[2];
    public Pair(int a, int b) {
        pair[0] = a;
        pair[1] = b;
    }

    public int[] getPair() {
        return pair;
    }
}
