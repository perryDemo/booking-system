package com.agoda.clone.agoda.service;

import java.util.List;

import com.agoda.clone.agoda.dto.PropertyRequest;
import com.agoda.clone.agoda.dto.PropertyResponse;

public interface PropertyService {

    void save(PropertyRequest propertyRequest);
    List<PropertyResponse> fetchAll();
}
