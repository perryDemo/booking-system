package com.agoda.clone.agoda.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agoda.clone.agoda.dto.BookingRequest;
import com.agoda.clone.agoda.service.OrderService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/paypal")
@AllArgsConstructor
public class PaypalController {

    private OrderService orderService;

    @PostMapping("/orders/{offerId}/{quantity}/{night}")
    public ResponseEntity<?> findReviewByPropertyID(@PathVariable int offerId, @PathVariable int quantity, @PathVariable int night){
        return orderService.createOrder(offerId,quantity,night);
    }

    @PostMapping("/capturePayment")
    public ResponseEntity<?> capturePayment(@RequestBody BookingRequest bookingRequest){
        return orderService.capturePayment(bookingRequest);
    }
}
