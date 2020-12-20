package com.eltropy.bank.util;

import lombok.Data;
import org.apache.logging.log4j.Level;

@Data
public class CustomException extends Exception {

    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String errorMessage;
    private transient Level level = Level.INFO;

    public CustomException(String errorCode, String errorMessage, Level level) {

        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.level = level;
    }

    public CustomException(String errorCode, String errorMessage) {
        this(errorCode, errorMessage, Level.INFO);
    }

}
