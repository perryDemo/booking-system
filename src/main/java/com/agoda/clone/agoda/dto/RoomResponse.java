package com.agoda.clone.agoda.dto;

import com.agoda.clone.agoda.model.Property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private int id;
    private String Name;
    private Property property;

}
