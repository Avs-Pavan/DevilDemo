package com.kevin.jevil.devl.models;

import com.kevin.jevil.devl.models.CodeLocation;

public class DevilLog {
    private String type;
    private String message;
    private CodeLocation codeLocation;

    public DevilLog(String type, String message, CodeLocation codeLocation) {
        this.type = type;
        this.message = message;
        this.codeLocation = codeLocation;
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
}
