import java.io.PrintWriter;
import java.util.LinkedList;

public class BinarySearchTree {

    private Node root;

    public BinarySearchTree() {
        root = null;
    }

    private static Node insertNode(Node node, int key) {
        if (node == null) {
            node = new Node(key);
            return node;
        }
        if (key <= node.data) {
            node.left = insertNode(node.left, key);
        } else {
            node.right = insertNode(node.right, key);
        }
        return node;
    }

    public void insertKey(int key) {
        root = insertNode(root, key);
    }

    private static Node deleteNode(Node node, int key) {
        if (node == null) {
            return node;
        }
        if (key < node.data) {
            node.left = deleteNode(node.left, key);
        } else if (key > node.data) {
            node.right = deleteNode(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            Node minNode = node.right;
            while (minNode.left != null) {
                minNode = minNode.left;
            }
            node.right = deleteNode(node, minNode.data);
        }
        return node;
    }

    public void deleteKey(int key) {
        root = deleteNode(root, key);
    }

    private static boolean searchNode(Node node, int key) {
        if (node == null) {
            return false;
        }
        if (key < node.data) {
            return searchNode(node.left, key);
        } else if (key > node.data) {
            return searchNode(node.right, key);
        } else {
            return true;
        }
    }

    public boolean searchKey(int key) {
        return searchNode(root, key);
    }

    public int height(int key) {
        Node currentNode = root;
        int height = 0;
        while (currentNode != null) {
            if (key < currentNode.data) {
                currentNode = currentNode.left;
            } else if (key > currentNode.data) {
                currentNode = currentNode.right;
            } else {
                return height;
            }
            height++;
        }
        return -1;
    }

    private static void postorder(Node node, PrintWriter out) {
        if (node.left != null) {
            postorder(node.left, out);
        }
        if (node.right != null) {
            postorder(node.right, out);
        }
        out.printf("%d ", node.data);
    }

    public void postorder(PrintWriter out) {
        if (root == null) {
            out.print("none\r\n");
            return;
        }
        postorder(root, out);
        out.print("\r\n");
    }

    public void levelorder(PrintWriter out) {
        if (root == null) {
            out.print("none\r\n");
            return;
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node temp = queue.pop();
            out.printf("%d ", temp.data);
            if (temp.left != null) {
                queue.add(temp.left);
            }
            if (temp.right != null) {
                queue.add(temp.right);
            }
        }
        out.print("\r\n");
    }

    private static LinkedList<Node> searchPath(Node node, int key, LinkedList<Node> list) {
        if (node == null) {
            return null;
        }
        list.add(node);
        if (key < node.data) {
            list = searchPath(node.left, key, list);
        } else if (key > node.data) {
            list = searchPath(node.right, key, list);
        }
        return list;
    }

    public Node LCA(int key1, int key2) {
        LinkedList<Node> path1 = searchPath(root, key1, new LinkedList<>());
        LinkedList<Node> path2 = searchPath(root, key2, new LinkedList<>());
        if (path1 == null || path2 == null) {
            return null;
        }
        Node common = null;
        while (path1.size() > 0 && path2.size() > 0 && path1.peek().data == path2.peek().data) {
            common = path1.pop();
            path2.pop();
        }
        return common;
    }

    private static LinkedList<Node> treeToList(Node node, LinkedList<Node> list) {
        if (node.left != null) {
            list = treeToList(node.left, list);
        }
        list.add(node);
        if (node.right != null) {
            list = treeToList(node.right, list);
        }
        return list;
    }

    public Node floor(int key) {
        LinkedList<Node> list = treeToList(root, new LinkedList<>());
        Node node = null;
        while (!list.isEmpty() && list.peek().data <= key) {
            node = list.pop();
        }
        if (node != null) {
            return node;
        } else {
            return null;
        }
    }

    public Node ceil(int key) {
        LinkedList<Node> list = treeToList(root, new LinkedList<>());
        Node node = list.pop();
        while (!list.isEmpty() && list.peek().data < key) {
            node = list.pop();
        }
        if (list.size() == 0) {
            return null;
        } else {
            return list.pop();
        }
    }

    public int rangeSum(int left, int right) {
        LinkedList<Node> list = treeToList(root, new LinkedList<>());
        int sum = 0;
        while (!list.isEmpty()) {
            int n = list.pop().data;
            if (n >= left && n <= right) {
                sum += n;
            }
        }
        return sum;
    }

    public int dist(int key1, int key2) {
        int height1 = height(key1);
        int height2 = height(key2);
        if (height1 == -1 || height2 == -1) {
            return -1;
        }
        Node common = LCA(key1, key2);
        int heightCommon = height(common.data);
        return height1 + height2 - 2 * heightCommon;
    }

    private static boolean isRed(Node node) {
        if (node == null) {
            return false;
        }
        return node.isRed;
    }

    private static Node rotateLeft(Node node) {
        Node result = node.right;
        node.right = result.left;
        result.left = node;
        result.isRed = node.isRed;
        node.isRed = true;
        return result;
    }

    private static Node rotateRight(Node node) {
        Node result = node.left;
        node.left = result.right;
        result.right = node;
        result.isRed = node.isRed;
        node.isRed = true;
        return result;
    }

    private static void flipColors(Node node) {
        node.isRed = true;
        node.left.isRed = false;
        node.right.isRed = false;
    }

    private static Node insertRB(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }
        if (key <= node.data) {
            node.left = insertRB(node.left, key);
        } else {
            node.right = insertRB(node.right, key);
        }
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }

    public void insertRB(int key) {
        root = insertRB(root, key);
        root.isRed = false;
    }

    public int getBlackHeight(int key) {
        Node current = root;
        while (current != null) {
            if (key < current.data) {
                current = current.left;
            } else if (key > current.data) {
                current = current.right;
            } else {
                break;
            }
        }
        if (current == null) {
            return -1;
        }
        int bheight = 0;
        while (current != null) {
            if (!current.isRed) {
                bheight++;
            }
            current = current.left;
        }
        return bheight;
    }
}
