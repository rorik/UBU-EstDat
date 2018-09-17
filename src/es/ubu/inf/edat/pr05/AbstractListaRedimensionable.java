package es.ubu.inf.edat.pr05;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Clase con código común a las soluciones de la práctica 5
 * de la clase Estructuras de Datos de la Universidad de Burgos,
 * curso 2017-2018.
 *
 * @author <a href="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz García</a>
 *
 * @param <E> El tipo de elementos de la lista.
 * @see ListaRedimensionableList
 * @see ListaRedimensionableArray
 * @see ListaRedimensionable
 */
public abstract class AbstractListaRedimensionable<E> extends AbstractList<E> {

    /**
     * Convierte la posición relativa a absoluta.
     * Es decir, convierte posiciones negativas en cero.
     *
     * @param posicion la posición relativa a ser convertida.
     * @return la posición absoluta en la lista.
     * @see #get(int)
     * @see #necesitaRedimensionar(int)
     */
    protected static int normalizarPosicion(final int posicion) {
        return posicion < 0 ? 0 : posicion;
    }

    /**
     * Comprueba si la lista debe ser redimensionada para acceder
     * a la posición dada.
     *
     * @param posicion la posición a ser comprobada.
     *                 Un numero negativo indica a la izquierda y uno positivo
     *                 hacia la derecha.
     * @return {@code true} si la posición no está ya en la lista;
     *         {@code false} en caso de que si esté.
     * @see #redimensionarSiNecesario(int)
     * @see #redimensionar(int)
     */
    protected boolean necesitaRedimensionar(final int posicion) {
        return posicion < 0 || posicion >= size();
    }

    /**
     * Redimensiona la lista en caso de que tenga que ser redimensionada
     * para acceder a la posición dada.
     *
     * @param posicion la posición a ser comprobada.
     *                 Un numero negativo indica a la izquierda y uno positivo
     *                 hacia la derecha.
     * @see #necesitaRedimensionar(int)
     * @see #redimensionar(int)
     */
    protected void redimensionarSiNecesario(final int posicion) {
        if (necesitaRedimensionar(posicion)) {
            redimensionar(posicion);
        }
    }

    /**
     * Añade elementos nulos ({@code null}) para poder acceder a
     * una determinada posición de la lista.
     * Si se da una posición que no necesite ser redimensionada,
     * se producirá un efecto de truncado, eliminando los elementos
     * a la derecha de esta posición. Para evitar perdida de información,
     * no utilizar directamente este método,
     * ver {@link #redimensionarSiNecesario(int)}.
     *
     * @param posicion la posición la cual debe ser accedida,
     *                 la lista será modificada de tal manera que si esta
     *                 posición es negativa, todos los elementos serán movidos
     *                 hacia la derecha tantas veces como lo indique la posición
     *                 por método de añadir {@code null} al inicio.
     *                 Y si es positiva, añadirá elementos nulos (o eliminará
     *                 elementos existentes) al final de la lista, tantas veces
     *                 como sea necesario para que la posición sea el último elemento.
     * @see #redimensionarSiNecesario(int)
     * @see #necesitaRedimensionar(int)
     */
    protected abstract void redimensionar(int posicion);

    /**
     * Convierte la colección dada en un array sin nulos.
     *
     * @param coleccion la colección a ser convertida.
     * @return un array con los elementos no nulos de la colección.
     * @see #trim(Object[])
     * @see #trim(Stream)
     */
    protected static Object[] trim(Collection<?> coleccion) {
        return trim(coleccion.stream());
    }

    /**
     * Elimina elementos nulos de un array.
     *
     * @param elementos el array a ser filtrado.
     * @return array con los elementos no nulos del array dado.
     * @see #trim(Collection)
     * @see #trim(Stream)
     */
    protected static Object[] trim(Object[] elementos) {
        return trim(Arrays.stream(elementos));
    }

    /**
     * Convierte un stream de objetos en un array en el cual
     * todos los elementos nulos son filtrados.
     *
     * @param stream el {@link Stream} a ser convertido.
     * @return un array con los elementos no nulos del stream.
     * @see #trim(Object[])
     * @see #trim(Collection)
     */
    protected static Object[] trim(Stream<?> stream) {
        return stream.filter(Objects::nonNull).toArray();
    }
}
