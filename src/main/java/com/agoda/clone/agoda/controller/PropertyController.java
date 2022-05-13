package com.agoda.clone.agoda.controller;

import java.util.List;

import com.agoda.clone.agoda.dto.PropertyRequest;
import com.agoda.clone.agoda.dto.PropertyResponse;
import com.agoda.clone.agoda.service.PropertyService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PropertyController {
    
    private PropertyService propertyService;

    @PostMapping("property")
    public void saveProperty(@RequestBody PropertyRequest property) {
        propertyService.save(property);
    }
    
    @GetMapping("property")
    public List<PropertyResponse> fetchAll (){
        return propertyService.fetchAll();
    }

}
