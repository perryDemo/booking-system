package com.agoda.clone.agoda.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.agoda.clone.agoda.dto.BookingView;
import com.agoda.clone.agoda.mapper.BookingMapper;
import com.agoda.clone.agoda.model.Offer;
import com.agoda.clone.agoda.model.Property;
import com.agoda.clone.agoda.model.Reviews;
import com.agoda.clone.agoda.model.Room;
import com.agoda.clone.agoda.repository.OfferRepository;
import com.agoda.clone.agoda.repository.PropertyRepository;
import com.agoda.clone.agoda.repository.ReviewRepository;
import com.agoda.clone.agoda.repository.RoomRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService{
    private final OfferRepository offerRepository;
    private final RoomRepository roomRepository;
    private final PropertyRepository propertyRepository;
    private final ReviewRepository reviewRepository;

    private final BookingMapper bookingMapper;
    @Override
    public BookingView getBookingPreview(int offerid) {
        Offer offer = offerRepository.findById(offerid);
        Room room = roomRepository.findById(offer.getRoom().getID());
        Property property = propertyRepository.findById(room.getProperty().getID()).orElseThrow();
        List<Reviews> reviews = reviewRepository.findByPropertyid(property.getID()).stream().toList();
        Map<String, Double> Rating = new HashMap<>();
        Rating.put("Cleanliness", 0.0);
        Rating.put("Facilities", 0.0);
        Rating.put("Location", 0.0);
        Rating.put("Room", 0.0);
        Rating.put("Service", 0.0);
        Rating.put("Value", 0.0);
        double totalRating = 0.0;
        for (Reviews review : reviews) {
            Rating.put("Cleanliness", Rating.get("Cleanliness") + review.getCleanliness());
            Rating.put("Facilities", Rating.get("Facilities") + review.getFacilities());
            Rating.put("Location", Rating.get("Location") + review.getLocation());
            Rating.put("Room", Rating.get("Room") + review.getRoom());
            Rating.put("Service", Rating.get("Service") + review.getService());
            Rating.put("Value", Rating.get("Value") + review.getValue());
            totalRating = totalRating + (review.getCleanliness() + review.getFacilities() + review.getLocation() + review.getRoom()+ review.getService()+review.getValue())/6;
        }

        Double max = Collections.max(Rating.values());
        List<Entry<String, Double>> temp = Rating.entrySet().stream()
        .filter(entry -> entry.getValue() == max)
        .map(entry -> entry)
        .collect(Collectors.toList());
        return bookingMapper.mapToBookingView(offer, property, room, temp.get(0).getValue(), temp.get(0).getKey(), reviewRepository.countByPropertyid(property.getID()), totalRating);
    }
    
}
