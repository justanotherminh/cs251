import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.HashMap;

public class Rhymer {
    private String[] wordList;

    public Rhymer(String[] wordList) {
        this.wordList = wordList;
    }

    public static void quickSort(String[] wordList, int start, int end) {
        if (end - start < 2) {
            return;
        }
        int pivot = start + 1;
        for (int i = start + 1; i < end; i++) {
            if (wordList[i].compareTo(wordList[start]) < 0) {
                String temp = wordList[pivot];
                wordList[pivot] = wordList[i];
                wordList[i] = temp;
                pivot++;
            }
        }
        String temp = wordList[start];
        wordList[start] = wordList[pivot - 1];
        wordList[pivot - 1] = temp;
        quickSort(wordList, start, pivot - 1);
        quickSort(wordList, pivot, end);
    }

    public void rhymeOperation(PrintWriter out) {
        for (int i = 0; i < wordList.length; i++) {
            StringBuilder sb = new StringBuilder(wordList[i]);
            sb = sb.reverse();
            wordList[i] = sb.toString();
        }
        quickSort(wordList, 0, wordList.length);
        for (int i = 0; i < wordList.length; i++) {
            StringBuilder sb = new StringBuilder(wordList[i]);
            sb = sb.reverse();
            wordList[i] = sb.toString();
        }
        for (String word : wordList) {
            out.println(word);
        }
    }

    public void suffixSharing(int k, PrintWriter out) {
        for (int i = 0; i < wordList.length; i++) {
            StringBuilder sb = new StringBuilder(wordList[i]);
            sb = sb.reverse();
            wordList[i] = sb.toString();
        }
        quickSort(wordList, 0, wordList.length);
        for (int i = 0; i < wordList.length; i++) {
            StringBuilder sb = new StringBuilder(wordList[i]);
            sb = sb.reverse();
            wordList[i] = sb.toString();
        }
        int longest = 0;
        for (String word : wordList) {
            if (word.length() > longest) {
                longest = word.length();
            }
        }
        HashMap<String, LinkedList<String>> suffixGroups = new HashMap<>();
        String suffix = "";
        int count = 0;
        LinkedList<String> suffixGroup = new LinkedList<>();
        for (int i = 1; i <= longest; i++) {
            for (String word : wordList) {
                if (word.length() < i) {
                    continue;
                }
                if (!word.substring(word.length() - i, word.length()).equals(suffix)) {
                    if (count >= k) {
                        suffixGroups.put(suffix, suffixGroup);
                    }
                    count = 1;
                    suffix = word.substring(word.length() - i, word.length());
                    suffixGroup = new LinkedList<>();
                    suffixGroup.push(word);
                } else {
                    count++;
                    suffixGroup.push(word);
                }
            }
        }
        String[] keys = new String[suffixGroups.keySet().size()];
        int i = 0;
        for (String key : suffixGroups.keySet()) {
            keys[i++] = key;
        }
        quickSort(keys, 0, keys.length);
        for (String key : keys) {
            out.printf("%s -> [", key);
            LinkedList<String> words = suffixGroups.get(key);
            String[] sortedWords = new String[words.size()];
            i = 0;
            while (!words.isEmpty()) {
                sortedWords[i++] = words.pop();
            }
            quickSort(sortedWords, 0, sortedWords.length);
            for (i = 0; i < sortedWords.length - 1; i++) {
                out.printf("%s, ", sortedWords[i]);
            }
            out.printf("%s]\n", sortedWords[i]);
        }
    }
}
