package com.agoda.clone.agoda.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferCount {
    private Offer Offer;
    private Date CheckIn;
    private long CheckInCount;
    private Date CheckOut;
    private long checkOutCount;
    private long QuantityCount;
}
