package es.ubu.inf.edat.examen;

import java.util.*;

public class Nuevos {

    /**
     * Este método debe juntar ambas listas en una,
     * sumando el enesimo mayor elemento del primero con
     * el enesimo menor elemento del segundo.
     * No se puede utilizar {@link java.util.Collections#sort(List)},
     * implementar utilizando {@link java.util.Queue}.
     * Ejemplo:
     * L1: [1, 9, 4, 10, 8]
     * L2: [3, 4, 0,  1, 7]
     * RT: [(10+0), (9+1), (8+3), (4+4), (1+7)]
     * [10, 10, 11, 8, 8]
     *
     * @param lista1
     * @param lista2
     * @return
     */
    public static Integer[] normalizarListas(Integer[] lista1, Integer[] lista2) {
        if (lista1.length == lista2.length) {
            final Queue<Integer> queue1 = new PriorityQueue<>(Comparator.reverseOrder());
            queue1.addAll(Arrays.asList(lista1));
            final Queue<Integer> queue2 = new PriorityQueue<>(Arrays.asList(lista2));
            final Integer[] resultado = new Integer[lista1.length];
            for (int i = 0; queue1.size() > 0; i++) {
                resultado[i] = queue1.poll() + queue2.poll();
            }
            return resultado;
        }
        throw new IllegalArgumentException("Las listas deben de ser del mismo tamaño");
    }

}
