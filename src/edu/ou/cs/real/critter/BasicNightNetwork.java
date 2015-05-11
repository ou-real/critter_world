package edu.ou.cs.real.critter;

import edu.ou.cs.real.settings.Settings;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.exceptions.VectorSizeMismatchException;
import org.neuroph.nnet.CompetitiveNetwork;
import org.neuroph.nnet.Perceptron;
import org.neuroph.nnet.UnsupervisedHebbianNetwork;
import org.neuroph.util.random.GaussianRandomizer;

import java.security.SecureRandom;

/**
 * Created by Brian on 3/6/2015.
 */
public class BasicNightNetwork implements CritterNeuralNetwork {
    private NeuralNetwork neuralNetwork;

    public BasicNightNetwork(CritterNeuralNetwork parent, Settings settings) {
        neuralNetwork = NeuralNetwork.createFromFile("nighttime.nnet");

        SecureRandom random = settings.getRandom();
        double sd = settings.getDouble("randomizer sd");

        double[] weights = parent.getWeights();
        for (int i = 0; i < weights.length; i++) {
            weights[i] += random.nextGaussian() * sd;
        }

        neuralNetwork.setWeights(weights);
    }

    /**
     * Create the basic night time network
     */
    public BasicNightNetwork() {
        neuralNetwork = NeuralNetwork.createFromFile("nighttime.nnet");
        neuralNetwork.randomizeWeights();
    }

    /**
     * run this network on the given network
     *
     * @param inputs the inputs to the neural network
     * @return the output of the neural network
     * @throws VectorSizeMismatchException if the inputs are the wrong length
     */
    public double[] run(double[] inputs) throws VectorSizeMismatchException {
        neuralNetwork.setInput(inputs);
        neuralNetwork.calculate();
        return neuralNetwork.getOutput();
    }

    /**
     * Get the weights of the neural network
     *
     * @return the weights of the neural network
     */
    public double[] getWeights() {
        Double[] underlyingWeights = neuralNetwork.getWeights();
        double[] weights = new double[underlyingWeights.length];
        for (int i = 0; i < underlyingWeights.length; i++) {
            weights[i] = (double) underlyingWeights[i];
        }
        return weights;
    }

    /**
     * set the weights of the neural network
     *
     * @param weights the new weights to be set
     */
    public void setWeights(double[] weights) {
        neuralNetwork.setWeights(weights);
    }

    public String toString() {
        String s = "";
        double[] weights = getWeights();
        for (double d : weights) {
            s += String.format("%f, ", d);
        }
        return s;
    }
}
