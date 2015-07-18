package Main;

public class DebugUtils {

    /**
     * If set to true, details about the generation and rendering process
     * will be printed in the console
     */
    private static boolean debugMode = true;


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
