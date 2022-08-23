package com.agoda.clone.agoda.service;

import com.agoda.clone.agoda.dto.BookingRequest;
import com.agoda.clone.agoda.dto.MybookingViewResponse;
import com.agoda.clone.agoda.mapper.BookingMapper;
import com.agoda.clone.agoda.model.Booking;
import com.agoda.clone.agoda.model.User;
import com.agoda.clone.agoda.repository.BookingRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private BookingMapper bookingMapper;
    private AccountService accountService;
    @Override
    public ResponseEntity<String> makeBooking(BookingRequest bookingRequest) {
        // TODO Auto-generated method stub
        return new ResponseEntity<>(HttpStatus.OK);        
    }
    @Override
    public Map<String, Object> getMyBooking(String type, int page, int size) {
        // TODO Auto-generated method stub
        Pageable paging = PageRequest.of(page, size);
        User user = accountService.getCurrentUser();
        List<MybookingViewResponse> mybookings = new ArrayList<MybookingViewResponse>();
        Page<Booking> pageMybooking = null;
        if(type.equals("completed"))
            pageMybooking = bookingRepository.findByUserAndCheckoutLessThan(user, new Date(), paging);
        if(type.equals("uncoming"))
            pageMybooking = bookingRepository.findByUserAndCheckoutGreaterThanEqual(user, new Date(), paging);
        mybookings = pageMybooking.getContent().stream()
                .map(mybooking->bookingMapper.mapToMybookingViewResponse(mybooking.getOffer().getRoom().getProperty(), mybooking, mybooking.getPayment()))
                .collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("mybookings", mybookings);
        response.put("currentPage", pageMybooking.getNumber());
        response.put("totalItems", pageMybooking.getTotalElements());
        response.put("totalPages", pageMybooking.getTotalPages());
        return response;
    }
    
}