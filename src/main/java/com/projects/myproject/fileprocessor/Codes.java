package com.projects.myproject.fileprocessor;

public enum Codes {

    OK ("00.Result.OK"),
    NOT_FOUND ("01.Result.NotFound"),
    ERROR ("02.Result.Error");

    private String title;

    Codes(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
