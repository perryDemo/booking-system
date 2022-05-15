package com.agoda.clone.agoda.service;

import com.agoda.clone.agoda.dto.BookingRequest;

import org.springframework.http.ResponseEntity;

public interface BookingService {
    public ResponseEntity<String> makeBooking(BookingRequest bookingRequest);
}
