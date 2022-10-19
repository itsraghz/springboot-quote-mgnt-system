package com.raghsonline.qms.util;

public class PropertyUtilTest {

    public static void main(String[] args) {
        PropertyUtil propertyUtil = PropertyUtil.getInstance();
        System.out.println("PropertyUtil has been loaded successfully");
        PropertyUtil.printProps();
    }
}
