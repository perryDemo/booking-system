package com.agoda.clone.agoda.service;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.agoda.clone.agoda.dto.BookingRequest;
import com.agoda.clone.agoda.mapper.BookingMapper;
import com.agoda.clone.agoda.model.Booking;
import com.agoda.clone.agoda.model.Offer;
import com.agoda.clone.agoda.model.Payment;
import com.agoda.clone.agoda.paypal.PaypalProvider;
import com.agoda.clone.agoda.repository.BookingRepository;
import com.agoda.clone.agoda.repository.OfferRepository;
import com.agoda.clone.agoda.repository.PaymentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private PaypalProvider paypalProvider;
    private OfferRepository offerRepository;
    private BookingRepository bookingRepository;
    private BookingMapper bookingMapper;
    private AccountService accountService;
    private PaymentRepository paymentRepository;
    @Override
    public ResponseEntity<?> createOrder(int offerId, int quantity, int night) {
        Offer offer = offerRepository.findById(offerId);
        if(offer.getQuantity()>=quantity){
            return ResponseEntity.ok().body(paypalProvider.createOrder(offerId, offer.getPrice()*quantity*night));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @Override
    public ResponseEntity<?> capturePayment(BookingRequest bookingRequest) {
        // TODO Auto-generated method stub
        Offer offer = offerRepository.findById(bookingRequest.getOfferid());
        if(offer.getQuantity()>=bookingRequest.getQuantity()){
            JSONObject jsonRS = new JSONObject(paypalProvider.capturePayment(bookingRequest.getOrderid()));
            if(jsonRS.get("status").equals("COMPLETED")){                                                 
                Booking booking = bookingMapper.mapToBooking(bookingRequest, accountService.getCurrentUser().getEmail());
                booking.setOffer(offer);
                booking.setUser(accountService.getCurrentUser());
                bookingRepository.save(booking);
                String payerId = jsonRS.getJSONObject("payer").getString("payer_id");
                String paymentId = jsonRS.getJSONArray("purchase_units").getJSONObject(0)
                                                                            .getJSONObject("payments")
                                                                            .getJSONArray("captures")
                                                                            .getJSONObject(0)
                                                                            .getString("id");
                double grossAmount = jsonRS.getJSONArray("purchase_units").getJSONObject(0)
                                                                            .getJSONObject("payments")
                                                                            .getJSONArray("captures")
                                                                            .getJSONObject(0)
                                                                            .getJSONObject("seller_receivable_breakdown")
                                                                            .getJSONObject("gross_amount")
                                                                            .getDouble("value");
                double paypalFee = jsonRS.getJSONArray("purchase_units").getJSONObject(0)
                                                                            .getJSONObject("payments")
                                                                            .getJSONArray("captures")
                                                                            .getJSONObject(0)
                                                                            .getJSONObject("seller_receivable_breakdown")
                                                                            .getJSONObject("paypal_fee")
                                                                            .getDouble("value");
                Payment payment = new Payment();
                payment.setOrderid(bookingRequest.getOrderid());
                payment.setGrossamount(grossAmount);
                payment.setPayerid(payerId);
                payment.setPaymentid(paymentId);
                payment.setBooking(booking);
                payment.setPaypalfee(paypalFee);
                payment.setCreatedby(accountService.getCurrentUser().getEmail());
                payment.setCreatedat(java.time.Instant.now());
                payment.setModifiedby(accountService.getCurrentUser().getEmail());
                payment.setModifiedat(java.time.Instant.now());
                payment.setDeletedby("");
                payment.setDeletedat(java.time.Instant.ofEpochSecond(0));
                paymentRepository.save(payment);   
                return ResponseEntity.ok().body("Payment success! Your Transaction ID is "+paymentId+". Please see the detail in My booking.");  
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }   
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    
}
