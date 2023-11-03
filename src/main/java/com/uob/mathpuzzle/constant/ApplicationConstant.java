package com.uob.mathpuzzle.constant;

public class ApplicationConstant {

    public static final String API_BASE_URL = "/v1";

    public static final int RESOURCE_NOT_FOUND = 404;
    public static final int REQUEST_FAIL = 401;
    public static final int PRECONDITION_FAIL = 412;
    public static final int RESOURCE_ALREADY_EXISTS = 409;
    public static final int TOO_MANY_REQUESTS = 429;
    public static final int PASSWORD_FORMAT_WRONG = 501;
    public static final int EMAIL_DUPLICATE = 502;
    public static final int OPERATION_SUCCESS = 0;
    public static final int OPERATION_FAILED = 1;
    public static final int BAD_REQUEST = 400;

    public static final String APPLICATION_ERROR_OCCURRED_MESSAGE = "Unexpected Error Occurred";
    public static final String SERVICE_ERROR = "Service Error, please contact support!";
    public static final String INVALID_AUTH_PROVIDER = "Invalid auth provider";
    public static final String FORBIDDEN_RESOURCE = "You are not authorized to access this resource!";
}
