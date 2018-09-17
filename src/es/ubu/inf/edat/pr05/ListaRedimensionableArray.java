package es.ubu.inf.edat.pr05;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * Solución a la práctica 5 de la clase Estructuras de Datos de
 * la Universidad de Burgos, curso 2017-2018.
 * Implementado usando Arrays de elementos.
 *
 * @author <a href="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz García</a>
 *
 * @param <E> El tipo de elementos de la lista.
 * @see ListaRedimensionableList
 * @see AbstractListaRedimensionable
 */
public class ListaRedimensionableArray<E> extends AbstractListaRedimensionable<E> {

    /**
     * Array de elementos de la lista.
     * Comienza con un elemento inicial el cual será remplazado.
     * @see #modificado
     * @see #obtenerElemento(int)
     */
    private Object[] elementos = new Object[1];

    /**
     * Indicador de si el elemento inicial ha sido remplazado.
     * @see #elementos
     */
    private boolean modificado = false;

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
     * @see #redimensionarSiNecesario(int)
     */
    @Override
    public E get(int index) {
        redimensionarSiNecesario(index);
        return obtenerElemento(normalizarPosicion(index));
    }

    /**
     * Devuelve el número de elementos de la lista.
     *
     * @return el número de elementos de la lista.
     */
    @Override
    public int size() {
        return modificado ? elementos.length : 0;
    }

    /**
     * Añade un elemento al final de la lista.
     *
     * Análisis complejidad algorítmica: O(n)
     *
     * @param elemento el elemento a ser añadido.
     * @return siempre devuelve {@code true}.
     * @see #add(int, Object)
     * @see #set(int, Object)
     * @see #addAll(Collection)
     * @see #redimensionarSiNecesario(int)
     */
    @Override
    public boolean add(E elemento) {
        if (modificado) {
            super.add(elemento);
        }
        else {
            add(0, elemento);
        }
        return true;
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
     * @see #redimensionarSiNecesario(int)
     */
    @Override
    public void add(int index, E elemento) {
        if (necesitaRedimensionar(index)) {
            redimensionar(index);
        }
        else if (modificado) {
            redimensionar(elementos.length);
            System.arraycopy(elementos, index, elementos, index + 1, elementos.length - index - 1);
        }
        modificado = true;
        elementos[normalizarPosicion(index)] = elemento;
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
     * @see #redimensionarSiNecesario(int)
     */
    @Override
    public E remove(int index) {
        final E anterior;
        if (elementos.length == 1 && index == 0) {
            anterior = obtenerElemento(0);
            elementos[0] = null;
            modificado = false;
        }
        else {
            final int posicionNormalizada = normalizarPosicion(index);
            redimensionarSiNecesario(index);
            anterior = obtenerElemento(posicionNormalizada);
            Object[] nuevo = new Object[modificado ? elementos.length - 1 : Math.abs(index)];
            if (modificado) {
                System.arraycopy(elementos, 0, nuevo, 0, posicionNormalizada);
                System.arraycopy(elementos, posicionNormalizada + 1, nuevo, posicionNormalizada, elementos.length - posicionNormalizada - 1);
            }
            elementos = nuevo;
            modificado = true;
        }
        return anterior;
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
     * @see #redimensionarSiNecesario(int)
     */
    @Override
    public E set(int index, E elemento) {
        redimensionarSiNecesario(index);
        final int posicionNormalizada = normalizarPosicion(index);
        final E anterior = obtenerElemento(posicionNormalizada);
        elementos[posicionNormalizada] = elemento;
        return anterior;
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
        final Object[] nuevo = new Object[posicion < 0 ? elementos.length - posicion : posicion + 1];
        if (modificado) {
            System.arraycopy(elementos, 0, nuevo, -Math.min(posicion, 0), elementos.length);
        }
        elementos = nuevo;
    }

    /**
     * Método que simplifica la transformación de Object a E,
     * utilizado para evitar el uso excesivo de supresión
     * de warnings unchecked.
     *
     * @param posicion la posición del array del elemento.
     * @return el elemento en esa posición del array.
     * @see #elementos
     */
    @SuppressWarnings("unchecked")
    private E obtenerElemento(final int posicion) {
        return (E) elementos[posicion];
    }
}