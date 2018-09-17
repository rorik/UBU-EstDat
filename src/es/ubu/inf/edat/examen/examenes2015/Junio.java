package es.ubu.inf.edat.examen.examenes2015;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * [es.ubu.inf.edat.examen.examenes2015.Junio] Created by Roderick D. on 2018/06/10.
 *
 * @author Roderick D.
 *         <a href="https://www.rorik.me">rorik.me</a>
 *         <a href="https://www.github.com/rorik">github.com/rorik</a>
 */
public class Junio {

    public static class GestorRutas {

        private Map<String, String> mapa;

        public GestorRutas() {
            mapa = new HashMap<>();
        }

        public GestorRutas(int capacidadInicial, float factorCarga) {
            mapa = new HashMap<>(capacidadInicial, factorCarga);
        }

        public void insercionTramos(String[] paradas) {

        }

    }

    /**
     * Se considera que se está trabajando sobre una clase
     * que implementa un árbol genérico, en el que cada nodo
     * que lo compone puede tener un número indeterminado de hijos.
     * <p>
     * Se pide añadir dos métodos a la clase:
     * - public int depth(E elemento)
     * Que dado un posible elemento contenido en el árbol,
     * devuelve la profundidad a la que se encuentra éste.
     * En caso de que el árbol no contenga el elemento,
     * el valor retornado será -1.
     * - public List<E> nodosHoja()
     * Que deberá devolver un listado de aquellos elementos alojados
     * en nodos considerados como hojas del árbol.
     * Es decir, que se encuentran alojados en aquellos nodos que
     * no tienen asignado ningún descendiente.
     * <p>
     * Ambos métodos se pueden resolver tanto de manera iterativa como recursiva.
     *
     * @param <E> El tipo de datos de cada nodo almacenado en el árbol.
     */
    public static class MiArbol<E> {
        private Nodo<E> raiz;
        private int size;

        public int depth(E elemento) {
            return depth(busqueda(raiz, elemento));
        }

        private Nodo<E> busqueda(Nodo<E> nodo, E elemento) {
            if (nodo.dato.equals(elemento)) {
                return nodo;
            }
            for (Nodo<E> hijo : nodo.hijos) {
                Nodo<E> busquedaRecursiva = busqueda(hijo, elemento);
                if (busquedaRecursiva != null) {
                    return busquedaRecursiva;
                }
            }
            return null;
        }

        private int depth(Nodo<E> nodo) {
            if (nodo == null) {
                return -1;
            }
            int max = 0;
            for (Nodo<E> hijo : nodo.hijos) {
                max = Math.max(depth(hijo) + 1, max);
            }
            return max;
        }

        public List<E> nodosHoja() {
            List<E> hojas = new ArrayList<>();
            nodosHoja(hojas, raiz);
            return hojas;
        }

        private void nodosHoja(List<E> lista, Nodo<E> nodo) {
            if (nodo.hijos.isEmpty()) {
                lista.add(nodo.dato);
            } else {
                for (Nodo<E> hijo : nodo.hijos) {
                    nodosHoja(lista, hijo);
                }
            }
        }

        public void setRaiz(Nodo<E> raiz) {
            this.raiz = raiz;
        }

        public static class Nodo<E> {
            protected E dato;
            protected List<Nodo<E>> hijos = new ArrayList<>();

            public Nodo(E dato) {
                this.dato = dato;
            }

            public void addHijo(Nodo<E> hijo) {
                hijos.add(hijo);
            }
        }
    }

    /**
     * Se solicita crear una clase de almacenamiento temporal para
     * un programa que resuelve el problema del productor-consumidor.
     * En este problema se pueden generar un número limitado de productos,
     * pero puede haber un número indeterminado de copias de estos productos.
     * Para ahorrar espacio en memoria, se ha decido almacenar
     * estos productos en una clase contenedora como la descrita en el cuadro.
     * Para la solución del problema se pueden emplear instnacias de cualquier
     * clase que implemente el intefaz Queue como auxiliares.
     * <p>
     * El método E siguienteProducto() devolverá una copia de aquel producto
     * del que haya menos copias almacenadas en el gestor en ese momento.
     * Se deberán seguir devolviendo copias de ese mismo producto
     * hasta que no queden más, tras lo que se devolverán las copias
     * del siguiente producto con menos copias y  así  sucesivamente.
     * En caso de no tener más productos restantes, se devolverá null.
     *
     * @param <E> El tipo de productos.
     */
    public static class GestorProductos<E> {

        private Queue<ProductoDuplicados> cola = new PriorityQueue<>(new ProductoDuplicadosComparator());

