package com.saponace.pureGen.Main;

import java.util.Properties;

public class GeneralProperties {

    public GeneralProperties(Properties props) {
        logLevel = props.getProperty("logLevel", "INFO");
    }

    /**
     * The logging level of the application
     */
    public String logLevel;

    @Override
    public String toString() {
        return "Logging level: " + logLevel.toString();
    }
}
