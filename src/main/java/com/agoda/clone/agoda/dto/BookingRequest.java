package com.agoda.clone.agoda.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    private String Createdat;
    private String Modifiedby;
    private int guest;
    private int children;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date checkin;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date checkout;
    private String residence;
    private String orderid;
}
