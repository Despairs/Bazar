package org.investsoft.bazar.action.common;

/**
 * Created by Despairs on 21.01.16.
 */
public class AsyncResult {

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
