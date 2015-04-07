package edu.ou.cs.real.critter;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.exceptions.VectorSizeMismatchException;
import org.neuroph.nnet.Perceptron;

/**
 * Created by Brian on 3/6/2015.
 */
public class BasicNightNetwork implements CritterNeuralNetwork {
    private NeuralNetwork neuralNetwork;

    /**
     * Create the basic night time network
     */
    public BasicNightNetwork() {
        neuralNetwork = new Perceptron(10, 5); // TODO
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
}
