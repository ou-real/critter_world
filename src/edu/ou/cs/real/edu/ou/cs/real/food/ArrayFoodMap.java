package edu.ou.cs.real.edu.ou.cs.real.food;

import com.sun.javafx.geom.Vec2d;

import java.util.ArrayList;

/**
 * Created by Brian on 3/30/2015.
 */
public class ArrayFoodMap implements FoodMappable {
    private ArrayList<Food> allFood;

    public ArrayFoodMap() {}

    public ArrayList<Food> getAll() {
        return allFood;
    }

    public ArrayList<Food> rangeSearch(Vec2d min, Vec2d max) {
        ArrayList<Food> food = new ArrayList<Food>();

        for (Food f : allFood) {
            Vec2d p = f.getPosition();
            if (p.x >= min.x && p.x <= max.x && p.y >= min.y && p.y <= max.y) {
                food.add(f);
            }
        }

        return food;
    }

    public void addFood(Food food) {
        allFood.add(food);
    }
}
