package es.ubu.inf.edat.pr01;

import java.util.Iterator;

/**
 * Iterador de primos reverso.
 * @author Roderigo Díaz García
 *
 */
public class IteradorPrimos implements Iterator<Integer> {
	protected int posicion = 0;
	
	public IteradorPrimos() {}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public Integer next() {
		posicion = siguientePrimo(posicion);
		return posicion;
	}
	
	private Integer siguientePrimo(Integer inicio) {
		Integer numero = inicio;
		boolean esPrimo = false;
		do {
			if (++numero <= 2) {
				if (numero == 1)
					continue;
				break;
			}
			if (numero % 2 == 0)
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
