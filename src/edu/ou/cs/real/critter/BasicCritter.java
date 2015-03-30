package edu.ou.cs.real.critter;

import com.sun.javafx.geom.Vec2d;
import edu.ou.cs.real.Action;
import edu.ou.cs.real.world.Arena;

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
        return Action.NO_ACTION;
    }

    public double[] distributeFood() {
        return null;
    }

    public double getRange() {
        return range;
    }

    public Critterable reproduce() {
        return new BasicCritter(this);
    }
}
