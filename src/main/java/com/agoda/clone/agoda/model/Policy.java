package com.agoda.clone.agoda.model;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

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
public class Policy {
    
    @Id
    @Column(name = "propertyid")
    private int ID;
    private String Infant;
    private String Infantextra;
    private String Children;
    private String Childrenextra;
    private String Other;
    private String Createdby;
    private Instant Createdat;
    private String Modifiedby;
    private Instant Modifiedat;
    private String Deletedby;
    private Instant Deletedat;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "propertyid")
    @MapsId
    @ToString.Exclude
    private Property property;
}
