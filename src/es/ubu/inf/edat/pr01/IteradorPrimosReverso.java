package es.ubu.inf.edat.pr01;

import java.util.NoSuchElementException;

/**
 * Iterador de primos reverso.
 * @author Roderigo Díaz García
 *
 */
public class IteradorPrimosReverso extends IteradorPrimos {
	
	public IteradorPrimosReverso(Integer inicio) {
		posicion = inicio.intValue();
		previous();
	}
	public boolean hasPrevious() {
		return posicion >= 2;
	}
	
	public Integer previous() {
		Integer primo = posicion;
		if (hasPrevious())
			posicion = anteriorPrimo(posicion);
		else
			throw new NoSuchElementException();
		return primo;
	}
	
	private Integer anteriorPrimo(Integer ultimo) {
		Integer numero = ultimo;
		boolean esPrimo = false;
		do {
			if (--numero % 2 == 0)
				continue;
			esPrimo = true;
			int maximo = (int) Math.sqrt(numero);
			for (int i = 2; i <= maximo; i++) {
				if (numero % i == 0) {
					esPrimo = false;
					break;
				}
			}
		} while (!esPrimo);
		return numero;
	}
	
}
