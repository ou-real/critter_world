package edu.ou.cs.real.food;

import com.sun.javafx.geom.Vec2d;

import java.util.ArrayList;

/**
 * Created by Brian on 3/30/2015.
 * @author Brian M West
 */
public class ArrayFoodMap implements FoodMappable {
    private ArrayList<Food> allFood;

    /**
     * Default constructor
     */
    public ArrayFoodMap() {
        allFood = new ArrayList<Food>();
    }

    /**
     * Get all instances of food on the map
     *
     * @return an ArrayList containing all food on the map
     */
    public ArrayList<Food> getAll() {
        return allFood;
    }

    /**
     * Search by range for any food within a specified area
     *
     * @param min the upper left coordinates of the range to search
     * @param max the bottom right coordinates of the range to search
     * @return an ArrayList containing the food within the range
     */
    public ArrayList<Food> rangeSearch(Vec2d min, Vec2d max) {
        ArrayList<Food> food = new ArrayList<Food>();

        for (Food f : allFood) {
            Vec2d p = f.getLocation();
            if (p.x >= min.x && p.x <= max.x && p.y >= min.y && p.y <= max.y) {
                food.add(f);
            }
        }

        return food;
    }

    /**
     * Add food to the map
     *
     * @param food the food instance to add (which already should contain coordinates)
     */
    public void addFood(Food food) {
        allFood.add(food);
    }
}
