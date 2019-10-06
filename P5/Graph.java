import java.util.*;
import java.io.PrintWriter;

class GraphComparator implements Comparator<Integer> {

    private double[] values;

    public GraphComparator(double[] values) {
        this.values = values;
    }

    @Override
    public int compare(Integer a, Integer b) {
        double diff = values[a] - values[b];
        if (diff < -1e-6) {
            return -1;
        } else if (diff > 1e-6) {
            return 1;
        } else {
            return 0;
        }
    }
}

public class Graph {

    private int numCities;
    private HashMap<String, Integer> citiesToIdx;
    private String[] idxToCities;
    private LinkedList<Integer>[] graphList;
    private LinkedList<Double>[] weightList;

    public Graph(int n_cities, int n_routes) {
        graphList = new LinkedList[n_cities];
        weightList = new LinkedList[n_cities];
        idxToCities = new String[n_cities];
        citiesToIdx = new HashMap<>();
        for (int i = 0; i < n_cities; i++) {
            graphList[i] = new LinkedList<>();
            weightList[i] = new LinkedList<>();
        }
        numCities = n_cities;
    }

    // Create the graph
    public void addRoute(String u, String v, double w) {
        if (!citiesToIdx.containsKey(u)) {
            idxToCities[citiesToIdx.size()] = u;
            citiesToIdx.put(u, citiesToIdx.size());
        }
        if (!citiesToIdx.containsKey(v)) {
            idxToCities[citiesToIdx.size()] = v;
            citiesToIdx.put(v, citiesToIdx.size());
        }
        graphList[citiesToIdx.get(u)].add(citiesToIdx.get(v));
        graphList[citiesToIdx.get(v)].add(citiesToIdx.get(u));
        weightList[citiesToIdx.get(u)].add(w);
        weightList[citiesToIdx.get(v)].add(w);
    }

    //part 1: Find the connected components and the bridges
    public void analyzeGraph(StringBuilder out) {

        int timer = 0;
        int[] low = new int[numCities];
        int[] times = new int[numCities];
        boolean[] visited = new boolean[numCities];

        LinkedList<Integer> sepEdges = new LinkedList<>();
        LinkedList<Integer> stack = new LinkedList<>();
        LinkedList<Integer> prev = new LinkedList<>();

        LinkedList<Integer> callbackStack = new LinkedList<>();
        LinkedList<Integer> callbackPrev = new LinkedList<>();

        int components = 0;

        for (int i = 0; i < numCities; i++) {
            if (visited[i]) {
                continue;
            }
            int callbackFlag = Integer.MIN_VALUE;
            components += 1;
            stack.push(i);
            prev.push(-1);
            while (!stack.isEmpty()) {
                int current = stack.pop();

                if (current == callbackFlag) {
                    current = callbackStack.pop();
                    int adj = callbackPrev.pop();
                    low[current] = Math.min(low[current], low[adj]);
                    if (times[current] < low[adj]) {
                        sepEdges.add(current);
                        sepEdges.add(adj);
                    }
                    continue;
                }

                int parent = prev.pop();
                if (!visited[current]) {
                    timer++;
                    low[current] = timer;
                    times[current] = timer;
                    visited[current] = true;
                    LinkedList<Integer> adj = graphList[current];
                    for (int node : adj) {
                        if (node == parent) {
                            continue;
                        }
                        if (visited[node]) {
                            low[current] = Math.min(low[current], times[node]);
                        } else {
                            callbackStack.push(current);
                            callbackPrev.push(node);
                            stack.push(callbackFlag);
                            stack.push(node);
                            prev.push(current);
                        }
                    }
                }
            }
        }

        out.append(components);
        out.append('\n');
        out.append(sepEdges.size() / 2);
        out.append('\n');
        Iterator<Integer> iter = sepEdges.descendingIterator();
        while (iter.hasNext()) {
            String u = idxToCities[iter.next()];
            String v = idxToCities[iter.next()];
            if (u.compareTo(v) < 0) {
                out.append(String.format("%s %s\n", u, v));
            } else {
                out.append(String.format("%s %s\n", v, u));
            }
        }
    }

