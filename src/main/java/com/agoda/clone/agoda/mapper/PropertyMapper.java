package com.agoda.clone.agoda.mapper;

import com.agoda.clone.agoda.dto.BreakfastDto;
import com.agoda.clone.agoda.dto.PropertyRequest;
import com.agoda.clone.agoda.dto.PropertyResponse;
import com.agoda.clone.agoda.dto.RestaurantDto;
import com.agoda.clone.agoda.model.Breakfast;
import com.agoda.clone.agoda.model.Policy;
import com.agoda.clone.agoda.model.Property;
import com.agoda.clone.agoda.model.PropertyDetail;
import com.agoda.clone.agoda.model.Restaurant;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PropertyMapper {
    
    @Mapping(target = "ID", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "cancellation", source = "cancellation")
    @Mapping(target = "distanceFromCity", source = "distanceFromCity")
    @Mapping(target = "distanceFromAirport", source = "distanceFromAirport")
    @Mapping(target = "checkInStart", source = "checkInStart")
    @Mapping(target = "checkInEnd", source = "checkInEnd")
    @Mapping(target = "checkOut", source = "checkOut")
    @Mapping(target = "announcements", source = "announcements")
    @Mapping(target = "star", source = "star")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "buildingFloorUnit", source = "buildingFloorUnit")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "zip", source = "zip")
    @Mapping(target = "createdby", source = "createdby")
    @Mapping(target = "createdat" , expression  = "java(java.time.Instant.now())")
    @Mapping(target = "modifiedby", source = "modifiedby")
    @Mapping(target = "modifiedat" , expression  = "java(java.time.Instant.now())")
    @Mapping(target = "deletedby", constant = "")
    @Mapping(target = "deletedat", expression = "java(java.time.Instant.ofEpochSecond(0))")
    Property map(PropertyRequest propertyRequest);

    @Mapping(target = "smoking", source = "propertyRequest.smoking")
    @Mapping(target = "lounges", source = "propertyRequest.lounges")
    @Mapping(target = "floors", source = "propertyRequest.floors")
    @Mapping(target = "restaurants", source = "propertyRequest.restaurants")
    @Mapping(target = "rooms", source = "propertyRequest.rooms")
    @Mapping(target = "voltage", source = "propertyRequest.voltage")
    @Mapping(target = "open", source = "propertyRequest.open")
    @Mapping(target = "renovation", source = "propertyRequest.renovation")
    @Mapping(target = "createdby", source = "propertyRequest.createdby")
    @Mapping(target = "createdat" , expression  = "java(java.time.Instant.now())")
    @Mapping(target = "modifiedby", source = "propertyRequest.modifiedby")
    @Mapping(target = "modifiedat" , expression  = "java(java.time.Instant.now())")
    @Mapping(target = "deletedby", constant = "")
    @Mapping(target = "deletedat", expression = "java(java.time.Instant.ofEpochSecond(0))")
    PropertyDetail mapPropertyDetail(PropertyRequest propertyRequest);

    @Mapping(target = "infant", source = "infant")
    @Mapping(target = "infantextra", source = "infantextra")
    @Mapping(target = "children", source = "children")
    @Mapping(target = "childrenextra", source = "childrenextra")
    @Mapping(target = "other", source = "other")
    @Mapping(target = "createdby", source = "createdby")
    @Mapping(target = "createdat" , expression  = "java(java.time.Instant.now())")
    @Mapping(target = "modifiedby", source = "modifiedby")
    @Mapping(target = "modifiedat" , expression  = "java(java.time.Instant.now())")
    @Mapping(target = "deletedby", constant = "")
    @Mapping(target = "deletedat", expression = "java(java.time.Instant.ofEpochSecond(0))")
    Policy mapPolicy(PropertyRequest propertyRequest);

    @Mapping(target = "name", source = "breakfastDto.name")
    @Mapping(target = "createdby" , source = "breakfastDto.createdby")
    @Mapping(target = "createdat" , expression  = "java(java.time.Instant.now())")
    @Mapping(target = "modifiedby" , source = "breakfastDto.modifiedby")
    @Mapping(target = "modifiedat" , expression  = "java(java.time.Instant.now())")
    @Mapping(target = "deletedby", constant = "")
    @Mapping(target = "deletedat", expression = "java(java.time.Instant.ofEpochSecond(0))")
    Breakfast mapToBreakfast(BreakfastDto breakfastDto);

    @Mapping(target = "name", source = "restaurantDto.name")
    @Mapping(target = "cuisine" , source = "restaurantDto.cuisine")
    @Mapping(target = "menu" , source = "restaurantDto.menu")
    @Mapping(target = "createdby" , source = "restaurantDto.createdby")
    @Mapping(target = "createdat" , expression  = "java(java.time.Instant.now())")
    @Mapping(target = "modifiedby" , source = "restaurantDto.modifiedby")
    @Mapping(target = "modifiedat" , expression  = "java(java.time.Instant.now())")
    @Mapping(target = "deletedby", constant = "")
    @Mapping(target = "deletedat", expression = "java(java.time.Instant.ofEpochSecond(0))")
    Restaurant mapToRestaurant(RestaurantDto restaurantDto);
    
    @Mapping(target = "name", source = "name")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "cancellation", source = "cancellation")
    @Mapping(target = "distanceFromCity", source = "distanceFromCity")
    @Mapping(target = "distanceFromAirport", source = "distanceFromAirport")
    @Mapping(target = "checkInStart", source = "checkInStart")
    @Mapping(target = "checkInEnd", source = "checkInEnd")
    @Mapping(target = "checkOut", source = "checkOut")
    @Mapping(target = "announcements", source = "announcements")
    @Mapping(target = "star", source = "star")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "buildingFloorUnit", source = "buildingFloorUnit")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "zip", source = "zip")
    @Mapping(target = "propertyDetail", source = "property.propertyDetail")
    @Mapping(target = "restaurant", source = "property.restaurant")
    @Mapping(target = "breakfast", source = "property.breakfast")
    PropertyResponse mapToResponse(Property property);

    List<Restaurant> mapToRestaurants(List<RestaurantDto> restaurantDto);

    List<Breakfast> mapToBreakfasts(List<BreakfastDto> breakfastDto);
}
