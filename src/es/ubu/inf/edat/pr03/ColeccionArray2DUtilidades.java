package es.ubu.inf.edat.pr03;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.ubu.inf.edat.pr02.ColeccionArray2D;

/**
 * Esta clase extiende la clase ColeccionArray2D, implementada en prácticas anteriores.
 * Por lo tanto, deberá seguir implementando el funcionamiento de la clase ya implementada (herencia), 
 * incluyendo varios otros comportamientos nuevos, pensados como utilidades de consulta sobre la
 * estructura de datos en 2D.  
 *
 * @author <a href="mailto:bbaruque@ubu.es">Bruno Baruque Zanón</a>
 * @author <a href="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz García</a>
 *
 * @param <E> Tipo de datos contenidos en el array 2D que debe ser {@link Comparable}.
 * @see ColeccionArray2D
 * @see Comparable
 */
public class ColeccionArray2DUtilidades<E extends Comparable<? super E>> extends ColeccionArray2D<E> {

	/**
	 * Constructor de la colección de datos.
	 *
	 * @param elementos elementos para el constructor de la colección.
	 * @see ColeccionArray2D#ColeccionArray2D(Object[][])
	 * @see Comparable
	 */
	public ColeccionArray2DUtilidades(E[][] elementos) {
		super(elementos);
	}

	/**
	 * Este método permite localizar el elemento más frecuente en la colección.
	 * Es decir el elemento que aparece más veces repetido en el contenido de la misma.
	 *
	 * Análisis de complejidad algorítmica: O(n^2)
	 * Podemos ver que consiste de una operación O(1) encapsulada dentro de
	 * un bucle for O(n) y este encapsulado en otro bucle for O(n).
	 * Por lo tanto, la complejidad será O(1*n*n) = O(n^2).
	 *
	 * @return Elemento con mayor frecuencia en el contenido de la colección.
	 * @see #quickMasFrecuente()
	 */
	public E masFrecuente() {
		E maximoElemento = null;
		int maximoOcurrencias = 0;
		for (final E elemento : this) {
			int ocurrencias = 0;
			for (final E elemento2 : this) {
				if (elemento2 == elemento) {
					ocurrencias++;
				}
			}
			if (ocurrencias > maximoOcurrencias) {
				maximoOcurrencias = ocurrencias;
				maximoElemento = elemento;
			}
		}
		return maximoElemento;
	}
	
	/**
	 * Este método permite localizar el elemento más frecuente en la colección.
	 * Es decir el elemento que aparece más veces repetido en el contenido de la misma.
	 * Utiliza un algoritmo más eficiente que {@link #masFrecuente()}
	 * utilizando un {@link HashMap}.
	 *
	 * Análisis de complejidad algorítmica: O(n^2)
	 * De manera similar a {@link #masFrecuente()} consiste de un for dentro de un for.
	 * La mayor diferencia es que en lugar de ser una iteración n*n es n*m,
	 * siendo m un número menor o igual a n.
	 * A pesar de que solo haya un for en el código, la función {@link HashMap#get(Object)}
	 * es implementada por el uso de un for.
	 *
	 * @return Elemento con mayor frecuencia en el contenido de la colección.
	 * @see #masFrecuente()
	 */
	public E quickMasFrecuente() {
		final Map<E, Integer> ocurrencias = new HashMap<>(size()/2);
		E maximoElemento = null;
		int maximoActual = 0;
		for (final E elemento : this) {
			Integer cuentaActual = ocurrencias.get(elemento);
			if (cuentaActual == null) {
				cuentaActual = 0;
			}
			ocurrencias.put(elemento, ++cuentaActual);
			if (cuentaActual > maximoActual) {
				maximoActual = cuentaActual;
				maximoElemento = elemento;
			}
		}
		return maximoElemento;
	}

