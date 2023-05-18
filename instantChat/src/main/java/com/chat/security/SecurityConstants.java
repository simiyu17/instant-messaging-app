package com.chat.security;

/**
 *
 * @author simiyu
 */
public class SecurityConstants {

    private SecurityConstants(){}

    public static final String SECRET = "SecretKeyToGenJSecretKeyToGenJWTsHDKSGdiendhGSHDUEjshskdghKSHDGHDWTsHDKSGdiendhGSHDUSecretKeyToGenJWTsHDKSGdiendhGSHDUEjshskdghKSHDGHDEjshskdghKSHDGHD";
    public static final long EXPIRATION_TIME = 60_000 * 100L; // 10 minute
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String DEFAULT_PASSWORD = "We do not use password here!!";
}