package edu.ou.cs.real.critter;

/**
 * Created by Brian on 3/2/2015.
 */
public interface CritterNeuralNetwork {
    public double[] run(double[] inputs);

    public double[] getWeights();
    public void setWeights(double[] weights);
}
