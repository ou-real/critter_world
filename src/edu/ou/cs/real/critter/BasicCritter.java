package edu.ou.cs.real.critter;

import com.sun.javafx.geom.Vec2d;
import edu.ou.cs.real.Action;
import edu.ou.cs.real.settings.Settings;
import edu.ou.cs.real.world.Arena;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Brian on 3/6/2015.
 */
public class BasicCritter implements Critterable {
    public Arena arena;
    public Settings settings;
    public Critterable parent;

    protected CritterNeuralNetwork dayNetwork;
    protected CritterNeuralNetwork nightNetwork;

    private Vec2d location;
    private double collectedFood;

    private ArrayList<Critterable> children;

    private double reproductionBank;
    private double migrationBank;

    private double range;

    public BasicCritter(BasicCritter parent, Settings settings) {
        this.settings = settings;
        children = new ArrayList<Critterable>();

        this.parent = parent;
        arena = parent.arena;

        location = new Vec2d(settings.getDouble("nest x"), settings.getDouble("nest y"));

        double mean = settings.getDouble("randomizer mean");
        double sd = settings.getDouble("randomizer standard_deviation");
        dayNetwork = new BasicDayNetwork(parent.dayNetwork, mean, sd);
        nightNetwork = new BasicNightNetwork(parent.nightNetwork, mean, sd);
    }

    public BasicCritter(Arena arena, Settings settings) {
        this.settings = settings;
        children = new ArrayList<Critterable>();

        parent = null;
        this.arena = arena;

        location = new Vec2d(settings.getDouble("nest x"), settings.getDouble("nest y"));

        dayNetwork = new BasicDayNetwork();
        nightNetwork = new BasicNightNetwork();
    }

    public Vec2d getLocation() {
        return location;
    }

    public void setLocation(Vec2d location) {
        this.location = location;
    }

    public void emptyFood() {
        collectedFood = 0;
    }

    public double getFood() {
        return collectedFood;
    }

    public void addFood(double foodValue) {
        collectedFood += foodValue;
    }

    public Action takeTurn() {
        double foodNorth = arena.getFoodNear(new Vec2d(location.x, location.y - range), range);
        double foodSouth = arena.getFoodNear(new Vec2d(location.x, location.y + range), range);
        double foodEast = arena.getFoodNear(new Vec2d(location.x + range, location.y), range);
        double foodWest = arena.getFoodNear(new Vec2d(location.x - range, location.y), range);
        double foodHere = arena.getFoodNear(location, range);
        // collectedFood is another input
        double maintenance = getMaintenance();

        double[] inputs = {foodNorth, foodSouth, foodEast, foodWest, foodHere, collectedFood, maintenance};
        int decision = getRandom(dayNetwork.run(inputs));
        switch(decision) {
            case 0:
                return Action.NORTH;
            case 1:
                return Action.SOUTH;
            case 2:
                return Action.EAST;
            case 3:
                return Action.WEST;
            case 4:
                return Action.NO_ACTION;
            default:
                // TODO throw error
        }

        return Action.NO_ACTION;
    }

    public double[] distributeFood() {
        double[] inputs = new double[6];
        inputs[0] = collectedFood;
        inputs[1] = children.size();
        inputs[2] = getMaintenance();
        inputs[3] = getReproductionBank();
        inputs[4] = getMigrationBank();
        inputs[5] = getRange();

        return nightNetwork.run(inputs);
    }

    public double getRange() {
        return range;
    }

    public double getMaintenance() {
        return range;
    }

    // TODO should increment child list
    public Critterable reproduce() {
        Critterable child = new BasicCritter(this, settings);
        children.add(child);
        return child;
    }

    public int getRandom(double[] probabilities) {
        double sum = 0;
        for (double d : probabilities) {
            sum += d;
        }

        double index = (new Random()).nextDouble() % sum;
        double place = 0;
        int i = 0;
        while (place < index) {
            place += probabilities[i++];
        }

        return Math.max(0, i - 1);
    }

    public double getReproductionBank() {
        return reproductionBank;
    }

    public void increaseReproductionBank(double value) {
        reproductionBank += value;
    }

    public double getMigrationBank() {
        return migrationBank;
    }

    public void increaseMigrationBank(double value) {
        migrationBank += value;
    }
}
