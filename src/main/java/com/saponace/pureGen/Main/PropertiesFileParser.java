package com.saponace.pureGen.Main;

import com.saponace.pureGen.generation.GenerationProperties;
import com.saponace.pureGen.rendering.RenderingProperties;

import java.io.*;
import java.util.Properties;

public class PropertiesFileParser {

    /**
     * Parse a properties file
     * @param propertyFileName The path of the property file
     * @return A properties object containing the properties of the file
     * @throws FileNotFoundException
     */
    public Properties parse(String propertyFileName) throws FileNotFoundException {
        Properties props = new Properties();
        InputStream is = openStream(propertyFileName);
        try {
            props.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    /**
     * Return a stream corresponding to the open property file
     * @param propertyFileName The path of the file
     * @return The InputStream of this file
     */
    private InputStream openStream(String propertyFileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResourceAsStream(propertyFileName);
    }


    public static void main(String[] args) {
        try {
            System.out.println(new GeneralProperties(new PropertiesFileParser().parse("general.properties")).toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
