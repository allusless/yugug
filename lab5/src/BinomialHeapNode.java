public class BinomialHeapNode<K extends Comparable<K>, D>{
        public K key;
        public D data;
        public int degree;
        public BinomialHeapNode<K, D> parent;
        public BinomialHeapNode<K, D> child;
        public BinomialHeapNode<K, D> sibling;

        public BinomialHeapNode() {
            key = null;
        }

        /* Function to get size */
        public int getSize() 
        {
            return (1 + ((child == null) ? 0 : child.getSize()) + ((sibling == null) ? 0 : sibling.getSize()));
        }
        
        public BinomialHeapNode(K key, D data) {
            this.key = key;
            this.data = data;
        }

        public int compareTo(BinomialHeapNode<K, D> other) {
            return this.key.compareTo(other.key);
        }

        public void print(int level) {
            BinomialHeapNode<K, D> curr = this;
            while (curr != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < level; i++) {
                    sb.append(" ");
                }
                sb.append(curr.key.toString());
                System.out.println(sb.toString());
                if (curr.child != null) {
                    curr.child.print(level + 1);
                }
                curr = curr.sibling;
            }
        }
    }