package es.ubu.inf.edat.pr05;

/**
 * Clase vacía que hereda de uno de los tipos de ListaRedimensiable
 * creados para la práctica 5 de la clase Estructuras de Datos de
 * la Universidad de Burgos, curso 2017-2018.
 * La razón de que esté vacía es que he creado dos soluciones distintas,
 * una es por medio de listas {@link ListaRedimensionableList}, y otra
 * con arrays {@link ListaRedimensionableArray}.
 *
 * @author <a href="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz García</a>
 *
 * @param <E> El tipo de elementos de la lista.
 * @see ListaRedimensionableArray
 * @see ListaRedimensionableList
 */
public class ListaRedimensionable<E> extends ListaRedimensionableArray<E> {}