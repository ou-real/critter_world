package edu.ou.cs.real.world;

import edu.ou.cs.real.settings.Settings;

import java.util.ArrayList;

/**
 * Created by Brian on 4/6/2015.
 */
public class Experiment {
    public Settings settings;

    public int startArenaCount;
    public int experimentLength;

    private ArrayList<Arena> arenas;
    private ArrayList<Arena> queue;

    public Experiment() {
    }

    public Experiment(Settings settings) {
        this.settings = settings;

        startArenaCount = settings.getInt("startArenaCount");
        experimentLength = settings.getInt("experimentLength");

        arenas = new ArrayList<Arena>(startArenaCount);
        for (int i = 0; i < startArenaCount; i++) {
            arenas.add(new Arena(this, settings));
        }

        queue = new ArrayList<Arena>();
    }

    public void addArena(Arena arena) {
        queue.add(arena);
    }

    public void run() {
        for (int day = 0; day < experimentLength; day++) {
            settings.log("Day: %d", day);

            double totalFood = settings.getDouble("foodDistribution");
            for (Arena arena : arenas) {
                arena.distribute(totalFood / arenas.size());
            }

            for (Arena arena : arenas) {
                arena.update();
            }

            for (Arena arena : queue) {
                arenas.add(arena);
            }
            queue.clear();
        }
    }
}
