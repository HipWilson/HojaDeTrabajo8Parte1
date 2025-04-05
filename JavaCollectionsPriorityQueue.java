public class JavaCollectionsPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
    private java.util.PriorityQueue<E> queue;

    public JavaCollectionsPriorityQueue() {
        queue = new java.util.PriorityQueue<>();
    }

    @Override
    public void add(E value) {
        queue.add(value);
    }

    @Override
    public E remove() {
        return queue.poll();
    }

    @Override
    public E peek() {
        return queue.peek();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}