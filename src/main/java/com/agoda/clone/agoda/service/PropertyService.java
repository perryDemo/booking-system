package com.agoda.clone.agoda.service;

import java.util.List;

import com.agoda.clone.agoda.dto.PropertyRequest;
import com.agoda.clone.agoda.dto.PropertyResponse;

import org.springframework.http.ResponseEntity;

public interface PropertyService {

    ResponseEntity<String> save(PropertyRequest propertyRequest);
    List<PropertyResponse> fetchAll();
}
