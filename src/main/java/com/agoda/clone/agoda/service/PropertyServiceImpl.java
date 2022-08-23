package com.agoda.clone.agoda.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.agoda.clone.agoda.dto.PropertyDetailsDto;
import com.agoda.clone.agoda.dto.PropertyRequest;
import com.agoda.clone.agoda.dto.PropertyResponse;
import com.agoda.clone.agoda.dto.ReviewDto;
import com.agoda.clone.agoda.mapper.PropertyMapper;
import com.agoda.clone.agoda.mapper.ReviewMapper;
import com.agoda.clone.agoda.model.Breakfast;
import com.agoda.clone.agoda.model.Offer;
import com.agoda.clone.agoda.model.OfferCount;
import com.agoda.clone.agoda.model.Policy;
import com.agoda.clone.agoda.model.Property;
import com.agoda.clone.agoda.model.PropertyDetail;
import com.agoda.clone.agoda.model.Restaurant;
import com.agoda.clone.agoda.repository.BookingRepository;
import com.agoda.clone.agoda.repository.OfferRepository;
import com.agoda.clone.agoda.repository.PropertyRepository;
import com.agoda.clone.agoda.repository.ReviewRepository;
import com.agoda.clone.agoda.repository.RoomRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class PropertyServiceImpl implements PropertyService{

    private final PropertyRepository propertyRepository;
    private final RoomRepository roomRepository;
    private final OfferRepository offerRepository;
    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final PropertyMapper propertyMapper;  
    private final ReviewMapper reviewMapper;  
    @Override
    public ResponseEntity<String> save(PropertyRequest propertyRequest){

        Property property = propertyMapper.map(propertyRequest);
        if(propertyRequest.getRestaurant() != null){
            List<Restaurant> restaurants = propertyMapper.mapToRestaurants(propertyRequest.getRestaurant());
            for(Restaurant restaurant:restaurants){
                restaurant.setProperty(property);
            }
            property.setRestaurant(restaurants);

        }
        if(propertyRequest.getBreakfast() != null){
            List<Breakfast> breakfasts = propertyMapper.mapToBreakfasts(propertyRequest.getBreakfast());
            for(Breakfast breakfast:breakfasts){
                breakfast.setProperty(property);
            }
            property.setBreakfast(breakfasts);
        }
        Policy policy = propertyMapper.mapPolicy(propertyRequest);
        policy.setProperty(property);
        PropertyDetail propertyDetail = propertyMapper.mapPropertyDetail(propertyRequest);
        propertyDetail.setProperty(property);
        property.setPropertyDetail(propertyDetail);
        property.setPolicy(policy);
        propertyRepository.save(property);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PropertyResponse> fetchAll(){
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public PropertyDetailsDto findHotelByID(int id) {
        Property property = propertyRepository.findByIDAndDeletedat(id, java.time.Instant.ofEpochSecond(0)).orElseThrow();
        List<ReviewDto> reviews = reviewRepository.findByPropertyid(property.getID()).stream().map(review->reviewMapper.maptoReviewDto(review, review.getBooking(), review.getBooking().getUser())).collect(Collectors.toList());;
        return propertyMapper.mapTpPropertyDetailsDto(property, reviewRepository.countByPropertyid(property.getID()), reviews);
    }
    @Override
    public Map<String, Object> searchHotel(int guest, int quantity, String city, String checkin, String checkout, int page, int size) throws ParseException {
        Date checkIn = new SimpleDateFormat("dd-MM-yyyy").parse(checkin);
        Date checkOut = new SimpleDateFormat("dd-MM-yyyy").parse(checkout);
        Calendar c = Calendar.getInstance();
        c.setTime(checkOut);
        c.add(Calendar.DATE, -1);
        Date checkInEnd =  c.getTime();
        int diffDay = (int) ((checkOut.getTime() - checkIn.getTime())/(1000*24*60*60));

        List<Offer> offers = offerRepository.findByGuestGreaterThanEqual(guest);
        final List<OfferCount> bookings = bookingRepository.findOfferMatchRequire(offers.stream().map(offer->offer.getID()).collect(Collectors.toList()), checkIn, checkInEnd);
        Map<Integer, List<Integer>> availableOfferList = new HashMap<>();
        Map<Integer, Map<String,Object>> roomMap = new HashMap<>();

        offers.forEach((offer)->{
            Map<String, Object> quantityMap = new HashMap<>();
            quantityMap.put("haveRoom", true);
            for(int i = 0 ; i < diffDay ; i++){
                c.setTime(checkIn);
                c.add(Calendar.DATE, i);
                quantityMap.put(c.getTime().toString(), offer.getQuantity());
                roomMap.put(offer.getID(), quantityMap);
            }
        });

        bookings.forEach((booking)->{
            long endDay = 0;
            if(booking.getCheckOut().getTime()< checkOut.getTime())
                endDay = booking.getCheckOut().getTime();
            else
                endDay = checkOut.getTime();

            int diff = (int) ((endDay - booking.getCheckIn().getTime())/(1000*24*60*60));
            Map<String, Object> quantityMap = roomMap.get(booking.getOffer().getID());
            for(int i = 0 ; i < diff ; i++){
                c.setTime(booking.getCheckIn());
                c.add(Calendar.DATE, i);
                int tempObj  = (int) quantityMap.get(c.getTime().toString());
                tempObj =   (int) (tempObj -booking.getQuantityCount());
                quantityMap.put(c.getTime().toString(), tempObj);                
                roomMap.replace(booking.getOffer().getID(), quantityMap);
            }
        });
        roomMap.forEach((key,day)->{
            day.forEach((dayKey, availableQuantity)->{
                if(availableQuantity instanceof Integer){
                    if((int)availableQuantity < quantity)
                    day.replace("haveRoom", false);
                }
            });
            if((boolean) day.get("haveRoom")){
                int propertyId = propertyRepository.findByRoom(roomRepository.findByOffer(offerRepository.findById(key))).getID();
                List<Integer> tempOfferIdList;
                if(availableOfferList.get(propertyId)==null){
                    tempOfferIdList = new ArrayList<>();
                } else {
                    tempOfferIdList = availableOfferList.get(propertyId);
                }
                tempOfferIdList.add(key);
                availableOfferList.put(propertyId,tempOfferIdList);
            }
        });

        List<Integer> rs = new ArrayList<>();
        availableOfferList.forEach((key,item)->{
            Property property = propertyRepository.findByDeletedbyAndIDAndAreaOrCity("",key, city,city);
            if(property!=null)
            rs.add(property.getID());
        });
        Pageable paging = PageRequest.of(page, size);
        List<PropertyResponse> propertyResponse = new ArrayList<PropertyResponse>();
        Page<Property> pageReview = propertyRepository.findByIDIn(rs, paging);
        propertyResponse = pageReview.getContent().stream()
                    .map(property->propertyMapper.mapToResponse(property, reviewRepository.countByPropertyid(property.getID()), offerRepository.findByIDIn(availableOfferList.get(property.getID())),         reviewRepository.findByPropertyid(property.getID()).stream().map(review->reviewMapper.maptoReviewDto(review, review.getBooking(), review.getBooking().getUser())).collect(Collectors.toList())))
                    .collect(Collectors.toList()); 
        Map<String, Object> response = new HashMap<>();
        response.put("property", propertyResponse);
        response.put("currentPage", pageReview.getNumber());
        response.put("totalItems", pageReview.getTotalElements());
        response.put("totalPages", pageReview.getTotalPages());
        return response;
    }
    /* 
    @Override
    public List<PropertyResponse> searchHotel(int guest, int quantity, String city, String checkin, String checkout) throws ParseException {

        Date checkIn = new SimpleDateFormat("dd-MM-yyyy").parse(checkin);
        Date checkOut = new SimpleDateFormat("dd-MM-yyyy").parse(checkout);
        List<Integer> roomsId = new ArrayList<Integer>();
        Calendar c = Calendar.getInstance();
        c.setTime(checkOut);
        c.add(Calendar.DATE, -1);
        Date checkInEnd =  c.getTime();

        List<HotelCount> offer = offerRepository.countOfferById(guest, quantity, checkIn, checkInEnd);
        int diffDay = (int) ((checkOut.getTime() - checkIn.getTime())/(1000*24*60*60));
        Map<Integer, List<Integer>> offerMap = new HashMap<>();
        offer.forEach(offerr->{
            if(diffDay == offerr.getTotal()){
                roomsId.add(offerr.getRoom().getID());
                if(offerMap.containsKey(offerr.getRoom().getProperty().getID())){
                    List<Integer> roomID = offerMap.get(offerr.getRoom().getProperty().getID());
                    roomID.add(offerr.getRoom().getID());
                    offerMap.replace(offerr.getRoom().getProperty().getID(), roomID);
                } else {
                    List<Integer> roomID = new ArrayList<Integer>();
                    roomID.add(offerr.getRoom().getID());
                    offerMap.put(offerr.getRoom().getProperty().getID(), roomID);
                }
            }
        });
        List<Room> rooms = roomRepository.findByIDInGroupByPropertyid(roomsId);
        return (List<PropertyResponse>) propertyRepository.findByRoomInAndCity(rooms, city).stream()
            .map(property->propertyMapper.mapToResponse(property, reviewRepository.countByPropertyid(property.getID()), offerRepository.findByRoomInAndCheckindateBetween(roomRepository.findByIDIn(offerMap.get(property.getID())),checkIn,checkInEnd)))
            .collect(Collectors.toList()); 
    }
*/
}
