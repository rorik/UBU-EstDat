package es.ubu.inf.edat.pr09;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Estructura arborea que permite almacenarelementos de una forma jerárquica,
 * de tal manera que cada elemento apunta a su padre.
 *
 * @param <E> El tipo de cada nodo.
 * @author <a href="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz García</a>
 */
public class ArbolTabulado<E> extends AbstractMap<E, E> {

    /**
     * Mapa donde se almacenan los nodos,
     * la clave se corresponde con un nodo
     * y su valor con su padre.
     * El nodo raíz tendrá un valor {@code null}.
     */
    private transient final Map<E, E> mapa;

    /**
     * Constructor standard de la clase ArbolTabulado.
     */
    public ArbolTabulado() {
        super();
        mapa = new HashMap<>();
    }

    /**
     * Constructor que permite definir el taño inicial del mapa que contendrá el árbol.
     * Permite al usuario definir este dato, pero no es obligatorio para la instanciación.
     *
     * @param initSize tamaño inicial del mapa interno que contiene la estructura del árbol.
     */
    public ArbolTabulado(final int initSize) {
        super();
        mapa = new HashMap<>(initSize);
    }

    /**
     * Permite insertar un nuevo elemento en el árbol.
     * Necesita como parámetros tanto el nodo a insertar,
     * como el padre del mismo. Se considerará la raíz del árbol
     * aquel nodo cuyo padre sea null (este será único en el árbol).
     * En caso de solicitar incluir un nodo cuyo padre no está
     * incluido previamente en el árbol,
     * se lanzará la excepción IllegalArgumentException.
     * <p>
     * Complejidad algorítmica: O(n)
     *
     * @param hijo  El dato a insertar en el árbol.
     * @param padre El dato que figurará como padre del anterior en el árbol.
     * @return Devuelve el anterior padre de ese
     *         nodo en caso de estar ya incluido anteriormente o null en caso contrario.
     */
    @Override
    public E put(final E hijo, final E padre) {
        if (padre == null) { // O(1)
            if (!isEmpty() && !Objects.equals(hijo, obtenerPrimerHijo(null))) { // O(n)
                throw new IllegalArgumentException("No puede haber más de una raíz del árbol.");
            }
        } else if (padre.equals(hijo)) { // O(1)
            throw new IllegalArgumentException("Un nodo no puede tener como padre si mismo.");
        } else if (containsKey(padre)) { // O(1)
            if (esCiclo(hijo, padre)) { // O(n)
                throw new IllegalArgumentException("No se pueden realizar ciclos o parentesco recíproco.");
            }
        } else {
            throw new IllegalArgumentException("No se puede insertar un nodo con un padre que no esté en el árbol.");
        }
        return mapa.put(hijo, padre); // O(1)
    }

    /**
     * Comprueba si al recorrer el arbol desde un nodo nado se llega a la raiz.
     * <p>
     * Complejidad algorítmica: O(n)
     *
     * @param hijo  el nodo a comprobar.
     * @param padre el padre del nodo a comprobar.
     * @return {@code true} si no es capaz de llegar a la raiz (es un ciclo),
     *         {@code false} en caso contrario.
     */
    private boolean esCiclo(final E hijo, final E padre) {
        E nodoActual = padre;
        for (int restantes = size(); nodoActual != null; restantes--) { // O(n)
            if (Objects.equals(nodoActual, hijo) || restantes == 0) { // O(1)
                return true;
            }
            nodoActual = get(nodoActual); // O(1)
        }
        return false;
    }

    /**
     * Método que permite eliminar un nodo del árbol.
     * En caso de tener hijos asociados a ese elemento,
     * estos pasarán a ser hijos del nodo padre del eliminado.
     * En caso de eliminar la raíz, se escogerá una nueva
     * de entre sus nodos hijos de forma aleatoria.
     * <p>
     * Complejidad algorítmica: O(n)
     *
     * @param objeto el nodo a ser eliminado.
     * @return Devuelve el nodo padre del objeto eliminado.
     */
    @Override
    public E remove(final Object objeto) {
        if (containsKey(objeto)) { // O(1)
            E padreNuevo;
            final E padre = get(objeto); // O(1)
            if (padre == null) {
                final E raiz = obtenerPrimerHijo(null); // O(n)
                mapa.remove(raiz); // O(1)
                padreNuevo = obtenerPrimerHijo(raiz); // O(n)
                if (padreNuevo == null) {
                    return null;
                }
                mapa.put(padreNuevo, null); // O(1)
            } else {
                padreNuevo = padre;
            }

            entrySet().stream() // O(n)
                    .filter(nodo -> nodo.getValue() == objeto) // O(n)
                    .forEach(nodo -> nodo.setValue(padreNuevo)); // O(n)
            mapa.remove(objeto); // O(1)
            return padreNuevo;
        }
        return null;
    }

