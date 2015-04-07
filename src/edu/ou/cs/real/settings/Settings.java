package edu.ou.cs.real.settings;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Brian on 4/6/2015.
 */
public class Settings {
    public JSONObject settings;

    public Settings() {
    }

    public Settings(String settingsFilePath) {
        try {
            String jsonString = readFile(settingsFilePath, Charset.defaultCharset());
            settings = new JSONObject(jsonString);

            System.out.println("Created Settings object!");
        } catch (IOException e) {
            settings = new JSONObject();
        }
    }

    public Object get(String key) {
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

    public int getInt(String key) {
        return (Integer)get(key);
    }

    public double getDouble(String key) {
        return (Double)get(key);
    }

    private String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
