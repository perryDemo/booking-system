package com.agoda.clone.agoda.model;

import java.time.Instant;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean paynow;
    private int quantity;
    private String contact;
    private int guest;
    private int children;
    private Date checkin;
    private Date checkout;
    private String residence;
    private String Createdby;
    private Instant Createdat;
    private String Modifiedby;
    private Instant Modifiedat;
    private String Deletedby;
    private Instant Deletedat;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    @ToString.Exclude
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offerid")
    @ToString.Exclude
    private Offer offer;

    @JsonBackReference
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy = "booking")
    private Reviews reviews;
    
    @JsonBackReference
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy = "booking")
    private Payment payment;
/*
    @JsonBackReference
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy = "booking")
    private BookingPeriod bookingPeriod;*/
}
