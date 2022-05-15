package com.agoda.clone.agoda.controller;

import java.util.List;

import com.agoda.clone.agoda.dto.PropertyRequest;
import com.agoda.clone.agoda.dto.PropertyResponse;
import com.agoda.clone.agoda.service.PropertyService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
