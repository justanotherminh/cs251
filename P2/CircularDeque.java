//implement circular deque here

public class CircularDeque {

    //create necessary variables and methods
    private int[] list;
    private int frontIndex;
    private int rearIndex;

    private void _resize() {
        if (frontIndex > rearIndex) {
            frontIndex -= list.length;
        }
        int[] newList = new int[list.length * 2];
        for (int i = frontIndex; i <= rearIndex; i++) {
            newList[i - frontIndex] = list[Math.floorMod(i, list.length)];
        }
        list = newList;
        rearIndex -= frontIndex;
        frontIndex -= frontIndex;
    }

    private void _reset() {
        list = new int[2];
        frontIndex = -1;
        rearIndex = -1;
    }

    public CircularDeque() {
        list = new int[2];
        frontIndex = -1;
        rearIndex = -1;
    }

    public void enqueueFront(int item) {
        if (frontIndex == -1 && rearIndex == -1) {
            list[0] = item;
            frontIndex = 0;
            rearIndex = 0;
            return;
        }
        if (rearIndex - frontIndex == list.length - 1 || rearIndex - frontIndex == -1) {
            _resize();
        }
        frontIndex -= 1;
        frontIndex = Math.floorMod(frontIndex, list.length);
        list[frontIndex] = item;
    }

    public void enqueueBack(int item) {
        // Always enqueue front when queue is empty
        if (frontIndex == -1 && rearIndex == -1) {
            list[0] = item;
            frontIndex = 0;
            rearIndex = 0;
            return;
        }
        if (rearIndex - frontIndex == list.length - 1 || rearIndex - frontIndex == -1) {
            _resize();
        }
        rearIndex += 1;
        rearIndex = Math.floorMod(rearIndex, list.length);
        list[rearIndex] = item;
    }

    public int dequeFront() {
        if (frontIndex == rearIndex) {
            _reset();
            return 0;
        }
        int ret = list[frontIndex];
        frontIndex += 1;
        frontIndex = Math.floorMod(frontIndex, list.length);
        return ret;
    }

    public int dequeBack() {
        if (frontIndex == rearIndex) {
            _reset();
            return 0;
        }
        int ret = list[rearIndex];
        rearIndex -= 1;
        rearIndex = Math.floorMod(rearIndex, list.length);
        return ret;
    }

    public int getArraySize() {
        return list.length;
    }

    public int getFrontIndex() {
        return frontIndex;
    }

    public int getRearIndex() {
        return rearIndex;
    }
}
