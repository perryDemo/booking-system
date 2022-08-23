package com.agoda.clone.agoda.service;

import com.agoda.clone.agoda.dto.BookingView;

public interface OfferService {
    BookingView getBookingPreview(int offerid);
}
