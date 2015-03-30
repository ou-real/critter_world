package edu.ou.cs.real.critter;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.exceptions.VectorSizeMismatchException;
import org.neuroph.nnet.Perceptron;

/**
 * Created by Brian on 3/6/2015.
 */
public class BasicNightNetwork implements CritterNeuralNetwork {
    private NeuralNetwork neuralNetwork;

    public BasicNightNetwork() {
        neuralNetwork = new Perceptron(10, 5); // TODO
        neuralNetwork.randomizeWeights();
    }

    public double[] run(double[] inputs) throws VectorSizeMismatchException {
        neuralNetwork.setInput(inputs);
        neuralNetwork.calculate();
        return neuralNetwork.getOutput();
    }

    public double[] getWeights() {
        Double[] underlyingWeights = neuralNetwork.getWeights();
        double[] weights = new double[underlyingWeights.length];
        for (int i = 0; i < underlyingWeights.length; i++) {
            weights[i] = (double) underlyingWeights[i];
        }
        return weights;
    }

    public void setWeights(double[] weights) {
        neuralNetwork.setWeights(weights);
    }
}
