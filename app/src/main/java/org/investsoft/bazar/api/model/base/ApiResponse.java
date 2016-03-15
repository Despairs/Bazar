package org.investsoft.bazar.api.model.base;

/**
 * Created by Despairs on 17.01.16.
 */
public class ApiResponse {

    protected int code;
    protected String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
