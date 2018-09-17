package me.rorik.ubu.edat.pr05;

import es.ubu.inf.edat.pr05.AbstractListaRedimensionable;
import es.ubu.inf.edat.pr05.ListaRedimensionableArray;
import es.ubu.inf.edat.pr05.ListaRedimensionableList;
import me.rorik.ubu.edat.AlgorithmTester;
import me.rorik.ubu.edat.Generators;
import me.rorik.ubu.edat.TestComponent;
import me.rorik.ubu.edat.TestResult;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;

/**
 * [me.rorik.ubu.edat.pr05.ListaRedimensionableTester] Created by Roderick D. on 2018/03/21.
 *
 * @author Roderick D.
 * <a href="https://www.rorik.me">rorik.me</a>
 * <a href="https://www.github.com/rorik">github.com/rorik</a>
 */
public class ListaRedimensionableTester {

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
     */
    private static final int RESOLUTION = 200;

    /**
     * The default size for methods with an unspecified size (less than 1),
     * a higher number increases runtime proportional to the algorithmic
     * complexity of each method.
     */
    private static final int DEFAULT_ITERATIONS = 30;

    /**
     * The initial amount of element in the list.
     */
    private static final int INITIAL_SIZE = 200;

    /**
     * The percentage over the maximum number of iterations of each test
     * component that will be used for the warm up process.
     * Must be a positive (or zero) integer.
     */
    private static final int WARMUP_PERCENTAGE = 70;

    /**
     * The main method of the test.
     *
     * @param args the parameters of the program, ignored.
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        /* The random element that will be used for generators and methods */
        final Random random = new Random();

        final Map<Callable<AbstractListaRedimensionable>, String> classGenerators = new LinkedHashMap<>(3); {
            classGenerators.put(ListaRedimensionableArray::new, "Arrays");
            classGenerators.put(() -> new ListaRedimensionableList(new ArrayList<>(INITIAL_SIZE)), "ArrayList");
            classGenerators.put(() -> new ListaRedimensionableList(new LinkedList<>()), "LinkedList");
        }

        /* Initialize the list of element generators */
        final List<Callable<Object>> elementGenerators = new ArrayList<>(5); {
            elementGenerators.add(() -> Generators.generateString(3, 12, random));
            elementGenerators.add(() -> Generators.generateInt(random));
            elementGenerators.add(() -> Generators.generateDouble(random));
            elementGenerators.add(() -> Generators.generateFloat(random));
            elementGenerators.add(() -> Generators.generateBoolean(random));
        }

        /* Initialize the list of methods */
        final List<TestComponent<BiFunction<AbstractListaRedimensionable, Object, Object>, Integer>> methods = new ArrayList<>(13); {
            final Integer[] iterations = {20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40,
                    42, 44, 46, 48, 50, 52, 54, 56, 58, 60, 70, 80, 90, 100,
                    120, 140, 160, 180, 200, 220, 240, 260, 280, 300, 340, 380, 420, 460, 500, 550, 600, 650, 700, 800};

            methods.add(new TestComponent<>(
                    AbstractList::add,
                    "add(E)\t",
                    iterations));
            methods.add(new TestComponent<>(
                    (AbstractListaRedimensionable list, Object object) -> {
                        list.add(random.nextInt(100), object);
                        return null;
                    },
                    "add(int, E)\t0 <= value < 100",
                    iterations));
            methods.add(new TestComponent<>(
                    (AbstractListaRedimensionable list, Object object) -> {
                        list.add(random.nextInt(100) + list.size(), object);
                        return null;
                    },
                    "add(int, E)\tsize() <= value < size() + 100",
                    iterations));
            methods.add(new TestComponent<>(
                    (AbstractListaRedimensionable list, Object object) -> {
                        list.add(-random.nextInt(100)-1, object);
                        return null;
                    },
                    "add(int, E)\t-100 <= value < 0",
                    iterations));
            methods.add(new TestComponent<>(
                    (AbstractListaRedimensionable list, Object object) -> {
                        list.set(Math.max(0, random.nextInt(list.size()+1)-1), object);
                        return null;
                    },
                    "set(int, E)\t0 <= value < size()",
                    iterations));
            methods.add(new TestComponent<>(
                    (AbstractListaRedimensionable list, Object object) -> {
                        list.set(random.nextInt(100) + list.size(), object);
                        return null;
                    },
                    "set(int, E)\tsize() <= value < size() + 100",
                    iterations));
            methods.add(new TestComponent<>(
                    (AbstractListaRedimensionable list, Object object) -> {
                        list.set(-random.nextInt(100)-1, object);
                        return null;
                    },
                    "set(int, E)\t-100 <= value < 0",
                    iterations));
            methods.add(new TestComponent<>(
                    (AbstractListaRedimensionable list, Object object) ->
                            list.get(Math.max(0, random.nextInt(list.size()+1)-1)),
                    "get(int)\t0 <= value < size()",
                    iterations));
            methods.add(new TestComponent<>(
                    (AbstractListaRedimensionable list, Object object) ->
                            list.get(random.nextInt(100) + list.size()),
                    "get(int)\tsize() <= value < size() + 100",
                    iterations));
            methods.add(new TestComponent<>(
                    (AbstractListaRedimensionable list, Object object) ->
                            list.get(-random.nextInt(100)-1),
                    "get(int)\t-100 <= value < 0",
                    iterations));
            methods.add(new TestComponent<>(
                    (AbstractListaRedimensionable list, Object object) ->
                            list.remove(Math.max(0, random.nextInt(list.size()+1)-1)),
                    "remove(int)\t0 <= value < size()",
                    iterations));
            methods.add(new TestComponent<>(
                    (AbstractListaRedimensionable list, Object object) ->
                            list.remove(random.nextInt(100) + list.size()),
                    "remove(int)\tsize() <= value < size() + 100",
                    iterations));
            methods.add(new TestComponent<>(
                    (AbstractListaRedimensionable list, Object object) ->
                            list.remove(-random.nextInt(100)-1),
                    "remove(int)\t-100 <= value < 0",
                    iterations));
        }

