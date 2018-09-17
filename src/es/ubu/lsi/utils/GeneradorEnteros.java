package es.ubu.lsi.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.Random;

public class GeneradorEnteros {

	static Random rand = new Random();
	
	/**
	 * Genera numeros aleatorios entre -100 y 100. Incluye numeros negativos.
	 * 
	 * @param tamano
	 * @return
	 */
	public static List<Integer> listaAleatoria (int tamano){
		
		Integer[] aleatoria = new Integer[tamano];
		
		for(int i=0; i<tamano; i++){
			
			int aleat = rand.nextInt(tamano);
			if(rand.nextBoolean())
				aleat = -1 * aleat;
			
			aleatoria[i] = aleat;
			
		}
		
		return Arrays.asList(aleatoria);
		
	}

	/**
	 * Genera numeros aleatorios entre -100 y 100. Incluye numeros negativos.
	 * 
	 * @param tamano
	 * @return
	 */

	public static int[] arrayAleatorio(int tamano){
		
		List<Integer> lista = listaAleatoria (tamano);
		int[] aleatoria = new int[tamano];
		
		for(int i=0; i<tamano;i++){
			aleatoria[i] = lista.get(i);
		}
		
		return aleatoria;
		
	}
	
	public static Integer[][] matrizAleatoria (int numElementos){
	
		int[] arrayAleatorios;
	
		int filas = (int) Math.floor(Math.sqrt(numElementos));
	
		int numCalc = (int) Math.pow(filas, 2);
		arrayAleatorios = GeneradorEnteros.arrayAleatorio(numCalc);
		Integer[] boxed = IntStream.of(arrayAleatorios).boxed().toArray( Integer[]::new );
		return monoToBidi(boxed, filas, filas);
		
	}

	public static <E> String toString (int[] array){
		String s = "[" + array[0];
		for(int i=1; i<array.length; i++){
			s = s + ", " + array[i];
		}
		s = s + "]";
		return s;
	}

	private static Integer[][] monoToBidi( final Integer[] array, final int rows, final int cols ) {
		if (array.length != (rows*cols))
			throw new IllegalArgumentException("Invalid array length");
	
		Integer[][] bidi = new Integer[rows][cols];
		for ( int i = 0; i < rows; i++ )
			System.arraycopy(array, (i*cols), bidi[i], 0, cols);
	
		return bidi;
	}
	
}
