package com.projects.myproject.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Data
@Entity(name="request_storage")
public class RequestStorage {

    public RequestStorage(){};

    public RequestStorage(@NonNull String code, @NonNull Long number, String fileName, String error) {
        this.code = code;
        this.number = number;
        this.fileName = fileName;
        this.error = error;
    }

    @Id
    @NonNull
    private Long id;
    @NonNull
    private String code;
    @NonNull
    private Long number;
    private String fileName;
    private String error;
}
