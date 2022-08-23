package com.agoda.clone.agoda.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.agoda.clone.agoda.dto.ReviewDto;
import com.agoda.clone.agoda.mapper.ReviewMapper;
import com.agoda.clone.agoda.model.Reviews;
import com.agoda.clone.agoda.repository.ReviewRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    @Override
    public Map<String, Object> findByPropertyID(int propertyid, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        List<ReviewDto> reviews = new ArrayList<ReviewDto>();
        Page<Reviews> pageReview = reviewRepository.findByPropertyid(propertyid, paging);
        reviews = pageReview.getContent().stream().map(review->reviewMapper.maptoReviewDto(review, review.getBooking(), review.getBooking().getUser())).collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviews);
        response.put("currentPage", pageReview.getNumber());
        response.put("totalItems", pageReview.getTotalElements());
        response.put("totalPages", pageReview.getTotalPages());
        return response;
    }
    
}
