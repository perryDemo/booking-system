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
    private String Name;
    private String Type;
    private String Description;
    private String Cancellation;
    @Column(name="distancefromcity")
    private String DistanceFromCity;
    @Column(name="distancefromairport")
    private String DistanceFromAirport;
    @Column(name="checkinstart")
    private String CheckInStart;
    @Column(name="checkinend")
    private String CheckInEnd;
    @Column(name="checkout")
    private String CheckOut;
    private String Announcements;
    private String Star;
    private String Address;
    @Column(name="buildingfloorunit")
    private String BuildingFloorUnit;
    private String Country;
    private String State;
    private String City;
    private String Zip;
    private String Createdby;
    private Instant Createdat;
    private String Modifiedby;
    private Instant Modifiedat;
    private String Deletedby;
    private Instant Deletedat;

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
