//Implement singly linked list, using Node class

public class LinkedList<T> {
    //use necessary variables and methods
    private Node<T> head;

    public LinkedList() {
        this.head = null;
    }

    public void push(Node<T> node) {
        node.setNext(head);
        head = node;
    }

    public Node<T> pop() {
        Node<T> temp = head;
        if (head != null) {
            head = head.getNext();
            return temp;
        } else {
            return null;
        }
    }

    public Node<T> getHead() {
        return head;
    }
}
