package es.ubu.inf.edat.pract00.ejerc;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PruebasUnitariasEstadisticasArray {

	/**
	 * Permite probar la clase de EstaditicasArray cuando el vector contiene un
	 * numero impar de elementos
	 */
	@Test
	public void pruebaPares(){
		
		int arrayPrueba[] = {3,6,2,8,12,57,9,25};

		EstadisticasArray estadisticas = new EstadisticasArray();
		
		estadisticas.rellenaVector(arrayPrueba);
		
		System.out.println("-- Prueba Pares --");
		System.out.println("Media Aritmetica: "+estadisticas.mediaAritmetica());
		assertEquals (13.2222, estadisticas.mediaAritmetica(), 0.001);
		
		System.out.println("Media Geometica: "+estadisticas.mediaGeometrica());
		assertEquals (1.0, estadisticas.mediaGeometrica(), 0.001);

		System.out.println("Mediana: " + estadisticas.mediana());
		assertEquals(9.0, estadisticas.mediana(), 0.001);
		
		System.out.println("Varianza: " + estadisticas.varianza());
		assertEquals(1.65277, estadisticas.varianza(), 0.0001);
		
	}

	/**
	 * Permite probar la clase de EstaditicasArray cuando el vector contiene un
	 * numero impar de elementos
	 */
	@Test
	public void pruebaImpares(){
		
		int arrayPrueba[] = {5,9,43,78,21,14,28};

		EstadisticasArray estadisticas = new EstadisticasArray();
		
		estadisticas.rellenaVector(arrayPrueba);
		
		System.out.println("-- Prueba Impares --");
		System.out.println("Media Aritmetica: "+estadisticas.mediaAritmetica());
		assertEquals (24.125, estadisticas.mediaAritmetica(), 0.01 );
		
		System.out.println("Media Geometica: "+estadisticas.mediaGeometrica());
		assertEquals (1, estadisticas.mediaGeometrica(), 0.01);

		System.out.println("Mediana: " + estadisticas.mediana());
		assertEquals(24, estadisticas.mediana(), 0.001);
		
		System.out.println("Varianza: " + estadisticas.varianza());
		assertEquals(3.44642, estadisticas.varianza(), 0.0001);
		
	
	}

}
