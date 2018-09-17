package me.rorik.ubu.edat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A wrapper around a function to be tested, it's identifying name
 * and an additional list of variables.
 *
 * @param <T> the function to be tested.
 *
 * @param <E> the type of the variables.
 *
 * @author Roderick D.
 *         <a href="https://www.rorik.me">rorik.me</a>
 *         <a href="https://www.github.com/rorik">github.com/rorik</a>
 */
@SuppressWarnings("unused")
public class TestComponent<T, E> {

    /**
     * The function of the test.
     */
    private final T function;

    /**
     * The identifying name of the function.
     */
    private String name;

    /**
     * A list with the additional variables.
     */
    private final List<E> variables;

    /**
     * Creates a TestComponent with the function, an identifying name, and variables.
     *
     * @param function the function of the test.
     *
     * @param name the identifying name of the function.
     *
     * @param variables the varargs with the additional variable arguments.
     */
    @SafeVarargs
    public TestComponent(final T function, final String name, final E... variables) {
        this.function = function;
        this.name = name;
        this.variables = new ArrayList<>(variables.length);
        this.variables.addAll(Arrays.asList(variables));
    }
    /**
     *
     * Creates a TestComponent with the function and variables.
     *
     * @param function the function of the test.
     *
     * @param variables the varargs with the additional variable arguments.
     */
    @SafeVarargs
    public TestComponent(final T function, final E... variables) {
        this.function = function;
        this.variables = new ArrayList<>(variables.length);
    }

    /**
     * Creates a TestComponent with the function and an identifying name.
     *
     * @param function the function of the test.
     *
     * @param name the identifying name of the function.
     */
    public TestComponent(final T function, final String name) {
        this.function = function;
        this.name = name;
        variables = new ArrayList<>(1);
    }

    /**
     * Creates a TestComponent with the function.
     *
     * @param function the function of the test.
     */
    public TestComponent(final T function) {
        this.function = function;
        variables = new ArrayList<>(1);
    }

    /**
     * Returns the function.
     *
     * @return the function.
     */
    public T getFunction() {
        return function;
    }

    /**
     * Returns the identifier.
     *
     * @return the name of the function.
     */
    public String getName() {
        return name;
    }

    /**
     * Replaces the identifier.
     *
     * @param name the new name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns an iterator of the additional variables.
     *
     * @return an iterator of the additional variables.
     */
    public Iterator<E> getIterator() {
        return variables.iterator();
    }

    /**
     * Returns the list of the additional variables.
     *
     * @return the list of the additional variables.
     */
    public List<E> getVariables() {
        return variables;
    }

    /**
     * Returns the size of the list of the additional variables.
     *
     * @return the size of the list of the additional variables.
     */
    public int variablesSize() {
        return variables.size();
    }

    /**
     * Adds variables.
     *
     * @param variables the varargs of the variables ot be added.
     */
    @SafeVarargs
    public final void addVariable(final E... variables) {
        this.variables.addAll(Arrays.asList(variables));
    }
}