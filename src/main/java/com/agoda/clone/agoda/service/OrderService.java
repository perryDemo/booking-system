package com.agoda.clone.agoda.service;

import org.springframework.http.ResponseEntity;

import com.agoda.clone.agoda.dto.BookingRequest;

public interface OrderService {
    ResponseEntity<?> createOrder(int offerId, int quantity, int night);
    ResponseEntity<?> capturePayment(BookingRequest bookingRequest);
}
