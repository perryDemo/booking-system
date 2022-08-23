package com.agoda.clone.agoda.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MybookingViewResponse {
    private String propertyName;
    private String checkInTime;
    private String checkOutTime;
    private int star;
    private String address;
    private String area;
    private String city;
    private String state;
    private String country;
    private double lat;
    private double lng;
    private int quantity;
    private int guest;
    private Date checkInDate;
    private Date checkOutDate;
    private String orderId;
    private double amount;
}
