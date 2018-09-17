package es.ubu.lsi.utils;

public class EjemploGeneradorEnteros {

	public static void main(String[] args) {

		for(int i = 100; i<1000; i=i+100) {
			
			Integer[][] matrix = GeneradorEnteros.matrizAleatoria(i);
			System.out.println("filas:"+matrix.length+" columnas: "+matrix[0].length);
			
		}
		

	}

}
