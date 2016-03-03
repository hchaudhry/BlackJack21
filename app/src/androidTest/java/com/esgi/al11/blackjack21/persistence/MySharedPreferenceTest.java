package com.esgi.al11.blackjack21.persistence;

import android.test.AndroidTestCase;

/**
 * Created by Hussam on 03/03/2016.
 */
public class MySharedPreferenceTest extends AndroidTestCase {

    private MySharedPreference preference;


    public void setUp() throws Exception {
        super.setUp();

        preference = new MySharedPreference(getContext());
    }

    public void testSaveString() throws Exception {
        preference.saveString("cleTest", "valeurTest");
        String value = preference.getStringValue("cleTest");
        preference.removeValue("cleTest");
        assertEquals("valeurTest", value);
    }

    public void testGetStringValue() throws Exception {
        String value = preference.getStringValue("cleTest");
        assertEquals(null, value);
    }

    public void testSaveInt() throws Exception {
        preference.saveInt("testInt", 2);
        assertEquals(2, preference.getIntValue("testInt"));
    }

    public void testGetIntValue() throws Exception {
        int value = preference.getIntValue("testInt");
        assertEquals(0, value);
    }

    public void testClearSharedPreferences() throws Exception {
        preference.saveString("clearKey", "clearValue");
        preference.clearSharedPreferences();

        assertEquals(null, preference.getStringValue("clearKey"));
    }

    public void testRemoveValue() throws Exception {
        preference.saveString("cleSave", "valeurSave");
        preference.removeValue("cleSave");

        assertEquals(null, preference.getStringValue("cleSave"));
    }

    public void testContains() throws Exception {
        assertFalse(preference.contains("testContains"));
    }
}