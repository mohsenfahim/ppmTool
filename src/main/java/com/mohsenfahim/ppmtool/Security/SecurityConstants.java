package com.mohsenfahim.ppmtool.Security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
<<<<<<< HEAD
    public static final long EXPIRATION_TIME = 300_000; //300 seconds = 5 minutes;
=======
    public static final long EXPIRATION_TIME = 3000000; //300 seconds = 5 minutes;
>>>>>>> 31998d21cb048e84587fcc93ac29f962ba904ecc
}
