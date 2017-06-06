import java.util.ArrayList;
import java.util.List;

public class BinomialHeap<K extends Comparable<K>, D> {
    public BinomialHeapNode<K, D> head;

    public BinomialHeap() {
        head = null;
    }

    public BinomialHeap(BinomialHeapNode<K, D> head) {
        this.head = head;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = null;
    }

    public void insert(BinomialHeapNode<K, D> node) {
        BinomialHeap<K, D> tempHeap = new BinomialHeap<K, D>(node);
        head = union(tempHeap);
    }
    
    public void insert(K key, D data) {
        BinomialHeapNode<K, D> node = new BinomialHeapNode<K, D>(key, data);
        BinomialHeap<K, D> tempHeap = new BinomialHeap<K, D>(node);
        head = union(tempHeap);
    }

    public BinomialHeapNode<K, D> findMinimum() {
        if (head == null) {
            return null;
        } else {
            BinomialHeapNode<K, D> min = head;
            BinomialHeapNode<K, D> next = min.sibling;

            while (next != null) {
                if (next.compareTo(min) < 0) {
                    min = next;
                }
                next = next.sibling;
            }

            return min;
        }
    }

    // Implemented to test delete/decrease key, runs in O(n) time
    public BinomialHeapNode<K, D> search(K key) {
        List<BinomialHeapNode<K, D>> nodes = new ArrayList<BinomialHeapNode<K, D>>();
        nodes.add(head);
        while (!nodes.isEmpty()) {
            BinomialHeapNode<K, D> curr = nodes.get(0);
            nodes.remove(0);
            if (curr.key.equals(key)) {
                return curr;
            }
            if (curr.sibling != null) {
                nodes.add(curr.sibling);
            }
            if (curr.child != null) {
                nodes.add(curr.child);
            }
        }
        return null;
    }

    public void decreaseKey(BinomialHeapNode<K, D> node, K newKey) {
        node.key = newKey;
        bubbleUp(node, false);
    }

    public void delete(BinomialHeapNode<K, D> node) {
        node = bubbleUp(node, true);
        if (head == node) {
            removeTreeRoot(node, null);
        } else {
            BinomialHeapNode<K, D> prev = head;
            while (prev.sibling.compareTo(node) != 0) {
                prev = prev.sibling;
            }
            removeTreeRoot(node, prev);
        }
    }

    private BinomialHeapNode<K, D> bubbleUp(BinomialHeapNode<K, D> node, boolean toRoot) {
        BinomialHeapNode<K, D> parent = node.parent;
        while (parent != null && (toRoot || node.compareTo(parent) < 0)) {
            K temp = node.key;
            node.key = parent.key;
            parent.key = temp;
            node = parent;
            parent = parent.parent;
        }
        return node;
    }

    public BinomialHeapNode<K, D> extractMin() {
        if (head == null) {
            return null;
        }

        BinomialHeapNode<K, D> min = head;
        BinomialHeapNode<K, D> minPrev = null;
        BinomialHeapNode<K, D> next = min.sibling;
        BinomialHeapNode<K, D> nextPrev = min;

        while (next != null) {
            if (next.compareTo(min) < 0) {
                min = next;
                minPrev = nextPrev;
            }
            nextPrev = next;
            next = next.sibling;
        }

        removeTreeRoot(min, minPrev);
        return min;
    }

    private void removeTreeRoot(BinomialHeapNode<K, D> root, BinomialHeapNode<K, D> prev) {
        // Remove root from the heap
        if (root == head) {
            head = root.sibling;
        } else {
            prev.sibling = root.sibling;
        }

        // Reverse the order of root's children and make a new heap
        BinomialHeapNode<K, D> newHead = null;
        BinomialHeapNode<K, D> child = root.child;
        while (child != null) {
            BinomialHeapNode<K, D> next = child.sibling;
            child.sibling = newHead;
            child.parent = null;
            newHead = child;
            child = next;
        }
        BinomialHeap<K, D> newHeap = new BinomialHeap<K, D>(newHead);

        // Union the heaps and set its head as this.head
        head = union(newHeap);
    }

    // Merge two binomial trees of the same order
    private void linkTree(BinomialHeapNode<K, D> minNodeTree, BinomialHeapNode<K, D> other) {
        other.parent = minNodeTree;
        other.sibling = minNodeTree.child;
        minNodeTree.child = other;
        minNodeTree.degree++;
    }

    // Union two binomial heaps into one and return the head
    public BinomialHeapNode<K, D> union(BinomialHeap<K, D> heap) {
        BinomialHeapNode<K, D> newHead = merge(this, heap);

        head = null;
        heap.head = null;

        if (newHead == null) {
            return null;
        }

        BinomialHeapNode<K, D> prev = null;
        BinomialHeapNode<K, D> curr = newHead;
        BinomialHeapNode<K, D> next = newHead.sibling;

        while (next != null) {
            if (curr.degree != next.degree || (next.sibling != null &&
                    next.sibling.degree == curr.degree)) {
                prev = curr;
                curr = next;
            } else {
                if (curr.compareTo(next) < 0) {
                    curr.sibling = next.sibling;
                    linkTree(curr, next);
                } else {
                    if (prev == null) {
                        newHead = next;
                    } else {
                        prev.sibling = next;
                    }

                    linkTree(next, curr);
                    curr = next;
               }
            }

            next = curr.sibling;
        }

        return newHead;
    }

    private BinomialHeapNode<K, D> merge(
            BinomialHeap<K, D> heap1, BinomialHeap<K, D> heap2) {
        if (heap1.head == null) {
            return heap2.head;
        } else if (heap2.head == null) {
            return heap1.head;
        } else {
            BinomialHeapNode<K, D> head;
            BinomialHeapNode<K, D> heap1Next = heap1.head;
            BinomialHeapNode<K, D> heap2Next = heap2.head;

            if (heap1.head.degree <= heap2.head.degree) {
                head = heap1.head;
                heap1Next = heap1Next.sibling;
            } else {
                head = heap2.head;
                heap2Next = heap2Next.sibling;
            }

            BinomialHeapNode<K, D> tail = head;

            while (heap1Next != null && heap2Next != null) {
                if (heap1Next.degree <= heap2Next.degree) {
                    tail.sibling = heap1Next;
                    heap1Next = heap1Next.sibling;
                } else {
                    tail.sibling = heap2Next;
                    heap2Next = heap2Next.sibling;
                }

                tail = tail.sibling;
            }

            if (heap1Next != null) {
                tail.sibling = heap1Next;
            } else {
                tail.sibling = heap2Next;
            }

            return head;
        }
    }

    public void print() {
        System.out.println("Binomial heap:");
        if (head != null) {
            head.print(0);
        }
    }
}