        /* The collection of output data */
        final List<String> data = new ArrayList<>(
                methods.stream().mapToInt(TestComponent::variablesSize).sum() * elementGenerators.size());
        try {
            System.out.println("-START-");
            /* For each class to test... */
            for (Callable<AbstractListaRedimensionable> listGenerator : classGenerators.keySet()) {
                final String name = classGenerators.get(listGenerator);
                /* For each method to test... */
                for (TestComponent<BiFunction<AbstractListaRedimensionable, Object, Object>, Integer> testComponent : methods) {
                    /* For each size... */
                    {
                        final int warmup = Collections.max(testComponent.getVariables()) * WARMUP_PERCENTAGE / 100;
                        System.out.println("@ WARMING UP: " + testComponent.getName() + " (x" + warmup + ')');
                        AlgorithmTester.testCollectionRepeated(
                                testComponent.getFunction(),
                                listGenerator.call(),
                                elementGenerators.get(0),
                                INITIAL_SIZE,
                                RESOLUTION,
                                warmup);
                    }
                    for (Iterator<Integer> iterator = testComponent.getIterator(); iterator.hasNext(); ) {
                        /* The size of the collection, must be at least 1, therefore,
                         * if it's lower than that, it's replaced with the default size */
                        final int iterations;
                        {
                            final int next = iterator.next();
                            iterations = (next > 0) ? next : DEFAULT_ITERATIONS;
                        }
                        /* The real size of the collection */
                        final int actualSize = iterations * iterations;
                        System.out.println("> " + "List: " + name + " Method: " + testComponent.getName() +
                                " (x" + iterations + ") <");
                        System.err.println("> " + "List: " + name + " Method: " + testComponent.getName() +
                                " (x" + iterations + ") <");
                        /* For each generator... */
                        for (Callable<?> generator : elementGenerators) {
                            /* Run test */
                            final TestResult result = AlgorithmTester.testCollectionRepeated(
                                    testComponent.getFunction(),
                                    listGenerator.call(),
                                    generator,
                                    INITIAL_SIZE,
                                    RESOLUTION,
                                    iterations);
                            System.out.println('\t' + name + '\t' + generator.call().getClass().getSimpleName() + "\t:\t" + result.toString());
                            /* Add result to data */
                            data.add(name + '\t' + testComponent.getName() + '\t' + iterations + '\t' + actualSize + '\t' + result.toData());
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            outputData(true, data);
            throw e;
        }

        outputData(false, data);
    }

    private static void outputData(boolean error, List<String> data) {
        if (error) {
            System.err.println("-END-\n-DATA OUTPUT BEGIN [UNFINISHED]-");
        }
        else {
            System.out.println("-END-\n-DATA OUTPUT BEGIN-");
        }

        for (final String line : data) {
            System.out.println(line);
        }

        if (error) {
            System.err.println("-DATA OUTPUT END-");
        }
        else {
            System.out.println("-DATA OUTPUT END-");
        }
    }
}
