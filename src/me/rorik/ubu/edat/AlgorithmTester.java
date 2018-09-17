package me.rorik.ubu.edat;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.Objects;

/**
 * [me.rorik.ubu.edat.AlgorithmTester] Created by Roderick D. on 2018/03/01.
 *
 *
 * @author Roderick D.
 *         <a href="https://www.rorik.me">rorik.me</a>
 *         <a href="https://www.github.com/rorik">github.com/rorik</a>
 */
@SuppressWarnings("unused")
public class AlgorithmTester {

    /**
     * Tests a given collection, generating the class for each iteration.
     *
     * @param method         the method to be tested, the returned value is ignored.
     *
     * @param classGenerator the generator of the final testing class.
     *                       Elements must be generated inside this generator.
     *
     * @param resolution     the number of iterations which this test will be repeated for,
     *                       a higher number increases result accuracy but increases total runtime.
     *                       To ensure consistency between results, always use the same resolution.
     *
     * @param <T>            the type of the class to be tested.
     *
     * @return the result of the test.
     */
    public static <T extends Collection> TestResult testCollectionGenerated(final Function<T, ?> method, final Callable<T> classGenerator, final int resolution) {
        requireResolution(resolution);
        Class testClass = null;
        final Long[] times = new Long[resolution];
        for (int i = 0; i < resolution; i++) {
            final T generatedClass;
            try {
                generatedClass = classGenerator.call();
                Objects.requireNonNull(generatedClass);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
            times[i] = applyFunctionNs(method, generatedClass);
            if (testClass == null) {
                testClass = generatedClass.iterator().next().getClass();
            }
        }
        return new TestResult(times, testClass);
    }

    /**
     * Tests a given collection, generating the elements for each iteration.
     *
     * @param method           the method to be tested, the returned value is ignored.
     *
     * @param testCollection   the collection which is tested, the elements will be overridden.
     *
     * @param elementGenerator the generator of the elements, it must return a single element,
     *                         if it throws an exception, an {@link IllegalArgumentException}
     *                         will be raised.
     *
     * @param size             the amount of elements to be tested in the array;
     *                         use a negative number to inherit from current size.
     *
     * @param resolution       the number of iterations which this test will be repeated for,
     *                         a higher number increases result accuracy but increases total runtime.
     *                         To ensure consistency between results, always use the same resolution.
     *
     * @param <E>              the type of the elements of the collection.
     *
     * @param <T>              the type of the class to be tested.
     *
     * @return the result of the test.
     */
    public static <E, T extends Collection<E>> TestResult testCollection(final Function<T, ?> method, final T testCollection, final Callable<E> elementGenerator, int size, final int resolution) {
        requireResolution(resolution);
        if (size < 0) {
            size = testCollection.size();
        }
        final Long[] times = new Long[resolution];
        for (int i = 0; i < resolution; i++) {
            testCollection.clear();
            try {
                for (int j = 0; j < size; j++) {
                    testCollection.add(elementGenerator.call());
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
            times[i] = applyFunctionMs(method, testCollection);
        }
        return new TestResult(times, testCollection.getClass());
    }

    /**
     * Tests a given collection, generating the elements for each iteration.
     *
     * @param method           the method to be tested, the returned value is ignored.
     *
     * @param testCollection   the collection which is tested, the elements will be overridden.
     *
     * @param elementGenerator the generator of the elements, it must return a single element,
     *                         if it throws an exception, an {@link IllegalArgumentException}
     *                         will be raised.
     *
     * @param size             the amount of elements to be tested in the array;
     *                         use a negative number to inherit from current size.
     *
     * @param resolution       the number of iterations which this test will be repeated for,
     *                         a higher number increases result accuracy but increases total runtime.
     *                         To ensure consistency between results, always use the same resolution.
     *
     * @param <E>              the type of the elements of the collection.
     *
     * @param <T>              the type of the class to be tested.
     *
     * @return the result of the test.
     */
    public static <E, T extends Collection<E>> TestResult testCollectionRepeated(final BiFunction<? super T, E, ?> method, final T testCollection, final Callable<? extends E> elementGenerator, int size, final int resolution, final int iterations) {
        requireResolution(resolution);
        if (size < 0) {
            size = testCollection.size();
        }
        final Long[] times = new Long[resolution];
        for (int i = 0; i < resolution; i++) {
            testCollection.clear();
            try {
                for (int j = 0; j < size; j++) {
                    testCollection.add(elementGenerator.call());
                }
                final long start = getNanoTime();
                for (int j = 0; j < iterations; j++) {
                    method.apply(testCollection, elementGenerator.call());
                }
                times[i] = getNanoTime() - start;
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        return new TestResult(times, testCollection.getClass());
    }

    /**
     * Tests a given method on a given class.
     *
     * @param method     the method to be tested, the returned value is ignored.
     *
     * @param testClass  the class which is tested.
     *
     * @param resolution the number of iterations which this test will be repeated for,
     *                   a higher number increases result accuracy but increases total runtime.
     *                   To ensure consistency between results, always use the same resolution.
     *
     * @param <T>        the type of the class to be tested.
     *
     * @return the result of the test.
     */
    public static <T> TestResult testMethod(final Function<T, ?> method, final T testClass, final int resolution) {
        requireResolution(resolution);
        final Long[] times = new Long[resolution];
        for (int i = 0; i < resolution; i++) {
            times[i] = applyFunctionMs(method, testClass);
        }
        return new TestResult(times, testClass.getClass());
    }

    /**
     * Measures the time which a given method takes to finish in milliseconds.
     *
     * @param method    the method to be measured.
     *
     * @param testClass the class whose method is measured.
     *
     * @param <T>       the type of the class whose method is measured.
     *
     * @return the time it takes the method to be completed.
     */
    private static <T> long applyFunctionMs(final Function<T, ?> method, final T testClass) {
        final long start = System.currentTimeMillis();
        method.apply(testClass);
        return System.currentTimeMillis() - start;
    }

    /**
     * Measures the time which a given method takes to finish in nanoseconds.
     *
     * @param method    the method to be measured.
     *
     * @param testClass the class whose method is measured.
     *
     * @param <T>       the type of the class whose method is measured.
     *
     * @return the time it takes the method to be completed.
     */
    private static <T> long applyFunctionNs(final Function<T, ?> method, final T testClass) {
        final long start = getNanoTime();
        method.apply(testClass);
        return getNanoTime() - start;
    }

    /**
     * Checks that a given resolution is a positive non-zero integer.
     *
     * @param resolution the given resolution.
     *
     * @throws IllegalArgumentException if the resolution is less than 1.
     */
    private static void requireResolution(int resolution) throws IllegalArgumentException {
        if (resolution < 1) {
            throw new IllegalArgumentException("The given resolution must be a positive non-zero integer (" + resolution + ')');
        }
    }

    private static long getNanoTime() {
        return System.nanoTime();
    }
}
