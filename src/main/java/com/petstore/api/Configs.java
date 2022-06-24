package com.petstore.api;

import java.io.IOException;
import java.util.Properties;

public class Configs {

    static Properties PROPERTIES = new Properties();
    static {
        try {
            PROPERTIES.load(Configs.class.getClassLoader().getResourceAsStream("config_endpoints.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String BASE_URL = PROPERTIES.getProperty("BASE_URL");
    public static String PET_ENDPOINT = BASE_URL.concat("/pet");
    public static String STORE_ENDPOINT = BASE_URL.concat("/store");
    public static String USER_ENDPOINT = BASE_URL.concat("/user");
}
