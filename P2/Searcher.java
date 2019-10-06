public class Searcher {
    char[][] board;
    String word;

    public Searcher(char[][] board, String word) {
        this.board = board;
        this.word = word;
    }

    private boolean SearchFrom(LinkedList<Pair> stack, int start) {
        if (start == word.length()) {
            return true;
        }
        LinkedList<Pair> candidates = new LinkedList<>();
        int[] coord = stack.getHead().getData().getPair();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (coord[0] + i >= 0 && coord[1] + j >= 0 && coord[0] + i < board[0].length && coord[1] + j < board[0].length) {
                    if (board[coord[0] + i][coord[1] + j] == word.charAt(start)) {
                        candidates.push(new Node<>(new Pair(coord[0] + i, coord[1] + j), null));
                    }
                }
            }
        }
        while (candidates.getHead() != null) {
            stack.push(candidates.pop());
            if (SearchFrom(stack, start + 1)) {
                return true;
            } else {
                stack.pop();
            }
        }
        return false;
    }

    public LinkedList<Pair> Search() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    LinkedList<Pair> stack = new LinkedList<>();
                    stack.push(new Node<>(new Pair(i, j), null));
                    if (SearchFrom(stack, 1)) {
                        return stack;
                    }
                }
            }
        }
        return null;
    }
}
