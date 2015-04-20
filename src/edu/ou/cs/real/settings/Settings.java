package edu.ou.cs.real.settings;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

/**
 * Created by Brian on 4/6/2015.
 */
public class Settings {
    public JSONObject settings;
    public SecureRandom random;

    public Settings() {
        random = new SecureRandom();
        byte[] bytes = ByteBuffer.allocate(8).putLong(System.currentTimeMillis()).array();
        random.setSeed(bytes);
    }

    public Settings(String settingsFilePath) {
        try {
            String jsonString = readFile(settingsFilePath, Charset.defaultCharset());
            settings = new JSONObject(jsonString);

            System.out.println("Created Settings object!");
        } catch (IOException e) {
            settings = new JSONObject();
        }

        random = new SecureRandom();
        long seed = 0;
        try {
            seed = getLong("random seed");
        } catch (ClassCastException e) {
            seed = System.currentTimeMillis();
        }
        byte[] bytes = ByteBuffer.allocate(8).putLong(seed).array();
        random.setSeed(bytes);
    }

    public Object get(String key) {
        log(String.format("Retrieve setting: \"%s\"", key));

        JSONObject current = settings;
        String[] values = key.split(" ");
        try {
            for (int i = 0; i < values.length - 1; i++) {
                current = current.getJSONObject(values[i]);
            }

            return current.get(values[values.length - 1]);
        } catch (JSONException e) {
            return "";
        }
    }

    public String getString(String key) {
        return (String)get(key);
    }

    public long getLong(String key) {
        try {
            return (Long)get(key);
        } catch (ClassCastException e) {
            return ((Integer)get(key)).longValue();
        }
    }

    public int getInt(String key) {
        return (Integer)get(key);
    }

    public double getDouble(String key) {
        try {
            return (Double)get(key);
        } catch (ClassCastException e) {
            return (double)getLong(key);
        }
    }

    private String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public SecureRandom getRandom() {
        return random;
    }

    public void log(String message) {
        System.out.println(message);
    }

    public void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }
}
