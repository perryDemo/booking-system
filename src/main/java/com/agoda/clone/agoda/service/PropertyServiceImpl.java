package com.agoda.clone.agoda.service;

import java.util.List;
import java.util.stream.Collectors;

import com.agoda.clone.agoda.dto.PropertyRequest;
import com.agoda.clone.agoda.dto.PropertyResponse;
import com.agoda.clone.agoda.mapper.PropertyMapper;
import com.agoda.clone.agoda.model.Breakfast;
import com.agoda.clone.agoda.model.Policy;
import com.agoda.clone.agoda.model.Property;
import com.agoda.clone.agoda.model.PropertyDetail;
import com.agoda.clone.agoda.model.Restaurant;

import com.agoda.clone.agoda.repository.PropertyRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class PropertyServiceImpl implements PropertyService{

    private PropertyRepository propertyRepository;
    private PropertyMapper propertyMapper;

    @Override
    public ResponseEntity<String> save(PropertyRequest propertyRequest){

        Property property = propertyMapper.map(propertyRequest);
        if(propertyRequest.getRestaurant() != null){
            List<Restaurant> restaurants = propertyMapper.mapToRestaurants(propertyRequest.getRestaurant());
            for(Restaurant restaurant:restaurants){
                restaurant.setProperty(property);
            }
            property.setRestaurant(restaurants);

        }
        if(propertyRequest.getBreakfast() != null){
            List<Breakfast> breakfasts = propertyMapper.mapToBreakfasts(propertyRequest.getBreakfast());
            for(Breakfast breakfast:breakfasts){
                breakfast.setProperty(property);
            }
            property.setBreakfast(breakfasts);
        }
        Policy policy = propertyMapper.mapPolicy(propertyRequest);
        policy.setProperty(property);
        PropertyDetail propertyDetail = propertyMapper.mapPropertyDetail(propertyRequest);
        propertyDetail.setProperty(property);
        property.setPropertyDetail(propertyDetail);
        property.setPolicy(policy);
        propertyRepository.save(property);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PropertyResponse> fetchAll(){
        return (List<PropertyResponse>)
            propertyRepository.findAll().stream().map(propertyMapper::mapToResponse).collect(Collectors.toList());
    }


}
