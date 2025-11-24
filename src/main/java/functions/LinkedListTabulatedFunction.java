package functions;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable {

    static class Node {
        Node next;
        Node prev;
        double x;
        double y;
        Node(double x, double y) { this.x = x; this.y = y; }
    }

    private Node head; // null when empty
    private int count;

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues == null || yValues == null) throw new IllegalArgumentException("Null arrays");
        if (xValues.length != yValues.length) throw new IllegalArgumentException("Lengths mismatch");
        if (xValues.length == 0) throw new IllegalArgumentException("Empty arrays");

        for (int i = 1; i < xValues.length; i++) {
            if (xValues[i] <= xValues[i - 1]) throw new IllegalArgumentException("x must be strictly increasing");
        }
        for (int i = 0; i < xValues.length; i++) addNode(xValues[i], yValues[i]);
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (source == null) throw new IllegalArgumentException("source null");
        if (count <= 0) throw new IllegalArgumentException("count <= 0");
        if (xFrom > xTo) { double t = xFrom; xFrom = xTo; xTo = t; }
        for (int i = 0; i < count; i++) {
            double x = (count == 1) ? xFrom : xFrom + (xTo - xFrom) * i / (count - 1);
            addNode(x, source.apply(x));
        }
    }

    private void addNode(double x, double y) {
        Node node = new Node(x, y);
        if (head == null) {
            head = node;
            head.next = head.prev = head;
        } else {
            Node last = head.prev;
            last.next = node;
            node.prev = last;
            node.next = head;
            head.prev = node;
        }
        count++;
    }

    @Override
    public int getCount() { return count; }

    private Node getNode(int index) {
        if (index < 0 || index >= count) throw new IndexOutOfBoundsException("index=" + index);
        Node cur;
        if (index <= count / 2) {
            cur = head;
            for (int i = 0; i < index; i++) cur = cur.next;
            return cur;
        } else {
            cur = head.prev;
            for (int i = count - 1; i > index; i--) cur = cur.prev;
            return cur;
        }
    }

    @Override
    public double getX(int index) { return getNode(index).x; }

    @Override
    public double getY(int index) { return getNode(index).y; }

    @Override
    public void setY(int index, double value) { getNode(index).y = value; }

    @Override
    public int indexOfX(double x) {
        if (head == null) return -1;
        Node cur = head;
        for (int i = 0; i < count; i++) {
            if (Double.compare(cur.x, x) == 0) return i;
            cur = cur.next;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        if (head == null) return -1;
        Node cur = head;
        for (int i = 0; i < count; i++) {
            if (Double.compare(cur.y, y) == 0) return i;
            cur = cur.next;
        }
        return -1;
    }

    @Override
    public double leftBound() {
        if (head == null) throw new IllegalStateException("empty");
        return head.x;
    }

    @Override
    public double rightBound() {
        if (head == null) throw new IllegalStateException("empty");
        return head.prev.x;
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (head == null) return 0;
        if (x <= head.x) return 0;
        if (x >= head.prev.x) return count;
        Node cur = head;
        for (int i = 0; i < count - 1; i++) {
            if (cur.x < x && cur.next.x >= x) return i;
            cur = cur.next;
        }
        return count - 1;
    }

    @Override
    protected double extrapolateLeft(double x) {
        if (count == 1) return head.y;
        Node a = head;
        Node b = head.next;
        return interpolate(x, a.x, b.x, a.y, b.y);
    }

    @Override
    protected double extrapolateRight(double x) {
        if (count == 1) return head.y;
        Node b = head.prev;
        Node a = b.prev;
        return interpolate(x, a.x, b.x, a.y, b.y);
    }

    @Override
    public void insert(double x, double y) {
        if (head == null) { addNode(x, y); return; }
        int idx = indexOfX(x);
        if (idx != -1) { setY(idx, y); return; }

        Node cur = head;
        int pos = 0;
        while (pos < count && cur.x < x) { cur = cur.next; pos++; }
        Node node = new Node(x, y);
        Node prev = cur.prev;
        prev.next = node;
        node.prev = prev;
        node.next = cur;
        cur.prev = node;
        if (pos == 0) head = node;
        count++;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= count) throw new IndexOutOfBoundsException("index=" + index);
        if (count == 1) { head = null; count = 0; return; }
        Node node = getNode(index);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        if (node == head) head = node.next;
        count--;
    }
}
