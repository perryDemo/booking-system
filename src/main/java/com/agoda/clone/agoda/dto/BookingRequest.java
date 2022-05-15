package com.agoda.clone.agoda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private boolean paynow;
    private int quantity;
    private String contact;
    private int offerid;
    private String Createdby;
    private String Modifiedby;

}
