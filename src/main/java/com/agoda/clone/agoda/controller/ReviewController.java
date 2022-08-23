package com.agoda.clone.agoda.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agoda.clone.agoda.service.ReviewService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/review")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/getReviews/{propertyid}")
    public Map<String,Object> findReviewByPropertyID(@PathVariable int propertyid, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return reviewService.findByPropertyID(propertyid, page, size);
    }
}
