package com.agoda.clone.agoda.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.agoda.clone.agoda.dto.PropertyDetailsDto;
import com.agoda.clone.agoda.dto.PropertyRequest;
import com.agoda.clone.agoda.dto.PropertyResponse;

import com.agoda.clone.agoda.service.PropertyService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/property")
@AllArgsConstructor
public class PropertyController {
    
    private PropertyService propertyService;

    @PostMapping("/saveProperty")
    public ResponseEntity<String> saveProperty(@RequestBody PropertyRequest property) {
        return propertyService.save(property);
    }
    
    @GetMapping("/getProperties")
    public List<PropertyResponse> fetchAll (){
        return propertyService.fetchAll();
    }
    
    @GetMapping("getProperty/{id}")
    public PropertyDetailsDto fetchHotel (@PathVariable int id){
        return propertyService.findHotelByID(id);
    }
    @GetMapping("searchHotelByCity/{guest}/{quantity}/{city}/{checkin}/{checkout}")
    public Map<String, Object> searchHotel (@PathVariable int guest,@PathVariable int quantity,
        @PathVariable String city, @PathVariable String checkin, @PathVariable String checkout,
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) throws ParseException{
        return propertyService.searchHotel(guest,quantity,city,checkin,checkout,page,size);
    }

}
