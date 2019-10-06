import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This is your Main program. This File will be run to test all cases, based on the input different function will be called.
 *
 * Complete the given and add necessary functions as  per your requirement.
 */

public class Main {
    public static void StackChecking(Scanner in, PrintWriter out) {
        int lines = Integer.parseInt(in.nextLine());
        StringBuilder output = new StringBuilder();
        LinkedList<Pair> stack = new LinkedList<>();
        while (in.hasNextLine()) {
            String[] testCase = in.nextLine().split(" ");
            if (testCase[0].equals("i")) {
                Pair p = new Pair(Integer.parseInt(testCase[1]), Integer.parseInt(testCase[2]));
                Node<Pair> node = new Node<>(p, null);
                stack.push(node);
            } else if (testCase[0].equals("p")) {
                Node<Pair> node = stack.pop();
                if (node != null) {
                    int[] p = node.getData().getPair();
                    output.append(String.format("%d %d\n", p[0], p[1]));
                } else {
                    output.append("empty\n");
                }
            }
        }
        output.deleteCharAt(output.length() - 1);
        out.print(output);
        out.close();
    }

    public static void WordSearch(Scanner in, PrintWriter out){
        //use in and out for file input output
        //use your implemented stack for solving problem 2
        StringBuilder output = new StringBuilder();
        String[] dim = in.nextLine().split(" ");
        int row = Integer.parseInt(dim[0]);
        int col = Integer.parseInt(dim[1]);
        char[][] grid = new char[row][col];
        for (int i = 0; i < row; i++) {
            String[] chars = in.nextLine().split(" ");
            for (int j = 0; j < col; j++) {
                grid[i][j] = chars[j].charAt(0);
            }
        }
        String word = in.nextLine();
        Searcher s = new Searcher(grid, word);
        LinkedList<Pair> resultReverse = s.Search();
        if (resultReverse != null) {
            LinkedList<Pair> result = new LinkedList<>();
            while (resultReverse.getHead() != null) {
                result.push(resultReverse.pop());
            }
            while (result.getHead() != null) {
                int[] pair = result.pop().getData().getPair();
                output.append(String.format("%d %d\n", pair[0], pair[1]));
            }
        } else {
            output.append("not found\n");
        }
        output.deleteCharAt(output.length() - 1);
        out.print(output);
        out.close();
    }

    public static void CircularQueue(Scanner in, PrintWriter out){
        //use in and out for file input output
        //use your implemented stack for solving problem 3
        int lines = Integer.parseInt(in.nextLine());
        StringBuilder output = new StringBuilder();
        CircularDeque queue = new CircularDeque();
        while (in.hasNextLine()) {
            String[] testCase = in.nextLine().split(" ");
            if (testCase[0].equals("e")) {
                if (testCase[1].equals("b")) {
                    queue.enqueueBack(Integer.parseInt(testCase[2]));
                } else {
                    queue.enqueueFront(Integer.parseInt(testCase[2]));
                }
            } else if (testCase[0].equals("d")) {
                if (testCase[1].equals("b")) {
                    output.append(queue.dequeBack());
                    output.append("\n");
                } else {
                    output.append(queue.dequeFront());
                    output.append("\n");
                }
            } else {
                output.append(String.format("%d %d %d\n",
                                            queue.getArraySize(), queue.getFrontIndex(), queue.getRearIndex()));
            }
        }
        output.deleteCharAt(output.length() - 1);
        out.print(output);
        out.close();
    }

    public static void main(String[] args) {
        if(args.length!=2){
            System.out.println("ERROR: Not enough Parameters");
            System.exit(0);
        }
        Scanner in=null;
        PrintWriter out=null;
        try {
            in = new Scanner(new File(args[0]));
            out= new PrintWriter(new File(args[1]));
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }

        int ProblemNo = Integer.parseInt(in.nextLine());

        if(ProblemNo==1){
            //Problem 1: Checking of your implemented stack
            StackChecking(in,out);
        }
        else if(ProblemNo==2){
            //Problem 2: Word searching probme
            WordSearch(in, out);
        }
        else if(ProblemNo==3){
            //Problem 3: Circular Dequeue Implementation checking
            CircularQueue(in, out);
        }
        else{
            System.out.println("Invalid Input");
            System.exit(0);
        }

        in.close();
        out.close();
    }
}