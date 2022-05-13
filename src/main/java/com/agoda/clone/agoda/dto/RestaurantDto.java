package com.agoda.clone.agoda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {
    private int id;
    private String name;
    private String cuisine;
    private String menu;
    private String Createdby;
    private String Modifiedby;
}
