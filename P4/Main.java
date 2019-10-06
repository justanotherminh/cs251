import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This is your Main program. This File will be run all operations, based on the input different function will be called.
 * <p>
 * Complete the given and add necessary functions as per your requirement.
 */

public class Main {
    public static void RyhmeOrder(Scanner in, PrintWriter out) {
        //use in and out for file input output
        //use your implemented Quick Sort for solving RhymeOrder Operation
        int lines = Integer.parseInt(in.nextLine());
        String[] words = new String[lines];
        for (int i = 0; i < lines; i++) {
            words[i] = in.nextLine();
        }
        Rhymer r = new Rhymer(words);
        r.rhymeOperation(out);
    }

    public static void SuffixShare(Scanner in, PrintWriter out, int k) {
        //use in and out for file input output
        //use your implemented QuickSort for solving Suffix Operation
        int lines = Integer.parseInt(in.nextLine());
        String[] words = new String[lines];
        for (int i = 0; i < lines; i++) {
            words[i] = in.nextLine();
        }
        Rhymer r = new Rhymer(words);
        r.suffixSharing(k, out);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("ERROR: Not enough Parameters");
            System.exit(0);
        }
        Scanner in = null;
        PrintWriter out = null;
        try {
            in = new Scanner(new File(args[0]));
            out = new PrintWriter(new File(args[1]));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        int PartNo = Integer.parseInt(in.nextLine());

        if (PartNo == 1) {
            //Part 1: Word Sorting
            int OperationNo = Integer.parseInt(in.nextLine());
            if (OperationNo == 1) {
                //Operation: Rhyme Word Sorting Order
                RyhmeOrder(in, out);
            } else if (OperationNo == 2) {
                //Operation: Rhyme Word Sorting Order
                int k = Integer.parseInt(in.nextLine());

                SuffixShare(in, out, k);
            } else {
                System.out.println("Invalid Input");
                System.exit(0);
            }

        } else if (PartNo == 2) {
            int lines = Integer.parseInt(in.nextLine());
            BinarySearchTree tree = new BinarySearchTree();
            for (int i = 0; i < lines; i++) {
                String[] command = in.nextLine().split(" ");
                int key = 0;
                if (command.length >= 2) {
                    key = Integer.parseInt(command[1]);
                }
                int key2 = 0;
                if (command.length >= 3) {
                    key2 = Integer.parseInt(command[2]);
                }
                switch (command[0]) {
                    case "insert":
                        tree.insertKey(key);
                        break;
                    case "delete":
                        if (tree.searchKey(key)) {
                            tree.deleteKey(key);
                            out.print("deleted\r\n");
                        } else {
                            out.print("deletion failed\r\n");
                        }
                        break;
                    case "search":
                        if (tree.searchKey(key)) {
                            out.print("found\r\n");
                        } else {
                            out.print("not found\r\n");
                        }
                        break;
                    case "height":
                        int height = tree.height(key);
                        if (height == -1) {
                            out.print("none\r\n");
                        } else {
                            out.printf("%d\r\n", height);
                        }
                        break;
                    case "range":
                        int sum = tree.rangeSum(key, key2);
                        out.printf("%d\r\n", sum);
                        break;
                    case "postorder":
                        tree.postorder(out);
                        break;
                    case "levelorder":
                        tree.levelorder(out);
                        break;
                    case "lca":
                        Node common = tree.LCA(key, key2);
                        if (common != null) {
                            out.printf("%d\r\n", common.data);
                        } else {
                            out.print("none\r\n");
                        }
                        break;
                    case "floor":
                        Node floor = tree.floor(key);
                        if (floor != null) {
                            out.printf("%d\r\n", floor.data);
                        } else {
                            out.print("none\r\n");
                        }
                        break;
                    case "ceil":
                        Node ceil = tree.ceil(key);
                        if (ceil != null) {
                            out.printf("%d\r\n", ceil.data);
                        } else {
                            out.print("none\r\n");
                        }
                        break;
                    case "dist":
                        int dist = tree.dist(key, key2);
                        if (dist != -1) {
                            out.printf("%d\r\n", dist);
                        } else {
                            out.print("none\r\n");
                        }
                        break;
                    case "insertRB":
                        tree.insertRB(key);
                        break;
                    case "Bheight":
                        int bheight = tree.getBlackHeight(key);
                        if (bheight != -1) {
                            out.printf("%d\r\n", bheight);
                        } else {
                            out.print("none\r\n");
                        }
                        break;
                }
            }

        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }

        in.close();
        out.close();
    }
}