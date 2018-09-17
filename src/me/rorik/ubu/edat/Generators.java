package me.rorik.ubu.edat;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Different types of generators.
 *
 * @author Roderick D.
 *         <a href="https://www.rorik.me">rorik.me</a>
 *         <a href="https://www.github.com/rorik">github.com/rorik</a>
 *
 * @see Random
 * @see String
 * @see Integer
 * @see Double
 * @see Float
 * @see Boolean
 */
public class Generators {
    /**
     * The values that each character of the string generator can take.
     */
    private static final String CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * Generate a {@link String} with a given minimum and maximum length.
     *
     * @param minimum the minimum length that the string can be.
     *
     * @param maximum the maximum length that the string can be.
     *
     * @param random  the random generator.
     *
     * @return the generated string.
     *
     * @throws IllegalArgumentException if the given range is not valid
     *                                  (minimum less than 0 or maximum less than minimum).
     *
     * @see #CHARSET
     */
    public static String generateString(int minimum, int maximum, Random random) {
        if (minimum < 0 || maximum < minimum) {
            throw new IllegalArgumentException("Incorrect length range (" + minimum + " - " + maximum + ')');
        }
        return IntStream.range(0, random.nextInt(maximum - minimum) + minimum)
                .mapToObj(i -> String.valueOf(CHARSET.charAt(random.nextInt(CHARSET.length()))))
                .collect(Collectors.joining());
    }

    /**
     * Generates an int that is between a given range.
     *
     * @param minimum the minimum value that the number can take.
     *
     * @param maximum the maximum value that the number can take.
     *
     * @param random  the random generator.
     *
     * @return the generated int.
     *
     * @see #generateInt(Random)
     * @see #generateDouble(Random)
     * @see #generateFloat(Random)
     */
    public static int generateInt(int minimum, int maximum, Random random) {
        return random.nextInt(maximum - minimum) + minimum;
    }

    /**
     * Generates an {@link Integer} without a range.
     *
     * @param random the random generator.
     *
     * @return the generated int.
     *
     * @see #generateInt(int, int, Random)
     * @see #generateDouble(Random)
     * @see #generateFloat(Random)
     */
    public static int generateInt(Random random) {
        return random.nextInt();
    }

    /**
     * Generates a {@link Double}.
     *
     * @param random the random generator.
     *
     * @return the generated double.
     *
     * @see #generateInt(Random)
     * @see #generateFloat(Random)
     */
    public static double generateDouble(Random random) {
        return random.nextDouble();
    }

    /**
     * Generates a {@link Float}.
     *
     * @param random the random generator.
     *
     * @return the generated float.
     *
     * @see #generateInt(Random)
     * @see #generateDouble(Random)
     */
    public static float generateFloat(Random random) {
        return random.nextFloat();
    }

    /**
     * Generates a {@link Boolean}.
     *
     * @param random the random generator.
     *
     * @return the generated boolean.
     */
    public static boolean generateBoolean(Random random) {
        return random.nextBoolean();
    }
}