	/**
	 * Todos los objetos en Java tienen asignado un código
	 * individual y único a lo largo de la ejecución de un programa.
	 * Esto permite identificar unívocamente cada objeto
	 * y realizar organizaciones y busquedas más eficientes de los datos.
	 * Veremos estas más adelante en la asignatura.
	 *
	 * El código mencionado se llama Hash Code y se puede
	 * obtener de cualquier objeto con la llamada:
	 * nombreObjeto.hashCode();
	 * Este método devuelve un numero entero (único) para cada objeto.
	 *
	 * Se pide que este método devuelva el sumatorio de los hashCode
	 * de todos los objetos que almacena en su interior.
	 *
	 * Análisis de complejidad algorítmica: O(n) * O(E.hashCode()) = O(n)
	 * Este método depende del coste de E.hashCode(),
	 * para clases como {@link Integer}, {@link Boolean},
	 * o {@link Double}; el coste e O(1). Pero para
	 * {@link String}, el primer uso de {@link String#hashCode()}
	 * tiene un coste de O(n).
	 * Por lo tanto el coste total es de O(E.hashCode()) * O(n), que para
	 * {@link String} es O(n^2) y para casi todos los demás O(n).
	 *
	 * @return sumatorio de los códigos hash de todos los elementos contenidos en la colección
	 * @see Object#hashCode()
	 */
	public int sumaHash() {
		int suma = 0;
		for (final E elemento : this) {
			if (elemento != null) {
				suma += elemento.hashCode();
			}
		}
		return suma;
	}

	/**
	 * En este metodo se solicita comprobar la diferencia del valor
	 * del codigo hash de uno de los elementos contenidos en la colección
	 * con el resto de elementos que aparecen en la misma.
	 *
	 * Si consideramos que el coligo hash está relacionado con el contenido
	 * de cada objeto, esto nos permitiría conocer "como de similar"
	 * es el objeto seleccionado con el resto de los contenidos.
	 *
	 * Análisis de complejidad algorítmica: O(n) * O(E.hashCode()) = O(n)
	 * La complejidad es similar a la anterior {@link #sumaHash()} por los mismos motivos,
	 * depende de la complejidad de E.hashCode().
	 * Al estar E.hashCode() dentro de un for, el coste
	 * total es O(E.hashCode()) * O(n).
	 *
	 * @param posicion que ocupa el elemento que se quiere comprobar,
	 * 				   en una iteración secuencial en la colección
	 * @return array de enteros que incluye la diferencia del código hash del elemento
	 * 				 que ocupa esa posición en una iteración secuencial,
	 * 				 con el código hash del elemento elegido como referencia.
	 * @throws IllegalArgumentException en caso de que la posición de referencia
	 * 									quede fuera de los márgenes de posiciones
	 *									disponibles en la colección.
	 * @see #diferenciasHash()
	 * @see #sumaHash()
	 */
	public int[] diferenciasHash(int posicion) {
		if (!esPosicionValida(posicion)) {
			throw new IllegalArgumentException("La posición indicada no está dentro de la colección");
		}
		final int hashCodePosicion = get(posicion).hashCode();
		final int[] diferencia = new int[size()];
		int posicionActual = 0;
		for (Iterator<E> iterator = iterator(); iterator.hasNext(); posicionActual++) {
			final E elemento = iterator.next();
			diferencia[posicionActual] = hashCodePosicion;
			if (elemento != null) {
				diferencia[posicionActual] = Math.abs(diferencia[posicionActual] - elemento.hashCode());
			}
		}
		return diferencia;
	}
	
	/**
	 * Devuelve un array con la diferencia de hashes con posición rodante.
	 * Es decir, el primer array contiene la diferencia con la posición 0,
	 * el segundo con posición 1, ..., y ultimo con posición {@link #size()}-1.
	 * El tamaño de cada array viene dado por el tamaño de la colección.
	 *
	 * Análisis de complejidad algorítmica: O(n) * O(diferenciasHash(int)) = O(n^2)
	 * Por el mismo motivo que {@link #diferenciasHash(int)}, la complejidad depende de
	 * E.hashCode().
	 * Al llamar a {@link #diferenciasHash(int)} dentro de un for, el coste
	 * total es O(diferenciasHash(int)) * O(n) = O(E.hashCode()) * O(n^2)
	 *
	 * @return array de diferencias de hashes con posición rodante.
	 * @see #diferenciasHash(int)
	 */
	public int[][] diferenciasHash() {
		final int[][] diferencias = new int[size()][size()];
		for (int i = 0; i < size(); i++) {
			diferencias[i] = diferenciasHash(i);
		}
		return diferencias;
	}

