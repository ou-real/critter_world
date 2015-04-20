package edu.ou.cs.real;

import edu.ou.cs.real.settings.Settings;
import edu.ou.cs.real.world.Experiment;

import java.io.File;

public class Main {
    public static Settings settings;

    public static void main(String[] args) {
        if (args.length > 0) {
            settings = new Settings(args[0]);
        } else {
            settings = new Settings();
        }

        Experiment experiment = new Experiment(settings);
        experiment.run();
        // TODO
    }
}
