package edu.ou.cs.real;

/**
 * Created by Brian on 3/2/2015.
 */
public enum Action {
    NO_ACTION("No action"),
    NORTH("North"),
    SOUTH("South"),
    EAST("East"),
    WEST("West");

    private String string;

    private Action(String string) {
        this.string = string;
    }

    public String toString() {
        return string;
    }
}
