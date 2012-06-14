package com.rcs.common;

import java.io.Serializable;


/**
 *
 * @author Prj.M@x <pablo.rendon@rotterdam-cs.com>
 */
public class LocalResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private boolean success = false;
    private int responseCode;
    private String message;
    private String body;

    public LocalResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
    
}
