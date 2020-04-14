package com.kevin.jevil.devl.models;

public class DevilException {
    private String type;
    private String message;
    private CodeLocation codeLocation;
    private String exception;

    public DevilException(String type, String message, CodeLocation codeLocation, String exception) {
        this.type = type;
        this.message = message;
        this.codeLocation = codeLocation;
        this.exception = exception;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CodeLocation getCodeLocation() {
        return codeLocation;
    }

    public void setCodeLocation(CodeLocation codeLocation) {
        this.codeLocation = codeLocation;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
