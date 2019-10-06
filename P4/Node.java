public class Node {

    int data;
    Node left;
    Node right;
    boolean isRed;

    public Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.isRed = true;
    }
}
