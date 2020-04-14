package com.kevin.jevil.devl.models;

import com.kevin.jevil.devl.models.CodeLocation;

public class DevilFailedRequest {
    private CodeLocation codeLocation;
    private  String url;
    private String errorMessage;

    public DevilFailedRequest(CodeLocation codeLocation, String url,String errorMessage) {
        this.codeLocation = codeLocation;
        this.url = url;
        this.errorMessage = errorMessage;
    }

    public CodeLocation getCodeLocation() {
        return codeLocation;
    }

    public void setCodeLocation(CodeLocation codeLocation) {
        this.codeLocation = codeLocation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
