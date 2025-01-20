package org.api.util;

public class Constants {
    public static final String BASE_URI = "https://restful-booker.herokuapp.com";
    public static final String BOOKING_SERVICE_ENDPOINT = "/booking";
    public static final String AUTH_ENDPOINT = "/auth";
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "password123";
    public static final String UPDATE_STRING = "{\n" +
            "    \"firstname\" : \"James\",\n" +
            "    \"lastname\" : \"Brown\",\n" +
            "    \"totalprice\" : 111,\n" +
            "    \"depositpaid\" : true,\n" +
            "    \"bookingdates\" : {\n" +
            "        \"checkin\" : \"2018-01-01\",\n" +
            "        \"checkout\" : \"2019-01-01\"\n" +
            "    },\n" +
            "    \"additionalneeds\" : \"Breakfast\"\n" +
            "}";
}
