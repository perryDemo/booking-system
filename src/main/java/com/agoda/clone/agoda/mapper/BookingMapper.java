package com.agoda.clone.agoda.mapper;

import com.agoda.clone.agoda.dto.BookingRequest;
import com.agoda.clone.agoda.dto.BookingView;
import com.agoda.clone.agoda.dto.MybookingViewResponse;
import com.agoda.clone.agoda.model.Booking;
import com.agoda.clone.agoda.model.Offer;
import com.agoda.clone.agoda.model.Payment;
import com.agoda.clone.agoda.model.Property;
import com.agoda.clone.agoda.model.Room;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BookingMapper {
    @Mapping(target = "paynow", source = "bookingRequest.paynow")
    @Mapping(target = "quantity", source = "bookingRequest.quantity")
    @Mapping(target = "contact", constant = "")
    @Mapping(target = "guest", source = "bookingRequest.guest")
    @Mapping(target = "children", constant = "0")
    @Mapping(target = "checkin", source = "bookingRequest.checkin")
    @Mapping(target = "checkout", source = "bookingRequest.checkout")
    @Mapping(target = "residence", constant = "")
    @Mapping(target = "createdby", expression = "java(username)")
    @Mapping(target = "createdat" , expression  = "java(java.time.Instant.now())")
    @Mapping(target = "modifiedby", expression = "java(username)")
    @Mapping(target = "modifiedat" , expression  = "java(java.time.Instant.now())")
    @Mapping(target = "deletedby", constant = "")
    @Mapping(target = "deletedat", expression = "java(java.time.Instant.ofEpochSecond(0))")
    Booking mapToBooking(BookingRequest bookingRequest, String username);

    @Mapping(target = "propertyid", source ="property.ID")
    @Mapping(target = "propertyname", source = "property.name")
    @Mapping(target = "star", source = "property.star")
    @Mapping(target = "address", source = "property.address")
    @Mapping(target = "country", source = "property.country")
    @Mapping(target = "state", source = "property.state")
    @Mapping(target = "city", source = "property.city")
    @Mapping(target = "area", source = "property.area")
    @Mapping(target = "roomname", source = "room.name")
    @Mapping(target = "hightestrating", source = "hightestrating")
    @Mapping(target = "highesttitle", source = "highesttitle")
    @Mapping(target = "totalreviews", source = "totalreviews")
    @Mapping(target = "totalrating", source = "totalrating")
    @Mapping(target = "guest", source = "offer.guest")
    @Mapping(target = "price", source = "offer.price")
    BookingView mapToBookingView(Offer offer, Property property, Room room, double hightestrating, String highesttitle, long totalreviews, double totalrating);

    @Mapping(target = "propertyName", source = "property.name")
    @Mapping(target = "checkInTime", source = "property.checkInStart")
    @Mapping(target = "checkOutTime", source = "property.checkOut")
    @Mapping(target = "star", source = "property.star")
    @Mapping(target = "address", source = "property.address")
    @Mapping(target = "area", source = "property.area")
    @Mapping(target = "city", source = "property.city")
    @Mapping(target = "state", source = "property.state")
    @Mapping(target = "country", source = "property.country")
    @Mapping(target = "lat", source = "property.lat")
    @Mapping(target = "lng", source = "property.lng")
    @Mapping(target = "quantity", source = "booking.quantity")
    @Mapping(target = "guest", source = "booking.guest")
    @Mapping(target = "checkInDate", source = "booking.checkin")
    @Mapping(target = "checkOutDate", source = "booking.checkout")
    @Mapping(target = "orderId", source = "payment.orderid")
    @Mapping(target = "amount", source = "payment.grossamount")
    MybookingViewResponse mapToMybookingViewResponse(Property property, Booking booking, Payment payment);
}
