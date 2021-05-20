package com.news.voicenews.error.exception;

public class InvalidJwtToken
        extends ValidatorException {

    public InvalidJwtToken() {
        super("Invalid jwt token", "access_token");
    }

}
