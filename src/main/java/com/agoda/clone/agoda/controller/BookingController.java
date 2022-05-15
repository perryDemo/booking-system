package com.agoda.clone.agoda.controller;

import com.agoda.clone.agoda.dto.BookingRequest;
import com.agoda.clone.agoda.service.BookingService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingController {
    private BookingService bookingService;

    @PostMapping("/makeBooking")
    public void saveBooking(@RequestBody BookingRequest bookingRequest) {
        bookingService.makeBooking(bookingRequest);
    }
}
