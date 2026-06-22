package com.artprintpoint.app.utils;

public class AppConstants {

    private AppConstants() {}

    // Status
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";

    // Messages
    public static final String USER_CREATED = "User registered successfully";
    public static final String LOGIN_SUCCESS = "Login successful";
    public static final String LOGOUT_SUCCESS = "Logout successful";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String USER_NOT_FOUND = "User not found";

    // Error Codes
    public static final String AUTH_INVALID = "AUTH_001";
    public static final String USER_NOT_FOUND_CODE = "USR_001";

    // Roles
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STAFF = "STAFF";
    public static final String ROLE_USER = "END_USER";

    // Payment Status
    public static final String PAYMENT_PENDING = "PENDING";
    public static final String PAYMENT_PAID = "PAID";
    public static final String PAYMENT_FAILED = "FAILED";

    // Payment Methods
    public static final String PAY_CASH = "CASH";
    public static final String PAY_UPI = "UPI";
    public static final String PAY_CARD = "CARD";
    public static final String PAY_ONLINE = "ONLINE";

    // Inventory Movement
    public static final String MOVEMENT_IN = "IN";
    public static final String MOVEMENT_OUT = "OUT";
}
