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

    public Food(Vec2d location) {
        this.location = location;

        value = 1f;

        claims = new ArrayList<Critterable>();
    }

    public Food(Vec2d location, float value) {
        this.location = location;

        this.value = value;

        claims = new ArrayList<Critterable>();
    }

    public void emptyClaims(){
        claims.clear();
    }

    public double getValue() {
        return value;
    }

    public ArrayList<Critterable> getClaims(){
        return claims;
    }

    public void addClaim(Critterable critter) {
        claims.add(critter);
    }

    public Vec2d getPosition(){
        return location;
    }
}
