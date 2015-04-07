package edu.ou.cs.real.test

import edu.ou.cs.real.settings.Settings

/**
 * Created by Brian on 4/6/2015.
 */
class SettingsTest extends GroovyTestCase {
    void testGet() {
        Settings settings = new Settings("settings.json");

        assert settings.get("arena width") == 100;
        assert settings.get("arena height") == 100;
    }

    void testGetInt() {
        Settings settings = new Settings("settings.json");
    }

    void testGetDouble() {
        Settings settings = new Settings("settings.json");
    }

    void testGetString() {
        Settings settings = new Settings("settings.json");
    }
}
