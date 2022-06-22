package com.root.meter.constants;

public class Constants {
    //USERS API
    public static final String prefix = "https://rooot.azurewebsites.net";     //"localhost:9191";
    public static final String USER_API_FIND_BY_NAME = prefix+"/user/find/ByName?name=";
    public static final String READING_API_FIND_CONSUMPTION_BY_USER_ID = prefix+"/get/period?";

    public static final String USER_API_FIND_BY_ID = prefix+"/user/find/ById?id=";
    //Meter
    public static final String METER_API_SAVE = prefix+"/meter/save";
    public static final String METER_API_GET_BY_ID = prefix+"/meter/get?meterId=";

    //Payment
    public static final String PAYMENT_API_SAVE = prefix+"/payment/save";
    public static final String GET_LAST_PAYMENT = prefix+"/payment/get/by/userId?userId=";

}
