package com.news.voicenews.error.exception;

public class ObjectNotFoundException extends ValidatorException{

    public ObjectNotFoundException(final String fieldName){
        super("Not found", fieldName);
    }
}
