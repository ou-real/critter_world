package edu.ou.cs.real.edu.ou.cs.real.food;

import com.sun.javafx.geom.Vec2d;

import java.util.ArrayList;

/**
 * Created by Brian on 3/6/2015.
 * Implementation based on princeton.edu example implementation
 */
public class QuadTree {
    private static int threshold = 5;

    private Vec2d min;
    private Vec2d max;

    private QuadTree ne = null;
    private QuadTree se = null;
    private QuadTree sw = null;
    private QuadTree nw = null;

    private ArrayList<Vec2d> foods;

    public QuadTree(Vec2d max) {
        this.min = new Vec2d(0, 0);
        this.max = max;

        foods = new ArrayList<Vec2d>(threshold);
    }

    public QuadTree(Vec2d min, Vec2d max) {
        this.min = min;
        this.max = max;

        foods = new ArrayList<Vec2d>(threshold);
    }

    public boolean insert(Vec2d food) {
        if (food.x > max.x || food.x < min.x) {
            return false;
        } else if (food.y > max.y || food.y < min.y) {
            return false;
        }
        
        if (ne == null) {
            foods.add(food);
            if (foods.size() > threshold) {
                se = new QuadTree(new Vec2d(min.x + ((max.x - min.x) / 2f), min.y + ((max.y - min.y) / 2f)), new Vec2d(max));
                ne = new QuadTree(new Vec2d(min.x + ((max.x - min.x) / 2f), min.y), new Vec2d(max.x, min.y + ((max.y - min.y) / 2f)));
                nw = new QuadTree(new Vec2d(min), new Vec2d(min.x + ((max.x - min.x) / 2f), min.y + ((max.y - min.y) / 2f)));
                sw = new QuadTree(new Vec2d(min.x, min.y + ((max.y - min.y) / 2f)), new Vec2d(min.x + ((max.x - min.x) / 2f), max.y));

                ArrayList<Vec2d> temp = foods;
                foods = new ArrayList<Vec2d>(threshold);
                for (Vec2d f: temp) {
                    this.insert(f);
                }
            }
            return true;
        } else {
            if (food.x > (max.x - min.x) / 2f) { // east side
                if (food.y > (max.y - min.y) / 2f) { // south side
                    return se.insert(food);
                } else {
                    return ne.insert(food);
                }
            } else { // west side
                if (food.y > (max.y - min.y) / 2f) { // south side
                    return sw.insert(food);
                } else {
                    return nw.insert(food);
                }
            }
        }
    }

    public int rangeSearch(Vec2d min, Vec2d max) {
        if (max.x < this.min.x || max.y < this.min.y) {
            return 0;
        } else if (min.x > this.max.x || min.y > this.max.y) {
            return 0;
        }
        
        if (ne == null) {
            int sum = 0;
            for (Vec2d food : foods) {
                if (food.x > min.x && food.y > min.y) {
                    if (food.x < max.x && food.y < max.y) {
                        sum++;
                    }
                }
            }
            return sum;
        } else {
            return se.rangeSearch(min, max) + ne.rangeSearch(min,max) + nw.rangeSearch(min, max) + sw.rangeSearch(min, max);
        }
    }

    public int rangeDelete(Vec2d min, Vec2d max) {
        if (max.x < this.min.x || max.y < this.min.y) {
            return 0;
        } else if (min.x > this.max.x || min.y > this.max.y) {
            return 0;
        }
        
        if (ne == null) {
            ArrayList<Vec2d> temp = new ArrayList<Vec2d>(threshold);
            for (Vec2d f : foods) {
                if (f.x > min.x && f.x < max.x && f.y > min.y && f.y < max.y) {
                    temp.add(f);
                }
            }
            int sum = foods.size() - temp.size();
            foods = temp;
            return sum;
        } else {
            return se.rangeDelete(min, max) + ne.rangeDelete(min, max) + nw.rangeDelete(min, max) + sw.rangeDelete(min, max);
        }
    }
}
