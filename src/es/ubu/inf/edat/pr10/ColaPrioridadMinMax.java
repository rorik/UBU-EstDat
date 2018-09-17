package es.ubu.inf.edat.pr10;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Cola de prioridad MaxMin.
 *
 * @param <E> Tipo de dato a almacenar en la cola.
 *            Si NO se facilita Comparator en el constructor,
 *            se presupone que implementará Comparable.
 * @author <a href="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz García</a>
 */
public class ColaPrioridadMinMax<E> extends AbstractQueue<E> {

    /**
     * La estructura lineal donde se almacenan los elementos.
     */
    private transient final List<E> elementos = new ArrayList<>();

    /**
     * Comparador de los elementos.
     * En caso de ser {@code null} se presupone
     * que los elementos implentan {@link Comparable}.
     */
    private transient final Comparator<E> comparador;

    /**
     * Constructor por defecto.
     * Se presupone que sólo se almacenarán objetos Comparables.
     */
    public ColaPrioridadMinMax() {
        super();
        this.comparador = null;
    }

    /**
     * Constructor con comparador.
     * Permite establecer el orden de comparación
     * que se va a emplear al organizar el contenido.
     *
     * @param comparador El objeto que implementa la interfaz {@link Comparator}.
     */
    public ColaPrioridadMinMax(final Comparator<E> comparador) {
        super();
        this.comparador = comparador;
    }

    /**
     * Calcula si la posición dada corresponde a un nivel mínimo o no.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @param posicion La posición del elemento del cual calcular el tipo de nivel.
     * @return {@code true} si se corresponde con un nivel mínimo;
     *         {@code false} en caso contrario.
     */
    private static boolean esNivelMinimo(final int posicion) {
        return ((int) (Math.log(posicion + 1) / Math.log(2))) % 2 == 0; // O(1)
    }

    /**
     * Obtiene la posición correspondiente al padre de la posición dada.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @param posicion La posición del elemento del cual cualcular su padre.
     * @return La posición del padre.
     */
    private static int getPadre(final int posicion) {
        return posicion <= 0 ? -1 : (posicion - 1) / 2; // O(1)
    }

    /**
     * Añade un elemento a la cola.
     * <p>
     * Complejidad algorítmica: O(log n) (ver {@link #bubbleUp(int)})
     *
     * @param elmento El elemento a ser añadido.
     * @return {@code true} en caso de que se haya insertado correctamente;
     *         {@code false} en caso contrario.
     */
    @Override
    public boolean offer(final E elmento) {
        elementos.add(elmento); // O(1)
        bubbleUp(elementos.size() - 1); // O(log n)
        return true;
    }

    /**
     * Método que recoloca un elemento que esté por detrás de su posición debida,
     * independientemente de que nivel sea.
     * <p>
     * Complejidad algorítmica: O(log n) (ver {@link #bubbleUp(int, boolean)})
     *
     * @param posicion La posición del elemento a recolocar.
     */
    private void bubbleUp(final int posicion) {
        final int padre = getPadre(posicion); // O(1)
        final boolean esNivelMinimo = esNivelMinimo(posicion); // O(1)
        if (padre >= 0 && compararElementos(padre, posicion, esNivelMinimo)) { // Está en nivel incorrecto. O(1)
            reemplazarElementos(posicion, padre); // O(1)
            bubbleUp(padre, !esNivelMinimo); // O(log n)
        } else { // Está en nivel correcto.
            bubbleUp(posicion, esNivelMinimo); // O(log n)
        }
    }

    /**
     * Método que recoloca un elemento que esté por detrás de su posición debida,
     * dependiendo de si es un nivel máximo (BubbleUpMax) o mínimo (BubbleUpMin).
     * Requiere que el elemento esté situado en el nivel correcto.
     * <p>
     * Complejidad algorítmica: O(log n) (recursividad tipo arbol binario)
     *
     * @param posicion La posición del elemento a recolocar.
     * @param menor    Es un nivel mínimo ({@code true}) o máximo ({@code false}).
     */
    private void bubbleUp(final int posicion, final boolean menor) {
        final int abuelo = getPadre(getPadre(posicion)); // O(1)
        if (abuelo >= 0 && compararElementos(abuelo, posicion, !menor)) { // O(1)
            reemplazarElementos(posicion, abuelo); // O(1)
            bubbleUp(posicion, menor); // O(log n)
        }
    }

    /**
     * Método que recoloca un elemento que esté por delante de su posición debida,
     * independientemente de que nivel sea.
     * <p>
     * Complejidad algorítmica: O(log n) (ver {@link #trickleDown(int, boolean)})
     *
     * @param posicion La posición del elemento a recolocar.
     */
    private void trickleDown(final int posicion) {
        trickleDown(posicion, esNivelMinimo(posicion)); // O(log n)
    }

