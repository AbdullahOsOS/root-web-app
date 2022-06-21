package com.root.meter.constants;

public class Constants {
    //USERS API
    public static final String prefix = "https://rooot.azurewebsites.net";     //"localhost:9191";
    public static final String USER_API_FIND_BY_NAME = prefix+"/user/find/ByName?name=";
    public static final String READING_API_FIND_CONSUMPTION_BY_USER_ID = prefix+"/get/period?";

    public static final String USER_API_FIND_BY_ID = prefix+"/user/find/ById?id=";
}
