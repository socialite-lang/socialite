package socialite.tables;

import socialite.visitors.IntVisitor;

public class MyIndexPosList {
    static final int CHUNK_SIZE=8;
    static final class Node {
        int[] elems;
        int len;
        Node next;

        public Node() {
            this(CHUNK_SIZE);
        }
        public Node(int capacity) {
            elems = new int[capacity];
            len = 0;
            next = null;
        }
        public int len() {
            int l = len;
            if (l < 0) return -l;
            else return l;
        }
        public boolean isFull() {
            return len() >= elems.length;
        }

        public boolean contains(int val) {
            int l = len();
            for (int i=0; i<l; i++) {
                if (elems[i] == val)
                    return true;
            }
            return false;
        }
        public void add(int val) {
            int l = len();
            elems[l] = val;
            len = l+1;
        }
        public boolean iterate(IntVisitor v) {
            int l = len();
            boolean cont = false;
            for (int i = 0; i < l; i++) {
                int e = elems[i];                
                cont = cont | v.visit(e);
                if (!cont) break;
            }
            return cont;
        }
    }
    volatile Node head;
    volatile Node tail;
    public MyIndexPosList() {
        head = new Node();
        tail = head;
    }
    public boolean contains(int v) {
        for (Node n=head; n!=null; n=n.next) {
            if (n.contains(v))
                return true;
        }
        return false;
    }
    public void add(int val) {
        if (tail.isFull()) {
            tail.next = new Node();
            tail = tail.next;
        }
        tail.add(val);
    }
    public void iterate(IntVisitor v) {
        for (Node n=head; n!=null; n=n.next) {
            boolean cont = n.iterate(v);
            if (!cont) return;
        }
    }
}