    /**
     * Método que recoloca un elemento que esté por delante de su posición debida,
     * dependiendo de si es un nivel máximo (TrickleDownMax) o mínimo (TrickleDownMin).
     * Requiere que el elemento esté situado en el nivel correcto.
     * <p>
     * Complejidad algorítmica: O(1) (recursividad tipo arbol binario)
     *
     * @param posicion La posición del elemento a recolocar.
     * @param menor    Es un nivel mínimo ({@code true}) o máximo ({@code false}).
     */
    private void trickleDown(final int posicion, final boolean menor) {
        final int descenciente = obtenerDescenciente(posicion, menor); // O(1)
        if (descenciente >= 0) {
            if (posicion == getPadre(descenciente)) { // O(1)
                if (compararElementos(descenciente, posicion, menor)) { // O(1)
                    reemplazarElementos(posicion, descenciente); // O(1)
                }
            } else {
                if (compararElementos(descenciente, posicion, menor)) { // O(1)
                    reemplazarElementos(posicion, descenciente); // O(1)
                    final int pid = getPadre(descenciente); // O(1)
                    if (compararElementos(descenciente, pid, !menor)) { // O(1)
                        reemplazarElementos(pid, descenciente); // O(1)
                    }
                    trickleDown(descenciente, menor); // O(log n)
                }
            }
        }
    }

    /**
     * Obtiene el nieto o hijo con mayor o menor valor.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @param posicion La posición del elemento del cual obtener sus hijos y nietos.
     * @param menor    Obtener el descendiente menor ({@code true}) o mayor ({@code false}).
     * @return La posición del hijo o nieto con menor o mayor valor.
     */
    private int obtenerDescenciente(final int posicion, final boolean menor) {
        int extremo = obtenerHijoExtremo(posicion, menor); // O(1)
        if (extremo >= 0) { // Comprobar nietos.
            final int izquierdo = obtenerHijoExtremo(obtenerHijo(posicion, true), menor); // O(1)
            if (izquierdo >= 0) {
                extremo = compararElementos(izquierdo, extremo, menor) ? izquierdo : extremo; // O(1)
                final int derecho = obtenerHijoExtremo(obtenerHijo(posicion, false), menor); // O(1)
                if (derecho >= 0) {
                    extremo = compararElementos(derecho, extremo, menor) ? derecho : extremo; // O(1)
                }
            }
        }
        return extremo;
    }

    /**
     * Obtiene el hijo con mayor o menor valor.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @param posicion La posición del elemento del cual obtener sus hijos.
     * @param menor    Obtener el hijo menor ({@code true}) o mayor ({@code false}).
     * @return La posición del hijo con menor o mayor valor.
     */
    private int obtenerHijoExtremo(final int posicion, final boolean menor) {
        int extremo = -1;
        if (posicion >= 0) {
            extremo = obtenerHijo(posicion, true); // O(1)
            if (extremo >= 0) {
                final int derecho = obtenerHijo(posicion, false); // O(1)
                if (derecho >= 0) {
                    extremo = compararElementos(derecho, extremo, menor) ? derecho : extremo; // O(1)
                }
            }
        }
        return extremo;
    }

    /**
     * Obtiene el hijo según su posición (izquierda o derecha.)
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @param posicion  La posición del elemento del cual obtener sus hijos.
     * @param izquierdo Obtener el hijo situado a la izquierda ({@code true})
     *                  o a la derecha ({@code false}).
     * @return La posición del hijo izquierdo o derecho.
     */
    private int obtenerHijo(final int posicion, final boolean izquierdo) {
        final int hijo = posicion * 2 + (izquierdo ? 1 : 2); // O(1)
        return hijo < size() ? hijo : -1; // O(1)
    }

    /**
     * Compara dos elementos e indica si el primero es mayor o menor que el segundo.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @param elemento1 El primer elemento a comparar.
     * @param elemento2 El segundo elemento a comparar.
     * @param menor     Calcular si el primer elemento es menor que el segundo ({@code true}),
     *                  o mayor ({@code false}).
     * @return {@code true} si se cumple la condición solicitada; {@code false} en caso contrario.
     */
    @SuppressWarnings("unchecked")
    private boolean compararElementos(final int elemento1, final int elemento2, final boolean menor) {
        final int resultado;
        if (comparador == null) {
            if (elementos.get(elemento1) instanceof Comparable) { // O(1)
                resultado = ((Comparable<E>) elementos.get(elemento1)).compareTo(elementos.get(elemento2)); // O(1)
            } else {
                throw new IllegalStateException("No se ha suministrado ni un Comparador ni elementos Comparables.");
            }
        } else {
            resultado = comparador.compare(elementos.get(elemento1), elementos.get(elemento2)); // O(1)
        }
        return menor ? resultado < 0 : resultado > 0; // O(1)
    }

