package es.ubu.inf.edat.pr04;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Clase para la resolución de la práctica.
 * 
 * @author <a href="mailto:rdg1003@alu.ubu.es">Rodrigo Diaz Garcia<a>
 */
public class EjerciciosListas {

	/**
	 * Compara dos listas una con otra, asegurandose de que los elementos
	 * coinciden uno a uno en orden inverso.
	 *
	 * @param lista1 primera lista a comparar.
	 * @param lista2 segunda lista a comparar.
	 * @return {@code true} si coinciden todos los elementos;
	 *		   {@code false} en caso contrario.
	 */
	public static <E> boolean sonInversas(List<E> lista1, List<E> lista2) {
		final Iterator<E> iterator1 = lista1.iterator();
		final ListIterator<E> iterator2 = lista2.listIterator(lista2.size());
		if (lista1.size() == lista2.size()) {
			for (E elemento; iterator1.hasNext();) {
				elemento = iterator1.next();
				if (elemento != iterator2.previous()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Duplica los elementos de la lista.
	 *
	 * @param lista de la cual duplicar sus elementos.
	 */
	public static <E> void duplica(List<E> lista) {
		final int tamanoInicial = lista.size();
		for (int i = 0; i < tamanoInicial; i++) {
			lista.add(i*2+1, lista.get(i*2));
		}
	}
	
	public static <E> void duplica(List<E> lista, int size) {
		final int tamanoInicial = lista.size();
		for (int i = 0; i < tamanoInicial; i++) {
			for (int j = 1; j < size; j++) {
				lista.add(i*size+j, lista.get(i*size));
			}
		}
	}
	
	public static <E> boolean esSublista(List<E> lista1, List<E> lista2) {
		int match = 0;
		Iterator<E> iteratorSublista = lista1.iterator();
		for (final ListIterator<E> iterator = lista2.listIterator(); iterator.hasNext() && iteratorSublista.hasNext();) {
			if (iterator.next() == iteratorSublista.next()) {
				if (++match == lista1.size()) {
					return true;
				}
			}
			else {
				if (match > 0) {
					iterator.previous();
					match = 0;
				}
				iteratorSublista = lista1.iterator();
			}
		}
		return false;
	}
}