	/**
	 * Método que devuelve la posición, en una iteración secuencial,
	 * en la que aparecería el elemento que se pasa como parámetro.
	 * Si el elemento se encuentra en la primera posición de la iteración,
	 * el valor a devolver es 0, de forma análoga a las posiciones de un array. 
	 * En caso de que el elemento no se encuentre en la colección,
	 * se devolverá un número menor que 0 para indicarlo.
	 *
	 * En este caso se solicita al alumno que realice esta búsqueda de forma secuencial.
	 * Es decir, recorriendo el contenido hasta localizar el elemento.
	 *
	 * Análisis de complejidad algorítmica: O(n)
	 * Una operación O(1) dentro de un bucle for O(n),
	 * coste total: O(n*1) = O(n).
	 *
	 * @param buscado Elemento que se solicita buscar en la colección.
	 * @return posición en la que aparecerá el elemento en una iteración
	 * 		   o número negativo si no se encuentra en la colección.
	 * @see #busquedaBinaria(Comparable)
	 */
	public int busquedaSecuencial(E buscado) {
		int posicion = 0;
		for (final Iterator<E> iterator = iterator(); iterator.hasNext(); posicion++) {
			if (iterator.next() == buscado) {
				return posicion;
			}
		}
		return -1;
	}
		
	/**
	 * Método que devuelve la posición, en una iteración secuencial,
	 * en la que aparecería el elemento que se pasa como parámetro.
	 * Si el elemento se encuentra en la primera posición de la iteración, el valor
	 * a devolver es 0, de forma análoga a las posiciones de un array. 
	 * En caso de que el elemento no se encuentre en la colección,
	 * se devolverá un número menor que 0 para indicarlo.
	 * Si un elemento de la colección es nulo, es trasladado al inicio.
	 * Si un elemento se repite, no se puede garantizar que posición devolverá.
	 * 
	 * En este caso se solicita al alumno que realice esta búsqueda empleando el método de 
	 * <a href=https://es.wikipedia.org/wiki/B%C3%BAsqueda_binaria> búsqueda binaria</a>. 
	 * Para ello, el alumno deberá:
	 * 1. Realizar la ordenación del contenido.
	 * Se puede almacenar en un array o lista nueva y ordenar ésta.
	 * Ver método: Arrays.sort()
	 * 2. Realizar la búsqueda binaria:
	 * Comprobar los elementos de los extremos y el centro, seleccionar el subconjunto
	 * en el que se debe continuar la búsqueda y repetir.
	 *
	 * Análisis de complejidad algorítmica: O(n^2) o O(n^2 * log n) (?)
	 * Calcular la complejidad de este método es complicada,
	 * analizando el código, debería ser la operación más costosa, que es
	 * {@link List#sort(Comparator)} con un coste de O(n log n). El bucle while
	 * tiene una complejidad de O(log n).
	 * Pero analizando los datos obtenidos, se ajusta más a a una curva O(n^2) o
	 * incluso O(n^2 log n).
	 *
	 * @param buscado Elemento que se solicita buscar en la colección.
	 * @return Posicion en la que aparecerá el elemento en una iteración
	 * 		   o número negativo si no se encuentra en la colección.
	 * @see #busquedaSecuencial(Comparable)
	 */
	public int busquedaBinaria(E buscado) {
		final List<E> ordenados = new ArrayList<>(size());
		iterator().forEachRemaining(ordenados::add);
		ordenados.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
		if (buscado == null) {
			return ordenados.get(0) == null ? 0 : -1;
		}
		int inferior = 0;
		int superior = size() - 1;
		while (inferior <= superior) {
			final int medio = (inferior + superior) / 2;
			final E elementoMedio = ordenados.get(medio);
			final int comparacion = elementoMedio == null ? 1 : elementoMedio.compareTo(buscado);
			if (comparacion > 0) {
				superior = medio - 1;
			}
			else if (comparacion < 0) {
				inferior = medio + 1;
			}
			else {
				return medio;
			}
		}
		return -1;
	}
}
