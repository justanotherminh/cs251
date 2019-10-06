import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is your Main program. This File will be run all operations, based on the input different function will be called.
 * <p>
 * Complete the given and add necessary functions as per your requirement.
 */

public class Main {
    // Read the graph
    private static Graph getGraph(Scanner in) {
        int nodes = in.nextInt();
        int edges = in.nextInt();
        Graph graph = new Graph(nodes, edges);
        for (int i = 0; i < edges; i++) {
            String u = in.next();
            String v = in.next();
            double weight = in.nextDouble();
            graph.addRoute(u, v, weight);
        }
        return graph;
    }

    // Part 1 : Find the connected components and the bridge
    public static void Part1(Scanner in, PrintWriter out) {
        //use in and out for file input output
        Graph graph = getGraph(in);
        StringBuilder output = new StringBuilder();
        graph.analyzeGraph(output);
        if (output.charAt(output.length() - 1) == '\n') {
            output.deleteCharAt(output.length() - 1);
        }
        out.print(output);
    }

    // Part 2 :  Find a ticket using Dijkstra
    public static void Part2(Scanner in, PrintWriter out) {
        //use in and out for file input output
        Graph graph = getGraph(in);
        StringBuilder output = new StringBuilder();
        while (in.hasNext()) {
            String u = in.next();
            if (u.equals("END")) {
                break;
            }
            String v = in.next();
            graph.findCost(u, v, output);
        }
        if (output.charAt(output.length() - 1) == '\n') {
            output.deleteCharAt(output.length() - 1);
        }
        out.print(output);
    }

    // Part 3 :Try to do a tour of all cities
    public static void Part3(Scanner in, PrintWriter out) {
        //use in and out for file input output
        Graph graph = getGraph(in);
        String root = in.next();
        StringBuilder output = new StringBuilder();
        graph.eulerianTour(root, output);
        if (output.charAt(output.length() - 1) == '\n') {
            output.deleteCharAt(output.length() - 1);
        }
        out.print(output);
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

        int PartNo = in.nextInt();

        if (PartNo == 1) {
            Part1(in, out);
        } else if (PartNo == 2) {
            Part2(in, out);
        } else if (PartNo == 3) {
            Part3(in, out);
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }

        in.close();
        out.close();
    }
}