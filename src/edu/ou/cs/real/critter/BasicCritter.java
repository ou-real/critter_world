package edu.ou.cs.real.critter;

import com.sun.javafx.geom.Vec2d;
import edu.ou.cs.real.Action;
import edu.ou.cs.real.world.Arena;

import java.awt.*;
import java.util.Random;

/**
 * Created by Brian on 3/6/2015.
 */
public class BasicCritter implements Critterable {
    private Arena arena;

    private CritterNeuralNetwork dayNetwork;
    private CritterNeuralNetwork nightNetwork;

    private Vec2d location;
    private double collectedFood;

    private double range;

    public BasicCritter(BasicCritter parent) {
        // TODO reproduction code
    }

    public BasicCritter(Arena arena) {
        this.arena = arena;

        location = new Vec2d(0, 0);

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
        double[] inputs = new double[10];
        // TODO what inputs do we need to give the night network?
        return nightNetwork.run(inputs);
    }

    public double getRange() {
        return range;
    }

    public double getMaintenance() {
        return range;
    }

    public Critterable reproduce() {
        return new BasicCritter(this);
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
}
