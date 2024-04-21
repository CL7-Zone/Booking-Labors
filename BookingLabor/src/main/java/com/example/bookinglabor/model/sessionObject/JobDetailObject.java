package com.example.bookinglabor.model.sessionObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


@Setter
@Getter
@AllArgsConstructor
public class JobDetailObject implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nameJob;
    private double price;
    private String imageJob;
    private String description;
    private String categoryName;
    private Long labor_id;
}
