package com.agoda.clone.agoda.dto;

import java.util.List;

import com.agoda.clone.agoda.model.Breakfast;
import com.agoda.clone.agoda.model.Policy;
import com.agoda.clone.agoda.model.PropertyDetail;
import com.agoda.clone.agoda.model.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyResponse {
    private String Name;
    private String Type;
    private String Description;
    private String Cancellation;
    private String DistanceFromCity;
    private String DistanceFromAirport;
    private String CheckInStart;
    private String CheckInEnd;
    private String CheckOut;
    private String Announcements;
    private String Star;
    private String Address;
    private String BuildingFloorUnit;
    private String Country;
    private String State;
    private String City;
    private String Zip;

    private Policy policy;

    private PropertyDetail propertyDetail;

    private List<Restaurant> restaurant;

    private List<Breakfast> breakfast; 
}
