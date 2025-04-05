public interface PriorityQueue<E extends Comparable<E>> {
    public void add(E value);
    public E remove();
    public E getFirst();
    public boolean isEmpty();
    public int size();
    public void clear();
}