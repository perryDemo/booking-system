package com.agoda.clone.agoda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingView {
    private int propertyid;
    private String propertyname;
    private int star;
    private String address;
    private String country;
    private String state;
    private String city;
    private String area;
    private String roomname;
    private double hightestrating;
    private String highesttitle;
    private long totalreviews;
    private double totalrating;
    private int guest;
    private double price;
}
