package com.agoda.clone.agoda.service;

import com.agoda.clone.agoda.dto.BookingRequest;
import com.agoda.clone.agoda.mapper.BookingMapper;
import com.agoda.clone.agoda.model.Offer;
import com.agoda.clone.agoda.repository.BookingRepository;
import com.agoda.clone.agoda.repository.OfferRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService{
    private BookingRepository bookingRepository;
    private OfferRepository offerRepository;
    private BookingMapper bookingMapper;
    private AccountService accountService;
    @Override
    public ResponseEntity<String> makeBooking(BookingRequest bookingRequest) {
        // TODO Auto-generated method stub
        Offer offer = offerRepository.findById(bookingRequest.getOfferid());
        if(offer.getQuantity()>0){
            offer.setQuantity(offer.getQuantity()-bookingRequest.getQuantity());
            offerRepository.save(offer);
            bookingRepository.save(bookingMapper.mapToBooking(bookingRequest, offer, accountService.getCurrentUser()));
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        
    }
    
}
