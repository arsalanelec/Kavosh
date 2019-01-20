package com.example.arsalan.kavosh.model;

import java.util.List;

public class RetroResponse {
    String status;
    List<String> errorList;

    public RetroResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }
}
