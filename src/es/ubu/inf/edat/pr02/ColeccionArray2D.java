package es.ubu.inf.edat.pr02;

import java.util.*;

/**
 * Colección de elementos almacenados en un array bidimensional,
 * el tamaño es fijo y no puede ser cambiado una vez la colección ha sido creada.
 *
 * @author <a href="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz García</a>
 *
 * @param <E> tipo de datos de los elementos.
 * @see es.ubu.inf.edat.pr03.ColeccionArray2DUtilidades
 * @see Iterator2D
 */
public class ColeccionArray2D<E> extends AbstractCollection<E> {
	/**
	 * Elementos de la colección.
	 */
	private final E[][] elementos;

	/**
	 * Constructor de la colección.
	 *
	 * @param elementos array bidimensional con los elementos.
	 */
	public ColeccionArray2D(E[][] elementos) {
		this.elementos = elementos;
	}

	/**
	 * Crea un iterador del contenido de la colección.
	 *
	 * @return iterador generado.
	 * @see Iterator2D
	 */
	@Override
	public Iterator<E> iterator() {
		return new Iterator2D();
	}

	/**
	 * Devuelve el tamaño de la colección.
	 *
	 * @return el tamaño de la colección.
	 * @see #esPosicionValida(int)
	 * @see #ancho()
	 * @see #largo()
	 */
	@Override
	public int size() {
		return ancho() * largo();
	}

	/**
	 * Añade un elemento a la colección, esta operación no puede ser usada
	 * al no ser que se sobrescriba.
	 *
	 * @param e elemento a ser añadido.
	 * @return {@code true} si ha sido añadido;
	 * 		   {@code false} si no lo ha encontrado.
	 * @see #remove(Object)
	 * @see UnsupportedOperationException
	 */
	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Comprueba si un elemento es contenido en la colección.
	 *
	 * @param o el objeto a ser buscado.
	 * @return {@code true} si ha sido encontrado;
	 * 		   {@code false} si no lo ha encontrado.
	 * @see #get(int)
	 * @see #set(int, Object)
	 */
	@Override
	public boolean contains(Object o) {
		for (E elemento : this) {
			if (elemento == o) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Elimina el objeto dado de la colección.
	 *
	 * @param o el objeto a ser eliminado.
	 * @return {@code true} si ha sido eliminado;
	 * 		   {@code false} si no lo ha encontrado.
	 * @see #add(Object)
	 * @see Iterator2D#remove()
	 */
	@Override
	public boolean remove(Object o) {
		for (Iterator<E> iterator = iterator(); iterator.hasNext();) {
			if (iterator.next() == o) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	/**
	 * Obtiene el largo del array.
	 *
	 * @return el largo del array.
	 * @see #ancho()
	 * @see #size()
	 */
	private int largo() {
		return elementos.length;
	}

	/**
	 * Obtiene el ancho del array.
	 *
	 * @return el ancho del array.
	 * @see #largo()
	 * @see #size()
	 */
	private int ancho() {
		return elementos[0].length;
	}

	/**
	 * Reemplaza el elemento en una posición dado por otro elemento dado.
	 *
	 * @param i posición del elemento a ser reemplazado.
	 * @param e nuevo elemento.
	 * @return el elemento reemplazado, puede ser {@code null} si la
	 *		   posición no es válida o el elemento reemplazado era {@code null}.
	 * @see #get(int)
	 * @see #esPosicionValida(int)
	 */
	public E set(int i, E e) {
		if (esPosicionValida(i)) {
			final E anterior = get(i);
			elementos[i/ancho()][i%ancho()] = e;
			return anterior;
		}
		return null;
	}

	/**
	 * Obtiene el elemento con una posición dada.
	 *
	 * @param i posición del elemento buscado.
	 * @return el elemento que se encuentra el la posición dada;
	 * 		   o {@code null} en caso de que la posición no sea válida.
	 * @see #set(int, Object)
	 * @see #esPosicionValida(int)
	 */
	public E get(int i) {
		return esPosicionValida(i) ? elementos[i / ancho()][i % ancho()] : null;
	}

	/**
	 * Calcula si una posición dada está dentro de la colección.
	 *
	 * @param indice posición a ser comprobada.
	 * @return {@code true} si la posición es válida;
	 * 		   {@code false} en caso contrario.
	 * @see #get(int)
	 * @see #set(int, Object)
	 * @see es.ubu.inf.edat.pr03.ColeccionArray2DUtilidades#diferenciasHash(int)
	 */
	protected boolean esPosicionValida(int indice) {
		return indice >= 0 && indice < size();
	}

	/**
	 * Iterador de los elementos de la colección.
	 *
	 * @author <a href="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz García</a>
	 * @see ColeccionArray2D#iterator()
	 */
	private class Iterator2D implements Iterator<E>{
		/**
		 * Indicador de la posición actual.
		 */
		private int posicion = -1;

		/**
		 * Constructor vacío del iterador.
		 */
		Iterator2D() {
		}

		/**
		 * Comprueba si hay un siguiente elemento.
		 *
		 * @return {@code true} si hay un elemento siguiente;
		 * 		   {@code false} en caso contrario.
		 * @see #next()
		 * @see ColeccionArray2D#esPosicionValida(int)
		 */
		@Override
		public boolean hasNext() {
			return esPosicionValida(posicion+1);
		}

		/**
		 * Obtiene el siguiente elemento, avanza la posición.
		 *
		 * @return el siguiente elemento.
		 * @see #hasNext()
		 * @see ColeccionArray2D#get(int)
		 */
		@Override
		public E next() {
			return get(++posicion);
		}

		/**
		 * Elimina el elemento actual.
		 * @see ColeccionArray2D#remove(Object)
		 * @see ColeccionArray2D#set(int, Object)
		 */
		@Override
		public void remove() {
			set(posicion, null);
		}
	}
}
