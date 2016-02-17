package org.investsoft.bazar.api.model;

/**
 * Created by Despairs on 17.01.16.
 */
public class ApiException extends Exception {

    private int code;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
