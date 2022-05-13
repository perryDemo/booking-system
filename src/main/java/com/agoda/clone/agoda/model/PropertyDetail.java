package com.agoda.clone.agoda.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name="propertydetail")
public class PropertyDetail {
    @Id
    @Column(name = "propertyid")
    private int ID;
    private String Smoking;
    private int Lounges;
    private int Floors;
    private int Restaurants;
    private int Rooms;
    private int Voltage;
    private int Open;
    private int Renovation;
    private String Createdby;
    private Instant Createdat;
    private String Modifiedby;
    private Instant Modifiedat;
    private String Deletedby;
    private Instant Deletedat;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propertyid")
    @MapsId
    @ToString.Exclude
    private Property property;
}
