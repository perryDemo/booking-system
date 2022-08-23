package com.agoda.clone.agoda.service;

import com.agoda.clone.agoda.dto.BookingRequest;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface BookingService {
    public ResponseEntity<String> makeBooking(BookingRequest bookingRequest);
    public Map<String, Object> getMyBooking(String type,int page, int size);
}
