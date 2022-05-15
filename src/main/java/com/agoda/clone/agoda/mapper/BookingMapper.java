package com.agoda.clone.agoda.mapper;

import com.agoda.clone.agoda.dto.BookingRequest;
import com.agoda.clone.agoda.model.Booking;
import com.agoda.clone.agoda.model.Offer;
import com.agoda.clone.agoda.model.User;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BookingMapper {
    @Mapping(target = "paynow", source = "bookingRequest.paynow")
    @Mapping(target = "quantity", source = "bookingRequest.quantity")
    @Mapping(target = "contact", source = "bookingRequest.contact")
    @Mapping(target = "offer", source = "offer")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdby", expression = "java(user.getEmail())")
    @Mapping(target = "createdat" , expression  = "java(java.time.Instant.now())")
    @Mapping(target = "modifiedby", expression = "java(user.getEmail())")
    @Mapping(target = "modifiedat" , expression  = "java(java.time.Instant.now())")
    @Mapping(target = "deletedby", constant = "")
    @Mapping(target = "deletedat", expression = "java(java.time.Instant.ofEpochSecond(0))")
    Booking mapToBooking(BookingRequest bookingRequest, Offer offer, User user);
}
