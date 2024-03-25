package com.example.bookinglabor.model.sessionObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class BookingObject implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nameJob;
    private String imageJob;
    private String labor_name;
    private double price;
    private String categoryName;
    private String city_name;
    private Long labor_id;
}
