import java.util.ArrayList;
import java.util.List;

public class Pila<T> {
    private Object[] elementos;
    private int tope;

    /**
     * Crea una pila con capacidad fija.
     *
     * @param capacidad cantidad maxima de elementos
     * @throws IllegalArgumentException si la capacidad es menor o igual a cero
     */
    public Pila(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a cero.");
        }

        elementos = new Object[capacidad];
        tope = -1;
    }

    /**
     * Agrega un elemento al tope de la pila.
     *
     * @param elemento elemento a guardar
     * @throws IllegalStateException si la pila esta llena
     */
    public void push(T elemento) {
        if (size() == elementos.length) {
            throw new IllegalStateException("La pila esta llena.");
        }

        tope++;
        elementos[tope] = elemento;
    }

    /**
     * Retira y devuelve el elemento del tope.
     *
     * @return elemento retirado
     * @throws IllegalStateException si la pila esta vacia
     */
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila esta vacia.");
        }

        @SuppressWarnings("unchecked")
        T elemento = (T) elementos[tope];
        elementos[tope] = null;
        tope--;
        return elemento;
    }

    /**
     * Devuelve el elemento del tope sin retirarlo.
     *
     * @return elemento del tope
     * @throws IllegalStateException si la pila esta vacia
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila esta vacia.");
        }

        @SuppressWarnings("unchecked")
        T elemento = (T) elementos[tope];
        return elemento;
    }

    /**
     * Indica si la pila esta vacia.
     *
     * @return true si esta vacia, false en caso contrario
     */
    public boolean isEmpty() {
        return tope == -1;
    }

    /**
     * Devuelve la cantidad actual de elementos.
     *
     * @return numero de elementos guardados
     */
    public int size() {
        return tope + 1;
    }

    /**
     * Devuelve la capacidad maxima de la pila.
     *
     * @return capacidad total
     */
    public int getCapacidad() {
        return elementos.length;
    }

    /**
     * Entrega una copia de los elementos desde el tope hasta el fondo.
     *
     * @return lista con los elementos actuales
     */
    public List<T> toList() {
        List<T> copia = new ArrayList<T>();

        for (int i = tope; i >= 0; i--) {
            @SuppressWarnings("unchecked")
            T elemento = (T) elementos[i];
            copia.add(elemento);
        }

        return copia;
    }
}
