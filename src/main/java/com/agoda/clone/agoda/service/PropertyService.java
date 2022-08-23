package com.agoda.clone.agoda.service;


import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.agoda.clone.agoda.dto.PropertyDetailsDto;
import com.agoda.clone.agoda.dto.PropertyRequest;
import com.agoda.clone.agoda.dto.PropertyResponse;

import org.springframework.http.ResponseEntity;

public interface PropertyService {

    ResponseEntity<String> save(PropertyRequest propertyRequest);
    List<PropertyResponse> fetchAll();
    PropertyDetailsDto findHotelByID(int id);
    Map<String, Object> searchHotel(int guest, int quantity, String city, String checkin, String checkout, int page, int size) throws ParseException;
}