    /**
     * Obtiene el primer hijo del padre dado.
     * <p>
     * Complejidad algorítmica: O(n)
     *
     * @param padre el nodo del cual obtener el hijo.
     * @return el primer hijo o {@code null} en caso
     *         de que no tenga hijos o el padre no exista.
     */
    private E obtenerPrimerHijo(final E padre) {
        return entrySet().stream() // O(n)
                .filter(nodo -> nodo.getValue() == padre) // O(n)
                .findFirst() // O(1)
                .map(Entry::getKey) // O(1)
                .orElse(null); // O(1)
    }

    /**
     * Metodo que permite conocer todos los elementos almacenados
     * como descencientes del nodo pasado como parámetro.
     * Esto incluye a sus hijos directos y
     * a los elementos que se consideran hijos de éstos.
     * <p>
     * Complejidad algorítmica: O(n^2)
     *
     * @param nodo el nodo del que se quieren consultar
     *             los descendientes en la jerarquía.
     * @return listado con los datos situados por debajo
     *         de este árbol en la jerarquía.
     */
    public List<E> getDescendants(final E nodo) {
        final List<E> resultado = new ArrayList<>();
        entrySet().stream() // O(n)
                .filter(parNodo -> parNodo.getValue() == nodo) // O(n)
                .map(Entry::getKey) // O(n)
                .forEach(hijo -> { // O(n)
                    resultado.add(hijo); // O(n)
                    resultado.addAll(getDescendants(hijo)); // O(n^2)
                });
        return resultado;
    }

    /**
     * Metodo que permite conocer todos los elementos almacenados
     * como antecesores del nodo pasado como parámetro.
     * Esto incluye a sus padres directos y
     * a los elementos que se consideran padres de éstos.
     * <p>
     * Complejidad algorítmica: O(n)
     *
     * @param nodo el nodo a partir del cual
     *             se desean obtener los ancestros.
     * @return una lista con los datos ancestros al dato de consulta.
     */
    public List<E> getAncestors(final E nodo) {
        if (nodo == null) {
            return new LinkedList<>();
        }
        final List<E> resultado = new ArrayList<>();
        final E padre = get(nodo); // O(1)
        if (padre != null) {
            resultado.add(padre); // O(1)
            resultado.addAll(getAncestors(padre)); // O(n)
        }
        return resultado;
    }

    /**
     * Método que permite consultar la profundidad
     * a la que se encuentra un nodo dentro del árbol
     * (a partir de la raíz).
     * Devolverá -1 en caso de que no se encuentre contenido en el árbol.
     * <p>
     * Complejidad algorítmica: O(n)
     *
     * @param nodo el nodo sobre el cual se pretende consultar
     *             la profundidad en el arbol que lo contiene.
     * @return un entero representando la profundidad
     *         a la que se ha insertado el dato.
     */
    public int depth(final E nodo) {
        return containsKey(nodo) ? getAncestors(nodo).size() : -1;  // O(n)
    }

    /**
     * Método que permite consultar la altura a la
     * que se encuentra un nodo dentro del árbol.
     * Devolverá -1 en caso de que no se encuentre contenido en el árbol.
     * <p>
     * Complejidad algorítmica: O(n^2)
     *
     * @param nodo el nodo sobre el cual se pretende consultar
     *             la altura en el arbol que lo contiene.
     * @return un entero representando la altura
     *         a la que se ha insertado el dato.
     */
    public int height(final E nodo) {
        if (containsKey(nodo)) {
            final Set<Integer> alturasHijos = new HashSet<>();
            entrySet().stream() // O(n)
                    .filter(parNodo -> parNodo.getValue() == nodo) // O(n)
                    .map(Entry::getKey) // O(n)
                    .forEach(hijo -> alturasHijos.add(height(hijo) + 1)); // O(n^2)
            return alturasHijos.stream() // O(n)
                    .max(Comparator.naturalOrder()) // O(n)
                    .orElse(0); // O(1)
        }
        return -1;
    }

