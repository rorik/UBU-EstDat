package es.ubu.inf.edat.pr03;

import static org.junit.Assert.*;
import org.junit.*;
import es.ubu.inf.edat.pr02.ColeccionArray2DTest;

public class ColeccionArray2DUtilidadesTest extends ColeccionArray2DTest {

	private ColeccionArray2DUtilidades<Integer> coleccionIntegerUtil;
	
	@Test
	public void pruebaMasFrecuente_Integer() {
	
		// Creamos un array bidimensional de enteros
		arrayInteger = new Integer[][] { { 1, 2, 3 }, { 1, 2, 3 }, { 1, 2, 3 },	{ 1, 2, 2 } };
	
		// Obtenemos una colección a partir del array bidimensional
		coleccionIntegerUtil = new ColeccionArray2DUtilidades<>(arrayInteger);
		
		assertEquals(Integer.valueOf(2), coleccionIntegerUtil.masFrecuente());
		
	}

	@Test
	public void  sumaHash() {
		
		coleccionIntegerUtil = new ColeccionArray2DUtilidades<>(arrayInteger);
		
		int suma = coleccionIntegerUtil.sumaHash();
		
		assertEquals(78,suma);
		
	}
	
	@Test
	public void diferenciasHashIndividual() {
		
		coleccionIntegerUtil = new ColeccionArray2DUtilidades<>(arrayInteger);
		int[] control = {4,3,2,1,0,1,2,3,4,5,6,7}; 
		
		int[] dif = coleccionIntegerUtil.diferenciasHash(4);
		
		assertEquals(control.length, dif.length);
		
		for(int i=0; i<control.length; i++)
			assertEquals(control[i], dif[i]);
		
	}

	@Test
	public void diferenciasHashConjunto() {
		
		coleccionIntegerUtil = new ColeccionArray2DUtilidades<>(arrayInteger);
		
		int[][] control = {
				{0,1,2,3,4,5,6,7,8,9,10,11}, // diferencia con el 1
				{1,0,1,2,3,4,5,6,7,8,9,10},  // diferencia con el 2
				{2,1,0,1,2,3,4,5,6,7,8,9},  // diferencia con el 3
				{3,2,1,0,1,2,3,4,5,6,7,8},  // diferencia con el 4
				{4,3,2,1,0,1,2,3,4,5,6,7},  // diferencia con el 5
				{5,4,3,2,1,0,1,2,3,4,5,6},  // diferencia con el 6
				{6,5,4,3,2,1,0,1,2,3,4,5},  // diferencia con el 7
				{7,6,5,4,3,2,1,0,1,2,3,4},  // diferencia con el 8
				{8,7,6,5,4,3,2,1,0,1,2,3},  // diferencia con el 9
				{9,8,7,6,5,4,3,2,1,0,1,2},  // diferencia con el 10
				{10,9,8,7,6,5,4,3,2,1,0,1},  // diferencia con el 11
				{11,10,9,8,7,6,5,4,3,2,1,0},  // diferencia con el 12
				};
		
		int[][] dif = coleccionIntegerUtil.diferenciasHash();
		
		assertEquals(control.length, dif.length);
		assertEquals(control[0].length, dif[0].length);

		for(int i=0; i<control.length; i++) {
			for(int j=0; j<control.length; j++) {
				assertEquals(control[i][j], dif[i][j]);
			}
		}

	}

	@Test
	public void busquedaSecuencial() {
		
		coleccionIntegerUtil = new ColeccionArray2DUtilidades<>(arrayInteger);
		
		int control = 10; 
		int encontrado = coleccionIntegerUtil.busquedaSecuencial(11);
		assertEquals(control, encontrado);

		control = 5; 
		encontrado = coleccionIntegerUtil.busquedaSecuencial(6);
		assertEquals(control, encontrado);

		encontrado = coleccionIntegerUtil.busquedaSecuencial(521);
		assertTrue(encontrado<0);

		
	}
	
	@Test
	public void busquedaBinaria() {
		
		coleccionIntegerUtil = new ColeccionArray2DUtilidades<>(arrayInteger);
		
		int control = 10; 
		int encontrado = coleccionIntegerUtil.busquedaBinaria(11);
		assertEquals(control, encontrado);

		control = 5; 
		encontrado = coleccionIntegerUtil.busquedaBinaria(6);
		assertEquals(control, encontrado);

		encontrado = coleccionIntegerUtil.busquedaBinaria(521);
		assertTrue(encontrado<0);

		
	}
	
	@Test
	public void busquedaBinariaNulls() {
		
		coleccionIntegerUtil = new ColeccionArray2DUtilidades<>(arrayInteger);
		coleccionIntegerUtil.set(3, null);
		
		assertEquals(10, coleccionIntegerUtil.busquedaBinaria(11));
		assertEquals(5, coleccionIntegerUtil.busquedaBinaria(6));
		assertTrue(coleccionIntegerUtil.busquedaBinaria(521)<0);
		assertEquals(0, coleccionIntegerUtil.busquedaBinaria(null));
		assertTrue(coleccionIntegerUtil.busquedaBinaria(4)<0);
	}
	
}
