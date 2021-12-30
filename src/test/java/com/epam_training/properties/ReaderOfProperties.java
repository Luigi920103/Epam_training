package com.epam_training.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReaderOfProperties {
    public static Properties properties;

    public ReaderOfProperties() {
        propertiesReader();
    }

    public void propertiesReader() {
        //Carga de propiedades
        String appConfigPath = "src/test/resources/config.properties";
        properties = new Properties();
        try {
            properties.load(new FileInputStream(appConfigPath));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

}
