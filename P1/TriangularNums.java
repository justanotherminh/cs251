import java.util.*;
import java.io.*;

/**
 * Read a file that contains 2 lines:
 * <p>
 * <ol>
 *     <li>There are 2 numbers in this line:
 *         <ul>
 *             <li>First one specifies maximum digits allowed for each number.</li>
 *             <li>Second one specifies X such that if current line in output has C numbers, then next line has C+X numbers.
 *                  The last line in the output can ignore this rule if numbers provided cannot fill up the last line.
 *             </li>
 *         </ul>
 *     </li>
 *     <li>Sequence of numbers.</li>
 * </ol>
 * writeToFile() writes the string of these numbers which are in triangular pattern to a file.
 * All numbers are <b>left justified</b>.
 * There is no "\n" at the end of the last line.
 *
 * E.g.
 * Input file:
 * 2 2
 * 1 2 3 4 5 6 7 8 9 10
 *
 * Output:
 * | 1  |
 * | 2  | 3  | 4  |
 * | 5  | 6  | 7  | 8  | 9  |
 * | 10 |
 *
 * @author Hongxin Chu
 */
public class TriangularNums {

    /**
     * Construct a new TriangularNums object.
     * @param inputPath Path of the input file.
     */
    int maxDigits = 0;
    int lineDiff = 0;
    ArrayList<Integer> nums = new ArrayList();
    public TriangularNums(String inputPath) {
        try {
            File f = new File(inputPath);
            Scanner s = new Scanner(f);
            this.maxDigits = s.nextInt();
            this.lineDiff = s.nextInt();
            while (s.hasNext()) {
                nums.add(s.nextInt());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the string of numbers which are in triangular pattern to a file.
     * Make sure you flush the content to file instead of keeping the string inside the buffer.
     * Make sure no output stream is open when return.
     * @param filePath The path of the file to write.
     */
    public void writeToFile(String filePath) {
        try {
            PrintWriter w = new PrintWriter(filePath);
            int numsPerLine = 1;
            int n = 0;
            for (int num : this.nums) {
                String padding = "";
                for (int p = 0; p < this.maxDigits - Integer.toString(num).length(); p++) {
                    padding += " ";
                }
                w.print("| " + Integer.toString(num) + padding + " ");
                n += 1;
                if (n == numsPerLine) {
                    n = 0;
                    numsPerLine += this.lineDiff;
                    w.print("|");
                    if (num != this.nums.get(this.nums.size() - 1)) {
                        w.print("\n");
                    }
                }
            }
            if (n != 0) {
                w.print("|");
            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