    // Part 2: Find a ticket using Dijkstra
    public void findCost(String source, String destination, StringBuilder out) {

        int s = citiesToIdx.get(source);

        double[] distToSource = new double[numCities];
        int[] prevNode = new int[numCities];

        distToSource[s] = 0;

        PriorityQueue<Integer> queue = new PriorityQueue<>(numCities, new GraphComparator(distToSource));
        for (int i = 0; i < numCities; i++) {
            if (i != s) {
                distToSource[i] = Double.POSITIVE_INFINITY;
            }
            prevNode[i] = -1;
            queue.add(i);
        }
        while (!queue.isEmpty()) {
            int u = queue.poll();
            LinkedList<Integer> neighbors = graphList[u];
            LinkedList<Double> weights = weightList[u];
            assert neighbors.size() == weights.size();
            Iterator<Integer> neighborIterator = neighbors.iterator();
            Iterator<Double> weightIterator = weights.iterator();
            while (neighborIterator.hasNext()) {
                int v = neighborIterator.next();
                double weight = weightIterator.next();
                double altPathWeight = distToSource[u] + weight;
                if (altPathWeight < distToSource[v]) {
                    queue.remove(v);
                    distToSource[v] = altPathWeight;
                    prevNode[v] = u;
                    queue.add(v);
                }
            }
        }

        int currentNode = citiesToIdx.get(destination);
        LinkedList<String> shortestPath = new LinkedList<>();
        while (currentNode != -1) {
            shortestPath.push(idxToCities[currentNode]);
            currentNode = prevNode[currentNode];
        }
        ListIterator<String> cityIterator = shortestPath.listIterator();
        double totalDistance = distToSource[citiesToIdx.get(destination)];
        if (totalDistance == Double.POSITIVE_INFINITY) {
            out.append("not possible");
            return;
        }
        while (cityIterator.hasNext()) {
            out.append(String.format("%s ", cityIterator.next()));
        }
        out.append(String.format("%.2f\n", totalDistance));
    }

    private void tour(LinkedList<Integer>[] mst, int node, boolean[] visited, StringBuilder out) {
        if (visited[node]) {
            return;
        }
        out.append(idxToCities[node]);
        out.append('\n');
        visited[node] = true;
        String[] children = new String[mst[node].size()];
        Iterator<Integer> iter = mst[node].iterator();
        int i = 0;
        while (iter.hasNext()) {
            children[i++] = idxToCities[iter.next()];
        }
        Arrays.sort(children);
        for (String city : children) {
            tour(mst, citiesToIdx.get(city), visited, out);
        }
    }

    // Part 3: Try to do a tour of all cities
    public void eulerianTour(String rootName, StringBuilder out) {

        int root = citiesToIdx.get(rootName);
        double[] prevWeight = new double[numCities];
        int[] prevEdge = new int[numCities];
        boolean[] marked = new boolean[numCities];
        for (int i = 0; i < numCities; i++) {
            prevWeight[i] = Double.POSITIVE_INFINITY;
            prevEdge[i] = -1;
        }
        prevWeight[root] = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>(new GraphComparator(prevWeight));
        queue.add(root);
        while (!queue.isEmpty()) {
            int u = queue.poll();
            marked[u] = true;
            LinkedList<Integer> neighbors = graphList[u];
            LinkedList<Double> weights = weightList[u];
            assert neighbors.size() == weights.size();
            Iterator<Integer> neighborIterator = neighbors.iterator();
            Iterator<Double> weightIterator = weights.iterator();
            while (neighborIterator.hasNext()) {
                int v = neighborIterator.next();
                double weight = weightIterator.next();
                if (marked[v]) {
                    continue;
                }
                if (weight < prevWeight[v]) {
                    queue.remove(v);
                    prevEdge[v] = u;
                    prevWeight[v] = weight;
                    queue.add(v);
                }
            }
        }
        LinkedList<Integer>[] mst = new LinkedList[numCities];
        for (int i = 0; i < numCities; i++) {
            mst[i] = new LinkedList<>();
        }
        for (int i = 0; i < numCities; i++) {
            int j = prevEdge[i];
            if (j != -1) {
                mst[i].add(j);
                mst[j].add(i);
            }
        }
        boolean[] visited = new boolean[numCities];
        tour(mst, root, visited, out);
    }
}
