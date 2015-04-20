package edu.ou.cs.real.settings;

import java.util.Objects;

/**
 * Created by Brian on 4/18/2015.
 */
public class PrintLogger implements Logger {
    public int day = 0;

    public void log(String format, Object... args) {
        String message = String.format(format, args);
        System.out.println(String.format("[Day %d] %s", day, message));
    }

    public void setDay(int day) {
        this.day = day;
    }
}
