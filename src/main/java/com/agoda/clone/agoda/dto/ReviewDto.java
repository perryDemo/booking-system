package com.agoda.clone.agoda.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReviewDto {
    private int reviewid;
    private double cleanliness;
    private double facilities;
    private double location;
    private double room;
    private double service;
    private double value;
    private String title;
    private String content;
    private int propertyid;
    private Instant createdat;
    private int guest;
    private Instant checkin;
    private Instant checkout;
    private String firstname;
    private String phone;
    private String countrycode;
}
