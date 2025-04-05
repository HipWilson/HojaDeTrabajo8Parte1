import java.util.ArrayList;
import java.util.NoSuchElementException;

public class VectorHeap<E extends Comparable<E>> implements PriorityQueue<E> {
    private ArrayList<E> data;

    public VectorHeap() {
        data = new ArrayList<>();
    }

    @Override
    public void add(E element) {
        data.add(element);
        percolateUp(data.size() - 1);
    }

    @Override
    public E remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        
        E min = data.get(0);
        E last = data.remove(data.size() - 1);
        
        if (!isEmpty()) {
            data.set(0, last);
            percolateDown(0);
        }
        
        return min;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return data.get(0);
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public void clear() {
        data.clear();
    }

    private void percolateUp(int leaf) {
        int parent = (leaf - 1) / 2;
        E value = data.get(leaf);
        
        while (leaf > 0 && value.compareTo(data.get(parent)) < 0) {
            data.set(leaf, data.get(parent));
            leaf = parent;
            parent = (parent - 1) / 2;
        }
        
        data.set(leaf, value);
    }

    private void percolateDown(int root) {
        int heapSize = data.size();
        E value = data.get(root);
        
        while (root < heapSize / 2) {
            int child = 2 * root + 1;
            
            if (child < heapSize - 1 && data.get(child).compareTo(data.get(child + 1)) > 0) {
                child++;
            }
            
            if (value.compareTo(data.get(child)) <= 0) {
                break;
            }
            
            data.set(root, data.get(child));
            root = child;
        }
        
        data.set(root, value);
    }
}