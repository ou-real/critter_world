package edu.ou.cs.real.food;

import com.sun.javafx.geom.Vec2d;

import java.util.ArrayList;

/**
 * Created by Brian on 3/30/2015.
 */
public interface FoodMappable {
    public ArrayList<Food> rangeSearch(Vec2d ul, Vec2d br);

    public ArrayList<Food> getAll();

    public void addFood(Food food);
}
