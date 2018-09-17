package me.rorik.ubu.edat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;

/**
 * [me.rorik.ubu.edat.TestResult] Created by Roderick D. on 2018/03/01.
 *
 * @author Roderick D.
 *         <a href="https://www.rorik.me">rorik.me</a>
 *         <a href="https://www.github.com/rorik">github.com/rorik</a>
 */
@SuppressWarnings("WeakerAccess")
public class TestResult {

    /**
     * The collection of measured times.
     */
    private final List<Long> times;

    /**
     * The class which the test was ran in.
     */
    private final Class testClass;

    /**
     * Creates a new TestResult with the given measured times and the
     * class in which the test took place.
     *
     * @param times the measured times.
     *
     * @param testClass the class of the test.
     */
    public TestResult(Long[] times, Class testClass) {
        this.testClass = testClass;
        if (times.length == 0) {
            throw new IllegalArgumentException("The times array can not be empty");
        }
        this.times = Arrays.asList(times);
        Collections.sort(this.times);
    }

    /**
     * Returns the average of the measured times.
     *
     * @return the average of the measured times.
     */
    public double getAverage() {
        final OptionalDouble average = times.stream().mapToLong(e -> e).average();
        return average.isPresent() ? average.getAsDouble() : 0;
    }

    /**
     * Returns the maximum measured time.
     *
     * @return the maximum measured time.
     */
    public Long getMax() {
        return times.get(times.size() - 1);
    }

    /**
     * Returns the value situated at a given percentile.
     * Make sure that the amount of measured times is
     * a multiple of the resolution of the percentile in order
     * to ensure maximum percentile accuracy.
     *
     * @param percentile the percentile to be obtained.
     *
     * @return the value which is closer to that percentile.
     */
    public Long getPercentile(double percentile) {
        if (times.size() < 100)
            percentile -= percentile % (100 / times.size());
        return times.get((int) Math.ceil((percentile / (double) 100) * (double) times.size()));
    }

    /**
     * Returns the sum of all measured times.
     *
     * @return the sum of all measured times.
     */
    public Long getTotal() {
        return times.stream().mapToLong(e -> e).sum();
    }

    /**
     * Returns the class of the test.
     *
     * @return the class of the test.
     */
    public Class getTestClass() {
        return testClass;
    }

    /**
     * Returns the amount of times that have been measured.
     *
     * @return the amount of times that have been measured.
     */
    public int size() {
        return times.size();
    }

    /**
     * Returns a {@link String} with information about the size,
     * the total, the average, the maximum value and the 95th percentile.
     *
     * @return information about the result.
     *
     * @see #toData()
     * @see #size()
     * @see #getTotal()
     * @see #getMax()
     * @see #getPercentile(double)
     */
    @Override
    public String toString() {
        return "{size=" + size() +
                ", total=\"" + getTotal() +
                "ns\", avg=\"" + getAverage() +
                "ns\", max=\"" + getMax() +
                "ns\", p90=\"" + getPercentile(90) +
                "ns\"}";
    }

    /**
     * Returns information about the result formatted for data output.
     * Contains information about the name of the class, the total,
     * the average, the maximum value and the percentile.
     *
     * @return information about the result formatted for data output.
     *
     * @see #toString()
     * @see #getClass()
     * @see #getTotal()
     * @see #getAverage()
     * @see #getMax()
     * @see #getPercentile(double)
     */
    public String toData() {
        return testClass.getSimpleName() + '\t' + getTotal() + '\t' + getAverage() + '\t' + getMax() + '\t' + getPercentile(95);
    }
}
