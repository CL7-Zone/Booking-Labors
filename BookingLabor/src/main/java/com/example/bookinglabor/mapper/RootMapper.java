package com.example.bookinglabor.mapper;


import com.example.bookinglabor.model.Root;

import java.util.Map;

public class RootMapper {


    public static Root mapToRoot(Map<String, Object> map){

        if(map != null){
            Root root = new Root();
            root.setEmail((String) map.get("email"));
            root.setName((String) map.get("name"));

        }
        return null;
    }
}
