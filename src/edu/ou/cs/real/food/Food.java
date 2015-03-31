package edu.ou.cs.real.food;

import com.sun.javafx.geom.Vec2d;
import edu.ou.cs.real.critter.Critterable;

import java.util.ArrayList;

/**
 * Created by Brian on 3/30/2015.
 */
public class Food {
    Vec2d location;
    double value;

    ArrayList<Critterable> claims;

    /**
     * Create food with default value at location
     *
     * @param location location of where to create the food
     */
    public Food(Vec2d location) {
        this.location = location;

        value = 1f;

        claims = new ArrayList<Critterable>();
    }

    /**
     * Create food with a given value at location
     *
     * @param location location of where to create the food
     * @param value caloric value of the food
     */
    public Food(Vec2d location, float value) {
        this.location = location;

        this.value = value;

        claims = new ArrayList<Critterable>();
    }

    /**
     * Remove all claims on the piece of food
     */
    public void emptyClaims(){
        claims.clear();
    }

    /**
     * Get the caloric value of this food
     *
     * @return the double value of the food's worth
     */
    public double getValue() {
        return value;
    }

    /**
     * Get all the Critterable instances that have laid claim to this food
     *
     * @return An ArrayList of claiming individuals
     */
    public ArrayList<Critterable> getClaims(){
        return claims;
    }

    /**
     * Update the list of claims to include the given critter
     *
     * @param critter the critter laying claim
     */
    public void addClaim(Critterable critter) {
        claims.add(critter);
    }

    /**
     * Get the location of this food
     *
     * @return a Vec2d representing the x,y coordinates of this food
     */
    public Vec2d getLocation(){
        return location;
    }
}
