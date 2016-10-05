package com.saponace.pureGen.Main;

public class DebugUtils {

    /**
     * The debug selector
     */
    private static boolean debugMode = false;


    // Display generation progress state if the debug flag is set to true

    /**
     * Display debug logs in the console
     * @param str The string to print
     */
    public static void printDebug(String str) {
        if (debugMode)
            System.out.println(str);
    }

    /**
     * Set the behaviour of the app. Should it print debug logs ?
     * @param bool If set to true, the logs will be displayed
     */
    public void setDebugMode(Boolean bool){
        this.debugMode = bool;
    }
}
