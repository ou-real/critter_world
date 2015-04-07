package edu.ou.cs.real.world;

import com.sun.javafx.geom.Vec2d;
import edu.ou.cs.real.settings.Settings;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Brian on 4/6/2015.
 */
public class Experiment {
    public int startArenaCount;
    public int daysToRun;

    private ArrayList<Arena> arenas;

    public Experiment() {
    }

    public Experiment(Settings settings) {
        startArenaCount = settings.getInt("startArenaCount");
        daysToRun = settings.getInt("startArenaCount");

        arenas = new ArrayList<Arena>(startArenaCount);
        for (int i = 0; i < startArenaCount; i++) {
            arenas.add(new Arena(settings));
        }
    }

    public void run() {
        for (int day = 0; day < daysToRun; day++) {
            // TODO distribute food to each arena

            // TODO run each arena every day
        }
    }
}
