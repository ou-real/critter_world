package edu.ou.cs.real.settings;

import edu.ou.cs.real.Action;
import edu.ou.cs.real.critter.Critterable;

import java.util.Objects;

/**
 * Created by Brian on 4/18/2015.
 */
public class PrintLogger implements Logger {
    public int day = 0;
    public int turn = 0;

    public void log(String format, Object... args) {
        String message = String.format(format, args);
        System.out.println(String.format("[Day %d] %s", day, message));
    }

    public void logAction(Critterable critter, Action action) {
        // TODO
    }

    public void setDay(int day) {
        this.day = day;
        this.turn = 0;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