        public E siguienteProducto() {
            final ProductoDuplicados siguiente = cola.peek();
            if (siguiente != null) {
                if (--siguiente.copias <= 0) {
                    cola.poll();
                }
                return siguiente.producto;
            }
            return null;
        }

        public int addProducto(E producto, int copias) { // No requerido
            if (producto == null || copias <= 0) {
                return -1;
            }
            ProductoDuplicados elemento = cola.stream().filter(p -> p.producto.equals(producto)).findFirst().orElse(null);
            if (elemento == null) {
                elemento = new ProductoDuplicados(producto, copias);
                cola.add(elemento);
            } else {
                elemento.copias += copias;
            }
            return elemento.copias;
        }

        private class ProductoDuplicados {
            public E producto;
            public int copias;

            public ProductoDuplicados(E producto, int copias) {
                this.producto = producto;
                this.copias = copias;
            }
        }

        private class ProductoDuplicadosComparator implements Comparator<ProductoDuplicados> {
            @Override
            public int compare(ProductoDuplicados o1, ProductoDuplicados o2) {
                return o1.copias - o2.copias;
            }
        }
    }

    /**
     * Una tienda online almacena un log muy básico de las compras realizadas
     * por los clientes en las distintas secciones de la misma.
     * El log es un conjunto de objetos con la información de cada una
     * de las entradas y queremos que se encuentre ordenada temporalmente.
     * Queremos ordenar los objetos basándonos en el atributo "fechaYHora" de la compra.
     * Se pide:<p>
     * - Modificar CompraProducto para que implemente Comparable.
     * - Crear un método de nombre "usuariosRetornan", que devuelva una lista con
     * los "idCliente" de los usuarios que han realizado más de una compra.
     * - Crear un método de nombre "comprasCercanas", que retorna un set de las
     * compras que se han efectuado con fecha próxima a la de una que se pasa por referencia.
     * El método recibe la compra de referencia y el radio (por delante y por detrás).
     * <p>
     * <b>!!! NOTA DE AUTOR !!!</b>
     * El método comprasCercanas es demasiado aumbiguo, ¿el radio se refiere a dias o milisegundos?
     * En caso de que sean días, ¿naturales o por diferencia de 24h?.
     * Esta implementación toma en cuenta el radio como dias naturales (es decir, la diferencia entre
     * 2018/01/01 00:00:000 y 2018/01/02 23:59:999 es 1).
     */
    public static class ContabilidadAccesos {
        SortedSet<CompraProducto> logCompras = new TreeSet<>();

        public List<String> usuariosRetornan() {
            final HashMap<String, Integer> mapa = new HashMap<>();
            for (CompraProducto compra : logCompras) {
                Integer total = mapa.get(compra.idCliente);
                total = (total == null ? 1 : total + 1);
                mapa.put(compra.idCliente, total);
            }
            final List<String> usuariosResultado = new ArrayList<>();
            for (Map.Entry<String, Integer> entrada : mapa.entrySet()) {
                if (entrada.getValue() > 1) {
                    usuariosResultado.add(entrada.getKey());
                }
            }
            return usuariosResultado;
        }

        public Set<CompraProducto> comprasCercanas(CompraProducto referencia, int radio) {
            final Set<CompraProducto> resultado = new HashSet<>();
            for (CompraProducto compra : logCompras) {
                if (Math.abs(compareDias(compra, referencia)) <= radio) {
                    resultado.add(compra);
                }
            }
            return resultado;
        }

        private int compareDias(CompraProducto c1, CompraProducto c2) {
            return (int) (c1.fechaYHora.getTime() / 86400000) - (int) c2.fechaYHora.getTime() / 86400000;
        }

        public boolean addCompra(CompraProducto compraProducto) { // No requerido
            return compraProducto != null && logCompras.add(compraProducto);
        }

        public Iterator<CompraProducto> iterator() { // No requerido
            return logCompras.iterator();
        }

        public static class CompraProducto implements Comparable<CompraProducto> {
            private String idCliente;
            private Date fechaYHora;
            private Seccion seccion;
            private double importe;

            public CompraProducto(String idCliente, Seccion seccion, Date fechaYHora, double importe) {
                this.idCliente = idCliente;
                this.seccion = seccion;
                this.fechaYHora = fechaYHora;
                this.importe = importe;
            }

            @Override
            public int compareTo(CompraProducto otro) {
                if (otro == null) {
                    throw new IllegalArgumentException();
                }
                return fechaYHora.compareTo(otro.fechaYHora);
            }

            public double getImporte() { // No requerido
                return importe;
            }

            public enum Seccion {Casa, Deportes, Moda, Motor, Salud, Telefonia}
        }
    }
}
