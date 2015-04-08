package edu.ou.cs.real.critter;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.exceptions.VectorSizeMismatchException;
import org.neuroph.nnet.Perceptron;
import org.neuroph.util.random.GaussianRandomizer;

/**
 * Created by Brian on 3/6/2015.
 */
public class BasicDayNetwork implements CritterNeuralNetwork {
    protected NeuralNetwork neuralNetwork;

    public BasicDayNetwork(CritterNeuralNetwork parent, double mean, double sd) {
        neuralNetwork = new Perceptron(8, 5);
        GaussianRandomizer randomizer = new GaussianRandomizer(mean, sd);
        neuralNetwork.setWeights(parent.getWeights());
        randomizer.randomize(neuralNetwork);
    }

    /**
     * Create a neural network according to predefined specifications
     */
    public BasicDayNetwork() {
        neuralNetwork = new Perceptron(8, 5);
        neuralNetwork.randomizeWeights();
    }

    /**
     * Run the neural network on the given inputs
     *
     * @param inputs the inputs of the neural network
     * @return the output of the neural network
     * @throws VectorSizeMismatchException if the input size does not correspond to the number of inputs into the network
     */
    public double[] run(double[] inputs) throws VectorSizeMismatchException {
        neuralNetwork.setInput(inputs);
        neuralNetwork.calculate();
        return neuralNetwork.getOutput();
    }

    /**
     * There's no good way to convert Double[] to double[] so this does it for you
     *
     * @return returns an array of the weights of the neural network
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
     * Sets the weights of the neural network to the specified values
     *
     * @param weights the new array of weights (hopefully it's the right length!)
     */
    public void setWeights(double[] weights) {
        neuralNetwork.setWeights(weights);
    }
}

