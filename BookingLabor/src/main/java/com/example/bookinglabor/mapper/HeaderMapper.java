package com.example.bookinglabor.mapper;

import com.example.bookinglabor.model.Header;

import java.util.logging.Handler;

public class HeaderMapper {

    public static Header mapToHeader(Header header){

        return Header.builder()
                .id(header.getId())
                .name(header.getName())
                .content(header.getContent())
                .build();
    }
}
