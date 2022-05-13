package com.agoda.clone.agoda.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyRequest {

    private int id;
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
    private String Createdby;
    private String Createdat;
    private String Modifiedby;
    private String Modifiedat;
    private String Deletedby;
    private String Deletedat;

    private String Smoking;
    private int Lounges;
    private int Floors;
    private int Restaurants;
    private int Rooms;
    private int Voltage;
    private int Open;
    private int Renovation;

    private String Infant;
    private String Infantextra;
    private String Children;
    private String Childrenextra;
    private String Other;

    private List<RestaurantDto> restaurant;
    private List<BreakfastDto> breakfast;
}
