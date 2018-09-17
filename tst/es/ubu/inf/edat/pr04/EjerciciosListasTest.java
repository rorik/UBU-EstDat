/**
 * Programa que prueba los m�todos de la clase {@code EjerciciosListas}.
 * 
 * Al ejecutarse con las aserciones habilitadas (opci�n -ea o -enableassertions
 * de la m�quina virtual), no deber�a saltar ninguna.
 */

package es.ubu.inf.edat.pr04;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import es.ubu.inf.edat.pr04.EjerciciosListas;

/**
 * Clase que prueba los métodos de la clase {@code EjerciciosListas}.
 * 
 * @author <a href="mailto:jjrodriguez@ubu.es">Juan José Rodríguez Diez</a>
 * @author <a href="mailto:bbaruque@ubu.es">Bruno Baruque Zanón</a>
 * @author <a href="mailto:jamcendon@ubu.es">Jose Antonio Martín Cendón</a>
 * 
 */
public class EjerciciosListasTest {

	/**
	 * Función que prueba {@code EjerciciosListas.sonInversas}
	 */
	@Test
	public void pruebaSonInversas() {

		// Creamos algunas EjerciciosListas
		List<Integer> l1 = Arrays.asList(1, 2, 3);
		List<Integer> l2 = Arrays.asList(3, 2, 1);
		List<Integer> l3 = Arrays.asList(1, 2, 3, 2, 1);
		List<Integer> l4 = Arrays.asList(1, 2, 1, 2, 1);

		// Casos en los que deberá devolver cierto
		assertTrue (EjerciciosListas.sonInversas(l1, l2));
		assertTrue (EjerciciosListas.sonInversas(l2, l1));
		assertTrue (EjerciciosListas.sonInversas(l3, l3));
		assertTrue (EjerciciosListas.sonInversas(l4, l4));

		// Casos en los que deberá devolver falso
		assertFalse (EjerciciosListas.sonInversas(l1, l1));
		assertFalse (EjerciciosListas.sonInversas(l2, l2));
		assertFalse (EjerciciosListas.sonInversas(l3, l4));
		assertFalse (EjerciciosListas.sonInversas(l4, l3));
	}

	/**
	 * Función que prueba {@code EjerciciosListas.duplica}
	 */
	@Test
	public void pruebaDuplica() {
		List<Integer> listInt = new LinkedList<Integer>(Arrays.asList(1, 2, 3));
		EjerciciosListas.duplica(listInt);
		assertEquals(Arrays.asList(1, 1, 2, 2, 3, 3), listInt);
	}

	/**
	 * Función que prueba {@code EjerciciosListas.esSubLista}
	 */
	@Test
	public void pruebaEsSublista() {

		// Creamos algunas EjerciciosListas
		List<Integer> l1 = Arrays.asList(1, 2, 3);
		List<Integer> l2 = Arrays.asList(-1, 0, 1, 2, 3, 4, 5);
		List<Integer> l3 = Arrays.asList(1, 1, 2, 1, 2, 2, 1, 1, 2, 3, 4);
		List<Integer> l4 = Arrays.asList(1, 2, 1, 1, 2);

		// Toda lista deberá ser sublista de si misma
		assertTrue (EjerciciosListas.esSublista(l1, l1));
		assertTrue (EjerciciosListas.esSublista(l2, l2));
		assertTrue (EjerciciosListas.esSublista(l3, l3));
		assertTrue (EjerciciosListas.esSublista(l4, l4));

		// Casos en los que sí son sublistas
		assertTrue (EjerciciosListas.esSublista(l1, l2));
		assertTrue (EjerciciosListas.esSublista(l1, l3));

		// Casos en los que noson sublistas
		assertFalse (EjerciciosListas.esSublista(l1, l4));
		assertFalse (EjerciciosListas.esSublista(l2, l1));
		assertFalse (EjerciciosListas.esSublista(l2, l3));
		assertFalse (EjerciciosListas.esSublista(l2, l4));
		assertFalse (EjerciciosListas.esSublista(l3, l1));
		assertFalse (EjerciciosListas.esSublista(l3, l2));
		assertFalse (EjerciciosListas.esSublista(l3, l4));
		assertFalse (EjerciciosListas.esSublista(l4, l1));
		assertFalse (EjerciciosListas.esSublista(l4, l2));
		assertFalse (EjerciciosListas.esSublista(l4, l3));
	}

	/**
	 * Función que prueba {@code EjerciciosListas.duplica}
	 */
	@Test
	public void pruebaDuplicaNumRepet() {
		
		List<Integer> listInt = new LinkedList<Integer>(Arrays.asList(1, 2, 3));
		
		EjerciciosListas.duplica(listInt,2);
		assertEquals(Arrays.asList(1, 1, 2, 2, 3, 3), listInt);
		
		listInt = new LinkedList<Integer>(Arrays.asList(1, 2, 3));
		EjerciciosListas.duplica(listInt,3);
		assertEquals(Arrays.asList(1, 1, 1, 2, 2, 2, 3, 3, 3), listInt);

		listInt = new LinkedList<Integer>(Arrays.asList(1, 2, 3));
		EjerciciosListas.duplica(listInt,6);
		assertEquals(Arrays.asList(1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3), listInt);

	}

	
}
