package edu.ou.cs.real.world;

import com.sun.javafx.geom.Vec2d;
import edu.ou.cs.real.critter.Critterable;
import edu.ou.cs.real.food.ArrayFoodMap;
import edu.ou.cs.real.food.Food;
import edu.ou.cs.real.food.FoodMappable;

import java.util.ArrayList;

/**
 * Created by Brian on 3/6/2015.
 */
public class Arena {
    private Vec2d size;
    private FoodMappable foodMap;
    private ArrayList<Critterable> critters;

    private int dayLength;

    public Arena(Vec2d size) {
        this.size = size;
        foodMap = new ArrayFoodMap();
        critters = new ArrayList<Critterable>();
    }

    public void update() {
        for (int i = 0; i < dayLength; i++) {
            for (Critterable critter : critters) {
                Vec2d location = critter.getLocation();
                switch (critter.takeTurn()) {
                    case NO_ACTION:
                        double range = critter.getRange();
                        ArrayList<Food> foodInRange = foodMap.rangeSearch(new Vec2d(location.x - range, location.y - range), new Vec2d(location.x + range, location.y + range));
                        for (Food food : foodInRange) {
                            food.addClaim(critter);
                        }
                        break;
                    case NORTH:
                        critter.setLocation(new Vec2d(location.x, location.y - critter.getRange()));
                        break;
                    case SOUTH:
                        critter.setLocation(new Vec2d(location.x, location.y + critter.getRange()));
                        break;
                    case EAST:
                        critter.setLocation(new Vec2d(location.x + critter.getRange(), location.y));
                        break;
                    case WEST:
                        critter.setLocation(new Vec2d(location.x - critter.getRange(), location.y));
                        break;
                    default:
                        // TODO error
                }
            }
        }

        for (Food food : foodMap.getAll()) {
            for (Critterable critter : food.getClaims()) {
                critter.addFood(food.getValue() / food.getClaims().size());
            }
            food.emptyClaims();
        }

        for (Critterable critter : critters) {
            double[] distribution = critter.distributeFood();

            // TODO do things here

            critter.emptyFood();
        }
    }

    public double getFoodNear(Vec2d location, double range) {
        double foodValues = 0;
        for (Food food : foodMap.rangeSearch(new Vec2d(location.x - range, location.y - range), new Vec2d(location.x + range, location.y + range))) {
            foodValues += food.getValue();
        }
        return foodValues;
    }
}
