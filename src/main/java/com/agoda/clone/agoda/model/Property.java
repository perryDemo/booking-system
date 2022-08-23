package com.agoda.clone.agoda.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Property {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;
    private String type;
    private String description;
    @Column(name="shortdescription")
    private String shortDescription;
    private double lat;
    private double lng;
    @Column(name="checkinstart")
    private String checkInStart;
    @Column(name="checkinend")
    private String checkInEnd;
    @Column(name="checkout")
    private String checkOut;
    private String announcements;
    private String star;
    @Column(name="buildingfloorunit")
    private String buildingFloorUnit;
    private String address;
    private String country;
    private String state;
    private String city;
    private String area;
    private String zip;
    private String createdby;
    private Instant createdat;
    private String modifiedby;
    private Instant modifiedat;
    private String deletedby;
    private Instant deletedat;

    @JsonManagedReference
    @PrimaryKeyJoinColumn
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy = "property")
    private PropertyDetail propertyDetail;

    @JsonManagedReference
    @PrimaryKeyJoinColumn
    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "property")
    private Policy policy;

    @JsonManagedReference
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "property")
    private List<Restaurant> restaurant;

    @JsonManagedReference
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "property")
    private List<Breakfast> breakfast;

    @JsonManagedReference
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "property")
    private List<Room> room;
}
