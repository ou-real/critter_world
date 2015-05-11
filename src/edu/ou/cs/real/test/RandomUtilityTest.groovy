package edu.ou.cs.real.test

import edu.ou.cs.real.settings.RandomUtility
import edu.ou.cs.real.settings.Settings

/**
 * Created by Brian on 5/11/2015.
 */
public class RandomUtilityTest extends GroovyTestCase {
    double[] build(double... doubles) {
        return doubles;
    }

    void testGetRandom() {
        Settings.instance = new Settings();

        double[] array = build(1, 0, 0, 0);
        assert RandomUtility.getRandom(array) == 0;

        array = build(0, 1, 0, 0);
        assert RandomUtility.getRandom(array) == 1;

        array = build(0, 0, 1, 0);
        assert RandomUtility.getRandom(array) == 2;

        array = build(0, 0, 0, 1);
        assert RandomUtility.getRandom(array) == 3;

        array = build(50, 25, 10, 15);
        double[] indices = new double[4];
        for (int i = 0; i < 100; i++) {
            indices[RandomUtility.getRandom(array)]++;
        }

        System.out.println(Arrays.toString(indices));
    }
}
