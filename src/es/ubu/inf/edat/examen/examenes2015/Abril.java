package es.ubu.inf.edat.examen.examenes2015;

import java.util.Iterator;

/**
 * [es.ubu.inf.edat.examen.examenes2015.Abril] Created by Roderick D. on 2018/06/10.
 *
 * @author Roderick D.
 *         <a href="https://www.rorik.me">rorik.me</a>
 *         <a href="https://www.github.com/rorik">github.com/rorik</a>
 */
public class Abril {
    /**
     * 30/04/2015 - 1. Iteradores<p>
     * Una de las sucesiones denominadas “especiales”
     * es la que está formada por los números triangulares.
     * Esta sucesión se genera partiendo del patrón de puntos
     * que forma un triángulo.<p>
     * <a href="https://i.imgur.com/eS2LIAT.png">Representación.</a><p>
     * Disponiendo del código siguiente, complételo e incluya un Iterador
     * que permita recorrer la serie formada por todos los posibles valores
     * que sean inferiores al contenido en la variable “limiteSuperior".
     */
    public static class NumerosTriangulares implements Iterable<Integer> {
        private int limiteSuperior;

        public NumerosTriangulares(int limiteSuperior) {
            this.limiteSuperior = limiteSuperior;
        }

        @Override
        public Iterator<Integer> iterator() {
            return new NumerosTriangularesIterator();
        }

        private class NumerosTriangularesIterator implements Iterator<Integer> {

            private int posicion = 0;
            private int valor = 0;

            @Override
            public boolean hasNext() {
                return valor + posicion < limiteSuperior;
            }

            @Override
            public Integer next() {
                return valor += ++posicion;
            }
        }
    }
}
