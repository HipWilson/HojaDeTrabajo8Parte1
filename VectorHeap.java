// Archivo: VectorHeap.java
import java.util.Vector;

public class VectorHeap<E extends Comparable<E>> implements PriorityQueue<E> {
    protected Vector<E> data; // Vector donde almacenaremos los elementos
    
    public VectorHeap() {
        data = new Vector<E>();
    }
    
    // Retorna el índice del padre de un nodo
    protected int parent(int i) {
        return (i - 1) / 2;
    }
    
    // Retorna el índice del hijo izquierdo de un nodo
    protected int left(int i) {
        return 2 * i + 1;
    }
    
    // Retorna el índice del hijo derecho de un nodo
    protected int right(int i) {
        return 2 * i + 2;
    }
    
    // Mueve un elemento hacia arriba hasta encontrar su posición correcta
    protected void percolateUp(int leaf) {
        int parent = parent(leaf);
        E value = data.get(leaf);
        
        while (leaf > 0 && value.compareTo(data.get(parent)) < 0) {
            data.set(leaf, data.get(parent));
            leaf = parent;
            parent = parent(leaf);
        }
        
        data.set(leaf, value);
    }
    
    // Mueve un elemento hacia abajo hasta encontrar su posición correcta
    protected void pushDownRoot(int root) {
        int heapSize = data.size();
        E value = data.get(root);
        
        while (root < heapSize) {
            int childpos = left(root);
            
            if (childpos >= heapSize) {
                // No tiene hijos, termina
                break;
            }
            
            // Determina cuál es el hijo con mayor prioridad
            if (right(root) < heapSize && 
                data.get(childpos + 1).compareTo(data.get(childpos)) < 0) {
                childpos++;
            }
            
            // Si el valor tiene mayor prioridad que ambos hijos, termina
            if (value.compareTo(data.get(childpos)) <= 0) {
                break;
            }
            
            // Mueve el hijo hacia arriba
            data.set(root, data.get(childpos));
            root = childpos;
        }
        
        data.set(root, value);
    }
    
    // Agrega un valor a la cola con prioridad
    @Override
    public void add(E value) {
        data.add(value);
        percolateUp(data.size() - 1);
    }
    
    // Elimina y retorna el elemento con mayor prioridad
    @Override
    public E remove() {
        if (isEmpty()) {
            return null;
        }
        
        E minVal = data.get(0);
        E lastVal = data.remove(data.size() - 1);
        
        if (!isEmpty()) {
            data.set(0, lastVal);
            pushDownRoot(0);
        }
        
        return minVal;
    }
    
    // Retorna el elemento con mayor prioridad sin eliminarlo
    @Override
    public E getFirst() {
        if (isEmpty()) {
            return null;
        }
        return data.get(0);
    }
    
    // Verifica si la cola está vacía
    @Override
    public boolean isEmpty() {
        return data.size() == 0;
    }
    
    // Retorna el tamaño de la cola
    @Override
    public int size() {
        return data.size();
    }
    
    // Limpia la cola
    @Override
    public void clear() {
        data.clear();
    }
}