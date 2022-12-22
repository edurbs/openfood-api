package com.edurbs.openfood.domain.exception;

public abstract class EntityNotFoundException extends BusinessException {

    protected EntityNotFoundException(String msg) {
        super( msg);
    }

    protected EntityNotFoundException(String msg, Throwable causa) {
        super(msg, causa);
    }

}
