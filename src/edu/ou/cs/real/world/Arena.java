package edu.ou.cs.real.world;

import com.sun.javafx.geom.Vec2d;
import edu.ou.cs.real.critter.BasicCritter;
import edu.ou.cs.real.critter.Critterable;
import edu.ou.cs.real.food.ArrayFoodMap;
import edu.ou.cs.real.food.Food;
import edu.ou.cs.real.food.FoodMappable;
import edu.ou.cs.real.settings.Settings;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Brian on 3/6/2015.
 */
public class Arena {
    public Settings settings;
    public Experiment experiment;

    private Vec2d size;
    private FoodMappable foodMap;

    private ArrayList<Critterable> critters;
    private ArrayList<Critterable> queue;

    private int dayLength;

    public Arena(Experiment experiment, Settings settings, Critterable... critters) {
        this.experiment = experiment;
        this.settings = settings;

        size = new Vec2d(settings.getDouble("arena width"), settings.getDouble("arena height"));
        dayLength = settings.getInt("dayLength");

        foodMap = new ArrayFoodMap();
        this.critters = new ArrayList<Critterable>(Arrays.asList(critters));
        queue = new ArrayList<Critterable>();
    }

    public Arena(Experiment experiment, Settings settings) {
        this.experiment = experiment;
        this.settings = settings;

        size = new Vec2d(settings.getDouble("arenaWidth"), settings.getDouble("arenaHeight"));
        dayLength = settings.getInt("dayLength");

        foodMap = new ArrayFoodMap();
        critters = new ArrayList<Critterable>();
        queue = new ArrayList<Critterable>();

        critters.add(new BasicCritter(this, settings));
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
                        critter.setLocation(new Vec2d((location.x + size.x) % size.x, (location.y - critter.getRange() + size.y) % size.y));
                        break;
                    case SOUTH:
                        critter.setLocation(new Vec2d((location.x + size.x) % size.x, (location.y + critter.getRange() + size.y) % size.y));
                        break;
                    case EAST:
                        critter.setLocation(new Vec2d((location.x + critter.getRange() + size.x) % size.x , (location.y + size.y) % size.y));
                        break;
                    case WEST:
                        critter.setLocation(new Vec2d((location.x - critter.getRange() + size.x) % size.x, (location.y + size.y) % size.y));
                        break;
                    default:
                }
            }
        }

        for (Food food : foodMap.getAll()) {
            for (Critterable critter : food.getClaims()) {
                critter.addFood(food.getValue() / food.getClaims().size());
            }
            food.emptyClaims();
        }

        /**
         * Outputs are:a
         * [0] - maintenance/growth
         * [1] - hold
         * [2] - distribute
         * [3] - reproduce
         * [4] - new home
         */
        for (int i = 0; i < critters.size(); i++) {
            Critterable critter = critters.get(i);
            double[] distribution = critter.distributeFood();

            double food = critter.getFood();
            double total = 0;
            for (double d : distribution) {
                total += d;
            }

            critter.increaseSize(food * distribution[0] / total);

            double shared = food * distribution[2] / total;
            for (int j = 0; j < critters.size(); j++) {
                critters.get(j).increaseSize(shared / critters.size());
            }

            critter.increaseReproductionBank(food * distribution[3] / total);
            critter.increaseMigrationBank(food * distribution[4] / total);

            critter.emptyFood();
            critter.maintain();

            critter.addFood(food * distribution[1] / total);
            critter.setLocation(new Vec2d(settings.getDouble("nest x"), settings.getDouble("nest y")));

            if (critter.getRange() < 0) {
                critters.remove(i--);
            } else {
                while (critter.getReproductionBank() >= settings.getDouble("creature reproduction_threshold")) {
                    critter.increaseReproductionBank(0 - settings.getDouble("creature reproduction_threshold"));
                    queue.add(critter.reproduce());
                }
                if (critter.getMigrationBank() >= settings.getDouble("creature migration_threshold")) {
                    critter.increaseMigrationBank(0 - settings.getDouble("creature migration_threshold"));

                    Arena newHome = new Arena(experiment, settings, critter);
                    critter.setArena(newHome);
                    experiment.addArena(newHome);
                    critters.remove(i--);
                }
            }
        }

        for (Critterable critter : queue) {
            critters.add(critter);
        }
        queue.clear();
    }

    public double getFoodNear(Vec2d location, double range) {
        double foodValues = 0;
        for (Food food : foodMap.rangeSearch(new Vec2d(location.x - range, location.y - range), new Vec2d(location.x + range, location.y + range))) {
            foodValues += food.getValue();
        }
        return foodValues;
    }

    public void distribute(double value) {
        for (int i = 0; i < (int)value; i++) {
            foodMap.addFood(new Food(randomCoordinates()));
        }
        foodMap.addFood(new Food(randomCoordinates(), value - (int)value));
    }

    public Vec2d randomCoordinates() {
        return new Vec2d(settings.getRandom().nextDouble() * size.x, settings.getRandom().nextDouble() * size.y);
    }
}
