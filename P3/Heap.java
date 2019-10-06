import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Heap {

    private static final int BRANCHES = 2;

    private int[] heap;
    private int size;

    /**
     * Construct a new heap
     */
    public Heap() {
        this.heap = new int[BRANCHES];
        this.size = 0;
    }

    /**
     * Insert element into this heap
     */
    public void insert(int e) {
        // Resize if array is full
        if (this.size == this.heap.length) {
            resize();
        }
        this.heap[this.size] = e;
        int currentIdx = this.size;
        // Bubble up
        while (currentIdx > 0) {
            int parentIdx = (currentIdx + 1) / 2 - 1;
            if (this.heap[currentIdx] >= this.heap[parentIdx]) {
                break;
            }
            // Swap child with parent
            int temp = this.heap[parentIdx];
            this.heap[parentIdx] = this.heap[currentIdx];
            this.heap[currentIdx] = temp;
            currentIdx = parentIdx;
        }
        this.size += 1;
    }

    /**
     * Return number of elements in this heap
     * @return size of the heap
     */
    public int size() {
        return this.size;
    }

    /**
     * Peek the smallest element in this heap
     * @throws NoSuchElementException if heap is empty
     * @return the smallest element in this heap.
     */
    public int min() throws NoSuchElementException{
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.heap[0];
    }

    /**
     * Remove the smallest element in this heap
     * @throws NoSuchElementException if heap is empty
     * @return the smallest element in this heap
     */
    public int removeMin() throws NoSuchElementException{
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int result = this.heap[0];
        this.heap[0] = this.heap[this.size - 1];
        this.size -= 1;
        int currentIdx = 0;
        while (currentIdx < this.size / 2) {
            int leftChildIdx = (currentIdx + 1) * 2 - 1;
            int rightChildIdx = (currentIdx + 1) * 2;
            int minIdx = currentIdx;
            if (this.heap[leftChildIdx] < this.heap[minIdx]) {
                minIdx = leftChildIdx;
            }
            if (this.heap[rightChildIdx] < this.heap[minIdx] && rightChildIdx < this.size) {
                minIdx = rightChildIdx;
            }
            if (currentIdx == minIdx) {
                break;
            }
            int temp = this.heap[minIdx];
            this.heap[minIdx] = this.heap[currentIdx];
            this.heap[currentIdx] = temp;
            currentIdx = minIdx;
        }
        return result;
    }

    /**
     * Return a sorted array of this heap.
     * Call removeMin until all elements are removed from the heap.
     * @return a sorted array of this heap
     */
    public int[] sort() {
        int[] result = new int[this.size];
        int i = 0;
        while (this.size > 0) {
            result[i] = removeMin();
            i += 1;
        }
        return result;
    }

    /**
     * Read in an input file and write output to output stream
     * Scanner in starts from the beginning of the file
     * @param in input Scanner
     * @param out output PrintStream
     */
    public static void generateOutput(Scanner in, PrintStream out) {
        // Skip first line
        in.nextLine();
        int lines = Integer.parseInt(in.nextLine());
        Heap heap = new Heap();
        StringBuilder output = new StringBuilder();
        while (in.hasNextLine()) {
            String[] command = in.nextLine().split(" ");
            if (command[0].equals("i")) {
                heap.insert(Integer.parseInt(command[1]));
            } else if (command[0].equals("r")) {
                try {
                    heap.removeMin();
                } catch (NoSuchElementException e) {
                    output.append("empty\n");
                }
            } else if (command[0].equals("m")) {
                try {
                    output.append(heap.min());
                    output.append("\n");
                } catch (NoSuchElementException e) {
                    output.append("empty\n");
                }
            } else if (command[0].equals("s")) {
                if (heap.size() == 0) {
                    output.append("empty\n");
                } else {
                    int[] sorted = heap.sort();
                    for (int i : sorted) {
                        output.append(i);
                        output.append("\n");
                    }
                }
            }
        }
        output.deleteCharAt(output.length() - 1);
        out.print(output);
    }

    private void resize() {
        int[] temp = new int[this.size * 2];
        System.arraycopy(this.heap, 0, temp, 0, this.size);
        this.heap = temp;
    }
}