    /**
     * Permite obtener todos los nodos contenidos dentro
     * del árbol obtenidos por medio de un recorrido en anchura
     * (es decir, se devuelven ordenados en función de los niveles del árbol).
     * Dentro del mismo nivel se devolverán de izquierda a derecha.
     * <p>
     * Complejidad algorítmica: O(n^2)
     *
     * @return una lista de elementos contenidos en el árbol.
     *         Aparecen en el orden de un recorrido en anchura.
     */
    public List<E> breadthTraverse() {
        if (isEmpty()) {
            return new LinkedList<>();
        }

        final Set<E> raices = new HashSet<>();
        raices.add(obtenerPrimerHijo(null)); // O(n)

        if (raices.isEmpty()) {
            throw new IllegalStateException("El árbol no está vacio pero no tiene nodo raíz.");
        }

        return getTraverseDescendants(raices); // O(n^2)
    }

    /**
     * Obtiene los elementos en recorrido de anchura,
     * a partir de los padres (o raices) dados. Todos los ancestros
     * de estos no serán tomados en cuenta.
     * <p>
     * Complejidad algorítmica: O(n^2)
     *
     * @param padres los nodos de los cuales obtener el
     *               ecorrido de ellos mismos y sus descendientes.
     * @return una lista de elementos contenidos en el árbol.
     *         Aparecen en el orden de un recorrido en anchura,
     *         empezando por los nodos dados e ignorando los ancestros de estos.
     */
    private List<E> getTraverseDescendants(final Set<E> padres) {
        final List<E> resultado = new ArrayList<>();
        final Set<Entry<E, E>> elementos = new HashSet<>(entrySet()); // O(n)
        while (!padres.isEmpty()) { // O(n)
            elementos.removeIf(nodo -> padres.contains(nodo.getKey())); // O(n^2)
            final Set<E> hijos = elementos.stream() // O(n^2)
                    .filter(nodo -> padres.contains(nodo.getValue())) // O(n^2)
                    .map(Entry::getKey) // O(n^2)
                    .collect(Collectors.toSet()); // O(n^2)
            resultado.addAll(padres); // O(n)
            padres.clear(); // O(n)
            padres.addAll(hijos); // O(n)
        }
        return resultado;
    }

    /**
     * Devuelve el número de elementos contenidos en el árbol.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @return el número de nodos del árbol.
     */
    @Override
    public int size() {
        return mapa.size();
    }

    /**
     * Permite consultar la estructura del árbol
     * en función de su estructura interna como
     * parejas de datos y padre de ese dato.
     * <p>
     * Complejidad algorítmica: O(n)
     *
     * @return el conjunto de nodos de este árbol.
     */
    @Override
    public Set<Entry<E, E>> entrySet() {
        return mapa.entrySet();
    }

    /**
     * Obtiene el elemento padre del nodo dado.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @param nodo el nodo del cual se desea obtener su padre.
     * @return el padre del nodo.
     */
    @Override
    public E get(final Object nodo) {
        return mapa.get(nodo);
    }

    /**
     * Permite eliminar todos los nodos del árbol.
     * <p>
     * Complejidad algorítmica: O(1)
     */
    @Override
    public void clear() {
        mapa.clear();
    }

    /**
     * Calcula si el árbol está vacio (es decir, no tiene nodos) o no.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @return {@code true} en caso de que no tenga nodos;
     *         {@code false} en caso contrario.
     */
    @Override
    public boolean isEmpty() {
        return mapa.isEmpty();
    }

    /**
     * Calcula si el nodo dado tiene hijos, es decir es padre.
     * <p>
     * Complejidad algorítmica: O(n)
     *
     * @param nodo el nodo a comprobar si es padre.
     * @return {@code false} en caso de que no sea padre
     *         o no exista el nodo en el árbol;
     *         {@code true} en caso de que sea padre.
     */
    @Override
    public boolean containsValue(final Object nodo) {
        return mapa.containsValue(nodo);
    }

    /**
     * Calcula si el nodo dado existe en el árbol.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @param nodo el nodo a comprobar si existe.
     * @return {@code true} si el nodo existe en el árbol;
     *         {@code false} en caso contrario.
     */
    @Override
    public boolean containsKey(final Object nodo) {
        return mapa.containsKey(nodo);
    }

    /**
     * Devuelve un {@link Set} con todos los nodos del árbol.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @return un {@link Set} con todos los nodos del árbol.
     */
    @Override
    public Set<E> keySet() {
        return mapa.keySet();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Complejidad algorítmica: O(n)
     */
    @Override
    public Collection<E> values() {
        return mapa.values();
    }
}
