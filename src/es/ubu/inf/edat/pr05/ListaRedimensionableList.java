package es.ubu.inf.edat.pr05;

import java.util.*;
import java.util.stream.Stream;

/**
 * Solución a la práctica 5 de la clase Estructuras de Datos de
 * la Universidad de Burgos, curso 2017-2018.
 * Implementado usando {@link List Listas}.
 * Puede utilizar cualquier implementación de {@link List} para
 * almacenar los datos, por defecto usa {@link ArrayList}.
 *
 * @author <a href="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz García</a>
 *
 * @param <E> El tipo de elementos de la lista.
 * @see ListaRedimensionableArray
 * @see AbstractListaRedimensionable
 */
public class ListaRedimensionableList<E> extends AbstractListaRedimensionable<E> {

    /**
     * Lista auxiliar con los elementos de la lista.
     */
    private List<E> elementos;

    /**
     * Constructor que inicializa la lista de elementos.
     *
     * @param elementos la lista sobre la cual va a trabajar esta lista.
     */
    public ListaRedimensionableList(List<E> elementos) {
        this.elementos = elementos;
    }

    /**
     * Constructor por defecto que inicializa la lista
     * auxiliar como un {@link ArrayList}.
     */
    public ListaRedimensionableList() {
        this.elementos = new ArrayList<>();
    }

    /**
     * Obtiene el elemento en la posición solicitada.
     * En caso de que no exista elemento en esa posición,
     * se generarán los {@code null} necesarios.
     *
     * Análisis complejidad algorítmica: O(n)
     *
     * @param index el índice del elemento a ser obtenido.
     *              Un numero negativo indica a la izquierda y uno positivo
     *              hacia la derecha.
     * @return el elemento que se encuentre en esa posición; o {@code null}
     *         en caso de que no hubiese elemento en esa posición.
     * @see #set(int, Object)
     * @see #add(int, Object)
     */
    @Override
    public E get(int index) {
        redimensionarSiNecesario(index);
        return elementos.get(normalizarPosicion(index));
    }

    /**
     * Devuelve el número de elementos de la lista.
     *
     * @return el número de elementos de la lista.
     */
    @Override
    public int size() {
        return elementos.size();
    }

    /**
     * Añade un elemento en una posición determinada, moviendo el resto
     * de elementos hacia cada lado. En caso de que no exista elemento en
     * esa posición, se generarán los {@code null} necesarios.
     *
     * Análisis complejidad algorítmica: O(n)
     *
     * @param index    la posición donde se colocará el elemento.
     *                 Un numero negativo indica a la izquierda y uno positivo
     *                 hacia la derecha.
     * @param elemento el elemento a ser añadido.
     * @see #add(Object)
     * @see #set(int, Object)
     */
    @Override
    public void add(int index, E elemento) {
        if (necesitaRedimensionar(index)) {
            redimensionar(index);
            elementos.set(normalizarPosicion(index), elemento);
        }
        else {
            elementos.add(index, elemento);
        }
    }

    /**
     * Elimina un elemento de la lista. En caso de que no exista
     * elemento en esa posición, se generarán los {@code null} necesarios.
     *
     * Análisis complejidad algorítmica: O(n)
     *
     * @param index la posición del elemento que se borrará.
     *              Un numero negativo indica a la izquierda y uno positivo
     *              hacia la derecha.
     * @return el elemento borrado; o {@code null}
     *         en caso de que no hubiese elemento en esa posición.
     * @see #remove(Object)
     * @see #set(int, Object)
     * @see #add(Object)
     */
    @Override
    public E remove(int index) {
        redimensionarSiNecesario(index);
        return elementos.remove(normalizarPosicion(index));
    }

    /**
     * Reemplaza un elemento con otro dado. En caso de
     * que no exista elemento en la posición dada,
     * se generarán los {@code null} necesarios.
     *
     * Análisis complejidad algorítmica: O(n)
     *
     * @param index    el índice del elemento a ser reemplazado.
     *                 Un numero negativo indica a la izquierda y uno positivo
     *                 hacia la derecha.
     * @param elemento el nuevo elemento.
     * @return el elemento reemplazado; o {@code null}
     *         en caso de que no hubiese elemento en esa posición.
     * @see #add(Object)
     * @see #add(int, Object)
     * @see #remove(int)
     */
    @Override
    public E set(int index, E elemento) {
        redimensionarSiNecesario(index);
        return elementos.set(normalizarPosicion(index), elemento);
    }

    /**
     * Comprueba si esta lista es igual a otro objeto,
     * para que ser iguales, deben ser una colección o array y los elementos
     * deben coincidir uno a uno ignorando los {@code null}.
     *
     * Análisis complejidad algorítmica: O(n)
     *
     * @param o el objeto a comprar con esta lista.
     * @return {@code true} si todos los elementos exceptuando nulos coinciden
     *         en el mismo orden; {@code false} en caso contrario o
     *         si no es una colección o array.
     * @see #trim(Object[])
     * @see #trim(Collection)
     * @see #trim(Stream)
     */
    @Override
    public boolean equals(Object o) {
        // TODO {@pr=10, @noprod} : this implementation violates the symmetric rule.
        if (o instanceof Collection) { // TODO {@ref, @noprod} : instanceof List
            return Arrays.deepEquals(trim(elementos), trim((Collection<?>) o));
        }
        else if (o != null && o.getClass().isArray()) { // TODO {@ref, @noprod} : return false
            return Arrays.deepEquals(trim(elementos), trim((Object[]) o));
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void redimensionar(final int posicion) {
        if (posicion < 0) {
            if (size() == 0) {
                elementos.add(0, null);
            }
            elementos.addAll(0, Collections.nCopies(-posicion, null));
        }
        else {
            final int desplazamiento = posicion - size() + 1;
            if (desplazamiento > 0) {
                elementos.addAll(Collections.nCopies(desplazamiento, null));
            }
        }
    }
}