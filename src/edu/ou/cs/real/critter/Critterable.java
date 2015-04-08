package edu.ou.cs.real.critter;

import com.sun.javafx.geom.Vec2d;
import edu.ou.cs.real.Action;

/**
 * Created by Brian on 3/2/2015.
 */
public interface Critterable {
    public Vec2d getLocation();
    public void setLocation(Vec2d location);

    public void emptyFood();
    public double getFood();
    public void addFood(double foodValue);

    public Action takeTurn();

    public double[] distributeFood();

    public double getRange();

    public double getMaintenance();

    public Critterable reproduce();

    public double getReproductionBank();
    public void increaseReproductionBank(double value);

    public double getMigrationBank();
    public void increaseMigrationBank(double value);
}
