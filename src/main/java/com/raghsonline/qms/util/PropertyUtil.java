package com.raghsonline.qms.util;

import org.hibernate.mapping.Property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    private static final String QUOTE_FILE_NAME = "quotes.md";

    private static PropertyUtil _propertyUtil;

    private static Properties _properties = new Properties();

    private PropertyUtil() { }

    public static PropertyUtil getInstance() {
        if(null== _propertyUtil) {
            _propertyUtil = new PropertyUtil();
        }

        return _propertyUtil;
    }

    static {
        loadProps();
    }
    public static void loadProps() {

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(QUOTE_FILE_NAME);

        System.out.println("InputStream :: " + is);

        if(null!=is) {
            try {
                _properties.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Properties are loaded from [" + QUOTE_FILE_NAME + "] with # " + _properties.size() + " entries");
    }

    public static void printProps() {
        System.out.println(_properties );
    }
}
