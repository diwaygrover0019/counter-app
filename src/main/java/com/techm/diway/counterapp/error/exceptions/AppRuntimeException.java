package com.techm.diway.counterapp.error.exceptions;

import com.techm.diway.counterapp.error.ErrorCodes;
import lombok.Getter;

public class AppRuntimeException extends RuntimeException {

    @Getter
    private ErrorCodes errorCodes;

    @Getter
    private Throwable exception;

    public AppRuntimeException(String errorMsg, ErrorCodes errorCodes) {
        super(errorMsg);
        this.errorCodes = errorCodes;
    }

    public AppRuntimeException(String errorMsg, ErrorCodes errorCodes, Throwable ex) {
        super(errorMsg, ex);
        this.errorCodes = errorCodes;
        this.exception = ex;
    }
}
