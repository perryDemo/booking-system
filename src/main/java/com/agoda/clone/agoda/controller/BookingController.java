package com.agoda.clone.agoda.controller;

import com.agoda.clone.agoda.dto.BookingRequest;
import com.agoda.clone.agoda.dto.BookingView;
import com.agoda.clone.agoda.service.BookingService;
import com.agoda.clone.agoda.service.OfferService;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingController {
    private BookingService bookingService;
    private OfferService offerService;
    @PostMapping("/makeBooking")
    public ResponseEntity<?> saveBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingService.makeBooking(bookingRequest);
    }

    @GetMapping("/bookingPreview/{offerID}")
    public BookingView getBookingPreview(@PathVariable int offerID){
        return offerService.getBookingPreview(offerID);
    }

    @GetMapping("/mybooking/{type}")
    public Map<String, Object> getMybookingData(@PathVariable String type, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return bookingService.getMyBooking(type, page-1, size);
    } 
}