    /**
     * Cambia la posición de dos elementos entre sí,
     * de tal manera que despues el elemento2 estará
     * en la posiciónque ocupaba el elemento1 y viceversa.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @param elemento1 La posición del primer elemento a comparar.
     * @param elemento2 La posición del segundo elemento a comparar.
     */
    private void reemplazarElementos(final int elemento1, final int elemento2) {
        Collections.swap(elementos, elemento1, elemento2); // O(1)
    }

    /**
     * El método permite consultar el primer elemento
     * en orden ascendente incluido en la colección.
     * El orden dependerá de cómo se haya instanciado la clase.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @return El primer elemento en orden ascendente según el orden definido en la cola.
     */
    @Override
    public E peek() {
        return size() > 0 ? elementos.get(0) : null; // O(1)
    }

    /**
     * El método permite consultar el último elemento
     * en orden ascendente incluido en la colección.
     * El orden dependerá de cómo se haya instanciado la clase.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @return El último elemento en orden ascendente según el orden definido en la cola.
     */
    public E peekLast() {
        E resultado = null;
        if (size() > 1) {
            resultado = elementos.get(obtenerHijoExtremo(0, false)); // O(1)
        } else if (size() == 1) {
            resultado = elementos.get(0); // O(1)
        }
        return resultado;
    }

    /**
     * El método permite obtener y eliminar el primer
     * elemento en orden ascendente incluido en la colección.
     * El orden dependerá de cómo se haya instanciado la clase.
     * Tras la eliminación, se deberá reorganizar
     * el contenido del montículo para poder obtener
     * el primer elemento en futuras llamadas.
     * <p>
     * Complejidad algorítmica: O(log n) (ver {@link #trickleDown(int)})
     *
     * @return El primer elemento en orden ascendente según el orden definido en la cola.
     */
    @Override
    public E poll() {
        E resultado = null;
        if (size() > 1) {
            resultado = elementos.set(0, elementos.remove(size() - 1)); // O(1)
            trickleDown(0); // O(log n)
        } else if (size() == 1) {
            resultado = elementos.remove(0); // O(1)
        }
        return resultado;
    }

    /**
     * El método permite obtener y eliminar el último
     * elemento en orden ascendente incluido en la colección.
     * El orden dependerá de cómo se haya instanciado la clase.
     * Tras la eliminación, se deberá reorganizar
     * el contenido del montículo para poder obtener
     * el último elemento en futuras llamadas.
     * <p>
     * Complejidad algorítmica: O(log n) (ver {@link #trickleDown(int)})
     *
     * @return El último elemento en orden ascendente según el orden definido en la cola.
     */
    public E pollLast() {
        E resultado = null;
        if (size() > 1) {
            final int max = obtenerHijoExtremo(0, false); // O(1)
            if (max == size() - 1) {
                resultado = elementos.remove(max); // O(1)
            } else {
                resultado = elementos.set(max, elementos.remove(size() - 1)); // O(1)
                trickleDown(max); // O(log n)
            }
        } else if (size() == 1) {
            resultado = elementos.remove(0); // O(1)
        }
        return resultado;
    }

    /**
     * Devuelve el iterador de los elementos de la cola.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @return El {@link Iterator} de los elementos de la cola.
     */
    @Override
    public Iterator<E> iterator() {
        return elementos.iterator(); // O(1)
    }

    /**
     * Devuelve el tamaño de la cola.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @return el número de elementos de la cola.
     */
    @Override
    public int size() {
        return elementos.size(); // O(1)
    }

    /**
     * Elimina todos los elementos de la cola.
     * <p>
     * Complejidad algorítmica: O(1)
     */
    @Override
    public void clear() {
        elementos.clear(); // O(1)
    }

    /**
     * Devuelve un {@link String} con información sobre los elementos.
     * <p>
     * Complejidad algorítmica: O(n) (recorre todos los elementos)
     *
     * @return Un {@link String} con información sobre los elementos.
     */
    @Override
    public String toString() {
        return elementos.toString(); // O(n)
    }

    /**
     * Comprueba si un objeto es contenido en la cola.
     * <p>
     * Complejidad algorítmica: O(n) (recorre todos los elementos)
     *
     * @param objeto El objeto a comprobar si existe en la cola.
     */
    @Override
    public boolean contains(Object objeto) {
        return elementos.contains(objeto);
    }

    /**
     * Comprueba si todos los objetos de la colección son contenidos en la cola.
     * <p>
     * Complejidad algorítmica: O(n) (recorre todos los elementos)
     *
     * @param objetos La colección de objetos a ser comprobados.
     */
    @Override
    public boolean containsAll(Collection<?> objetos) {
        return super.containsAll(objetos);
    }
}
