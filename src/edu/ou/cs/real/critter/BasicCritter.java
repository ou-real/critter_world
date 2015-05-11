package edu.ou.cs.real.critter;

import com.sun.javafx.geom.Vec2d;
import edu.ou.cs.real.Action;
import edu.ou.cs.real.settings.RandomUtility;
import edu.ou.cs.real.settings.Settings;
import edu.ou.cs.real.world.Arena;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Brian on 3/6/2015.
 *
 * This is the primary implementation of the critterable interface that can be used for experiments
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

    /**
     * Builds a new critter by mutating from a parent
     *
     * @param parent the parent of the new critter
     * @param settings the settings object for the entire experiment
     */
    public BasicCritter(BasicCritter parent, Settings settings) {
        this.settings = settings;
        children = new ArrayList<Critterable>();

        this.parent = parent;
        arena = parent.arena;

        range = 0;

        location = new Vec2d(settings.getDouble("nest x"), settings.getDouble("nest y"));

        dayNetwork = new BasicDayNetwork(parent.dayNetwork, settings);
        nightNetwork = new BasicNightNetwork(parent.nightNetwork, settings);
    }

    /**
     * Build a new critter from scratch to place in the arena
     *
     * @param arena the arena the critter will be placed in
     * @param settings the settings object for the entire experiment
     */
    public BasicCritter(Arena arena, Settings settings) {
        this.settings = settings;
        children = new ArrayList<Critterable>();

        parent = null;
        this.arena = arena;

        range = settings.getDouble("creature startSize");

        location = new Vec2d(settings.getDouble("nest x"), settings.getDouble("nest y"));

        dayNetwork = new BasicDayNetwork();
        nightNetwork = new BasicNightNetwork();

        /*System.out.println("Day: " + dayNetwork);
        System.out.println("Night: " + nightNetwork);
        System.out.println();*/
    }

    /**
     * Get the location of the critter in the arena
     *
     * @return the location in x,y coordinates
     */
    public Vec2d getLocation() {
        return location;
    }

    /**
     * Set the location of the critter in the arena
     *
     * @param location the location in x,y coordinates
     */
    public void setLocation(Vec2d location) {
        this.location = location;
    }

    /**
     * Set the arena of the critter (this is used when the critter migrates to a new arena)
     *
     * @param newHome the new arena for the critter's home
     */
    public void setArena(Arena newHome) {
        arena = newHome;
        children.clear();
    }

    /**
     * Clear the food bank of the critter to 0
     */
    public void emptyFood() {
        collectedFood = 0;
    }

    /**
     * Get the amount of food this critter has in its food bank
     *
     * @return the amount of held food calories
     */
    public double getFood() {
        return collectedFood;
    }

    /**
     * Increase the food bank by the given amount
     *
     * @param foodValue the food amount in calories
     */
    public void addFood(double foodValue) {
        collectedFood += foodValue;
    }

    /**
     * Run the day neural network to determine where the critter should move
     *
     * @return the action the critter decided to take
     */
    public Action takeTurn() {
        double foodNorth = arena.getFoodNear(new Vec2d(location.x, location.y - range), range);
        double foodSouth = arena.getFoodNear(new Vec2d(location.x, location.y + range), range);
        double foodEast = arena.getFoodNear(new Vec2d(location.x + range, location.y), range);
        double foodWest = arena.getFoodNear(new Vec2d(location.x - range, location.y), range);
        double foodHere = arena.getFoodNear(location, range);
        // collectedFood is another input
        double maintenance = getMaintenance();

        double[] inputs = {foodNorth, foodSouth, foodEast, foodWest, foodHere, collectedFood, maintenance};
        double[] outputs = dayNetwork.run(inputs);
        System.out.println(Arrays.toString(outputs));
        int decision = RandomUtility.getRandom(outputs);
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

    /**
     * run the night neural network to determine how the critter should distribute its collected food
     *
     * @return an array containing the distributions to (respectively) maintenance, storage, sharing, reproduction, and
     * migration
     */
    public double[] distributeFood() {
        double[] inputs = new double[6];
        inputs[0] = collectedFood;
        inputs[1] = children.size();
        inputs[2] = getMaintenance();
        inputs[3] = getReproductionBank();
        inputs[4] = getMigrationBank();
        inputs[5] = getRange();

        System.out.print(String.format("%f -->", inputs[0]));
        return nightNetwork.run(inputs);
    }

    /**
     * @return how far the critter can reach to gather food
     */
    public double getRange() {
        return range;
    }

    /**
     * @return how many calories are required per day to maintain this creature's size
     */
    public double getMaintenance() {
        return range;
    }

    /**
     * Create a new Critter based on this one
     *
     * @return the new child that was created
     */
    public Critterable reproduce() {
        Critterable child = new BasicCritter(this, settings);
        children.add(child);
        return child;
    }

    /**
     * @return the number of calories stored in the reproduction bank
     */
    public double getReproductionBank() {
        return reproductionBank;
    }

    /**
     * add calories to the reproduction bank
     *
     * @param value the number of calories to add
     */
    public void increaseReproductionBank(double value) {
        reproductionBank += value;
    }

    public void increaseSize(double value) {
        range += value;
    }

    public void maintain() {
        range -= getMaintenance();
    }

    public double getMigrationBank() {
        return migrationBank;
    }

    public void increaseMigrationBank(double value) {
        migrationBank += value;
    }
}
