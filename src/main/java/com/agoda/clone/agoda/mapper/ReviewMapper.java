package com.agoda.clone.agoda.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.agoda.clone.agoda.dto.ReviewDto;
import com.agoda.clone.agoda.model.Booking;
import com.agoda.clone.agoda.model.Reviews;
import com.agoda.clone.agoda.model.User;

import org.mapstruct.Builder;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ReviewMapper {
    @Mapping(target = "reviewid", source = "review.id")
    @Mapping(target = "cleanliness", source = "review.cleanliness")
    @Mapping(target = "facilities", source = "review.facilities")
    @Mapping(target = "location", source = "review.location")
    @Mapping(target = "room", source = "review.room")
    @Mapping(target = "service", source = "review.service")
    @Mapping(target = "value", source = "review.value")
    @Mapping(target = "title", source = "review.title")
    @Mapping(target = "propertyid", source = "review.propertyid")
    @Mapping(target = "createdat", source = "review.createdat")
    @Mapping(target = "guest", source = "booking.guest")
    @Mapping(target = "checkin", source = "booking.checkin")
    @Mapping(target = "checkout", source = "booking.checkout")
    @Mapping(target = "firstname", source = "user.firstname")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "countrycode", source = "user.countrycode")
    ReviewDto maptoReviewDto(Reviews review, Booking booking, User user);
}