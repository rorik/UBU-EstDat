/*
package me.rorik.ubu.edat.pr03;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import es.ubu.inf.edat.pr03.ColeccionArray2DUtilidades;
import me.rorik.ubu.edat.AlgorithmTester;
import me.rorik.ubu.edat.Generators;
import me.rorik.ubu.edat.TestComponent;
import me.rorik.ubu.edat.TestResult;

*/
/**
 * Wrapper for {@link AlgorithmTester} to be usable with
 * {@link ColeccionArray2DUtilidades}, tests all methods with
 * different generators and outputs the result to {@link System#out}.
 *
 * @author Roderick D.
 *         <a href="https://www.rorik.me">rorik.me</a>
 *         <a href="https://www.github.com/rorik">github.com/rorik</a>
 *
 * @see AlgorithmTester
 * @see TestComponent
 * @see TestResult
 *//*

public class ColeccionArray2DUtilidadesTester {

    */
/**
     * The amount of times each test will be run for.
     * A higher number increases accuracy, but also increases runtime.
     * The total runtime is proportional to this variable, while the
     * average and percentile are not.
     *
     * It's not recommended to use a resolution lower than 20,
     * as the percentile 95 cannot be accurately measured. The same principle
     * applies to numbers that aren't divisible by 20, albeit the loss of
     * accuracy is not as significant.
     *//*

    private static final int RESOLUTION = 200;

    */
/**
     * The default size for methods with an unspecified size (less than 1),
     * a higher number increases runtime proportional to the algorithmic
     * complexity of each method.
     *//*

    private static final int DEFAULT_SIZE = 30;

    */
/**
     * The default size for methods with an unspecified size (less than 1),
     * a higher number increases runtime proportional to the algorithmic
     * complexity of each method.
     *//*

    private static final int WARMUP_PERCENTAGE = 70;


    */
/**
     * The main method of the test.
     *
     * @param args the parameters of the program, ignored.
     *//*

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        */
/* The random element that will be used for generators and methods *//*

        final Random random = new Random();

        */
/* Initialize the list of generators *//*

        final List<Callable<Comparable>> generators = new ArrayList<>(5); {
            generators.add(() -> Generators.generateString(3, 12, random));
            generators.add(() -> Generators.generateInt(random));
            generators.add(() -> Generators.generateDouble(random));
            generators.add(() -> Generators.generateFloat(random));
            generators.add(() -> Generators.generateBoolean(random));
        }

        */
/* Initialize the list of methods *//*

        final List<TestComponent<ColeccionArray2DUtilidades, Object, Integer>> methods = new ArrayList<>(7); {
            final Integer[] precision = {20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54, 56, 58, 60};
            final Integer[] large = {20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54, 56, 58, 60, 70, 80, 90, 100, 120, 140, 160, 180, 200, 220, 240, 260, 280, 300, 340, 380, 420, 460, 500, 550, 600, 650};

            methods.add(new TestComponent<>(
                    ColeccionArray2DUtilidades::masFrecuente,
                    "masFrecuente()",
                    precision));
            methods.add(new TestComponent<>(
                    ColeccionArray2DUtilidades::quickMasFrecuente,
                    "quickMasFrecuente()",
                    large));
            methods.add(new TestComponent<>(
                    ColeccionArray2DUtilidades::sumaHash,
                    "sumaHash()",
                    large));
            methods.add(new TestComponent<>(
                    ColeccionArray2DUtilidades::diferenciasHash,
                    "diferenciasHash()",
                    precision));
            methods.add(new TestComponent<>(
                    utilidades -> utilidades.diferenciasHash(
                            random.nextInt(utilidades.size())),
                    "diferenciasHash(int)",
                    large));
            methods.add(new TestComponent<>(
                    utilidades -> utilidades.busquedaSecuencial(
                            (Comparable) utilidades.get(random.nextInt(utilidades.size()))),
                    "busquedaSecuencial(E)",
                    large));
            methods.add(new TestComponent<>(
                    utilidades -> utilidades.busquedaBinaria(
                            (Comparable) utilidades.get(random.nextInt(utilidades.size()))),
                    "busquedaBinaria(E)",
                    large));
        }

        */
/* The collection of output data *//*

        final List<String> data = new ArrayList<>(
                methods.stream().mapToInt(TestComponent::variablesSize).sum() * generators.size());

        System.out.println("-START-");
        */
/* For each method to test... *//*

        for (TestComponent testComponent : methods) {
            */
/* For each size... *//*

            {
                final int warmup = (int) testComponent.getVariables().get(testComponent.variablesSize() * WARMUP_PERCENTAGE / 100);
                System.out.println("@ WARMING UP: " + testComponent.getName() + " (" + warmup + 'x' + warmup + ')');
                AlgorithmTester.testCollectionGenerated(
                        testComponent.getFunction(),
                        () -> classGenerator(generators.get(0), warmup),
                        RESOLUTION);
            }
            for (Iterator<Integer> iterator = testComponent.getIterator(); iterator.hasNext();) {
                */
/* The size of the collection, must be at least 1, therefore,
                 * if it's lower than that, it's replaced with the default size *//*

                final int size; {
                    final int next = iterator.next();
                    size = (next > 0) ? next : DEFAULT_SIZE;
                }
                */
/* The real size of the collection *//*

                final int actualSize = size*size;
                System.out.println("> " + "Method: " + testComponent.getName() +
                        " (" + size + 'x' + size + ") <");
                System.err.println("> " + "Method: " + testComponent.getName() +
                        " (" + size + 'x' + size + ") <");
                */
/* For each generator... *//*

                for (Callable<Comparable> generator : generators) {
                    */
/* Run test *//*

                    final TestResult result = AlgorithmTester.testCollectionGenerated(
                            testComponent.getFunction(),
                            () -> classGenerator(generator, size),
                            RESOLUTION);
                    System.out.println('\t' + result.getTestClass().getSimpleName() + "\t:\t" + result.toString());
                    */
/* Add result to data *//*

                    data.add(testComponent.getName() + '\t' + size  + '\t' + actualSize + '\t' + result.toData());
                }
            }
        }

        System.out.println("-END-\n-DATA OUTPUT BEGIN-");
        */
/* For each line of data *//*

        for (final String line : data) {
            System.out.println(line);
        }
        System.out.println("-DATA OUTPUT END-");
    }

    */
/**
     * Generates a {@link ColeccionArray2DUtilidades} with elements generated by
     * a given element generator.
     *
     * @param generator the generator of the elements.
     *
     * @param size      the size of the side of the square array.
     *
     * @param <E>       the type of elements of the collection.
     *
     * @return the generated {@link ColeccionArray2DUtilidades} with the elements.
     *
     * @throws Exception if the given generator is
     *//*

    private static <E extends Comparable<? super E>> ColeccionArray2DUtilidades<E> classGenerator(final Callable<E> generator, final int size) throws Exception {
        //noinspection unchecked
        final E[][] elements = (E[][]) new Comparable[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                elements[i][j] = generator.call();
            }
        }
        return new ColeccionArray2DUtilidades<>(elements);
    }
}*/
