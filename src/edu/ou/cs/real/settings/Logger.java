package edu.ou.cs.real.settings;

import edu.ou.cs.real.Action;
import edu.ou.cs.real.critter.Critterable;

/**
 * Created by Brian on 4/18/2015.
 */
public interface Logger {
    public void log(String format, Object... args);

    public void logAction(Critterable critter, Action action);



    public void setDay(int day);
    public void setTurn(int turn);
}
