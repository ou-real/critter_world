package edu.ou.cs.real.settings;

import java.util.Random;

/**
 * Created by Brian on 5/11/2015.
 */
public class RandomUtility {
    /**
     * Given a list of weights (any real value), get a random option out of those probabilities
     *
     * @param probabilities the list of probabilities
     * @return the index of the probability that was chosen
     */
    public static int getRandom(double[] probabilities) {
        double sum = 0;
        for (double d : probabilities) {
            sum += d;
        }

        double index = Settings.instance.getRandom().nextDouble() * sum;
        double place = 0;
        int i = 0;
        while (place < index) {
            place += probabilities[i++];
        }

        return Math.max(0, i - 1);
    }
}
