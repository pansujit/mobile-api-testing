package com.pitechplus.rcim.backoffice.service;

import com.pitechplus.qautils.restutils.RestExchangeInfo;
import com.pitechplus.qautils.restutils.RestTemplateUtils;
import com.pitechplus.rcim.backoffice.data.enums.ApplicationType;
import com.pitechplus.rcim.backoffice.dto.booking.BookingCreateDto;
import com.pitechplus.rcim.backoffice.dto.booking.BookingDto;
import com.pitechplus.rcim.backoffice.dto.booking.FilteredSearchDto;
import com.pitechplus.rcim.backoffice.dto.booking.StartBookingDto;
import com.pitechplus.rcim.backoffice.dto.booking.feedback.FeedbackDto;
import com.pitechplus.rcim.backoffice.dto.booking.filteredsearch.SearchBookingResponseDto;
import com.pitechplus.rcim.backoffice.dto.booking.finish.FinishBookingInfoDto;
import com.pitechplus.rcim.backoffice.dto.booking.search.BookingQueryDto;
import com.pitechplus.rcim.backoffice.dto.booking.search.BookingResultDto;
import com.pitechplus.rcim.backoffice.dto.booking.search.SearchBookingDto;
import com.pitechplus.rcim.backoffice.utils.exceptions.BackOfficeException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by dgliga on 26.09.2017.
 */

@Component("booking-controller")
public class BookingService {

    @Value("${services.gw.admin}")
    private String gwAdminBaseUrl;
    
    @Value("${services.gw.mobile}")
    private String gwMobileBaseUrl;
 
    @Value("${booking.search.filtered}")
    private String searchFiltered;

    @Value("${bookings}")
    private String bookings;

    @Value("${cancel.booking}")
    private String cancelBooking;

    @Value("${get.booking}")
    private String getBooking;

    @Value("${replace.booking}")
    private String replaceBooking;

    @Value("${search.bookings}")
    private String searchBookings;

    @Value("${finish.booking}")
    private String finishBooking;

    @Value("${shortened.extend.booking}")
    private String shortenedExtendBooking;
    
    @Value("${booking.update}")
    private String updateBooking;
    
    @Value("${feedback.booking}")
    private String feedbackBooking;
    
    @Value("${member.booking.search}")
    private String memberBookingSearch;
    
    @Value("${door.lock}")
    private String lockDoorVehicleBooking;
    
    @Value("${door.unlock}")
    private String unlockDoorVehicleBooking;
    
    @Value("${start.booking}")
    private String startBooking;
    
    @Value("${member.filter.booking}")
    private String memberFilterBooking;
   
    
    
    private RestTemplateUtils restTemplateUtils = new RestTemplateUtils(15000, 15000);


    public ResponseEntity<SearchBookingResponseDto> searchFilteredBookings(String xAuthToken, SearchBookingDto filteredSearchDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity<SearchBookingDto> searchFilteredHttpEntity = new HttpEntity<>(filteredSearchDto, headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Filtered search for bookings for member: ")
                .url(gwMobileBaseUrl + searchFiltered)
                .httpMethod(HttpMethod.POST)
                .requestBody(searchFilteredHttpEntity)
                .response(SearchBookingResponseDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }


    
    public ResponseEntity<BookingDto> createBooking1(String xAuthToken, BookingCreateDto bookingCreateDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity<BookingCreateDto> bookingCreateDtoHttpEntity = new HttpEntity<>(bookingCreateDto, headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Create booking for member: " + bookingCreateDto.getMemberLogin())
                .url(gwMobileBaseUrl + bookings)
                .httpMethod(HttpMethod.POST)
                .requestBody(bookingCreateDtoHttpEntity)
                .response(BookingDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }

    public ResponseEntity<BookingDto> cancelBooking(String xAuthToken, String bookingId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity cancelBookingHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Cancel booking with id: " + bookingId)
                .url(gwMobileBaseUrl + cancelBooking)
                .httpMethod(HttpMethod.POST)
                .uriVariables(bookingId)
                .requestBody(cancelBookingHttpEntity)
                .response(BookingDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }

    public ResponseEntity<BookingDto> getBooking(String xAuthToken, UUID bookingId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity getBookingHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Get booking with id: " + bookingId)
                .url(gwMobileBaseUrl + getBooking)
                .httpMethod(HttpMethod.GET)
                .uriVariables(bookingId)
                .requestBody(getBookingHttpEntity)
                .response(BookingDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }

    public ResponseEntity<BookingDto> updateBooking(String xAuthToken, String bookingId, BookingCreateDto bookingCreateDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity<BookingCreateDto> updateBookingHttpEntity = new HttpEntity<>(bookingCreateDto, headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: update booking with id: " + bookingId)
                .url(gwMobileBaseUrl + updateBooking)
                .httpMethod(HttpMethod.POST)
                .uriVariables(bookingId)
                .requestBody(updateBookingHttpEntity)
                .response(BookingDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public ResponseEntity<BookingDto> replaceBooking(String xAuthToken, String bookingId, BookingCreateDto bookingCreateDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity<BookingCreateDto> replaceBookingHttpEntity = new HttpEntity<>(bookingCreateDto, headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Replace booking with id: " + bookingId)
                .url(gwMobileBaseUrl + replaceBooking)
                .httpMethod(HttpMethod.POST)
                .uriVariables(bookingId)
                .requestBody(replaceBookingHttpEntity)
                .response(BookingDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }

    public ResponseEntity<BookingResultDto[]> searchBookings(String xAuthToken, SearchBookingDto bookingQueryDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_BO.toString());
        HttpEntity<SearchBookingDto> searchBookingHttpEntity = new HttpEntity<>(bookingQueryDto, headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Search Bookings")
                .url(gwAdminBaseUrl + searchBookings)
                .httpMethod(HttpMethod.POST)
                .requestBody(searchBookingHttpEntity)
                .response(BookingResultDto[].class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }

    public ResponseEntity<BookingDto> finishBooking(String xAuthToken, UUID bookingId,
    		FinishBookingInfoDto finishBookingInfoDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity<FinishBookingInfoDto> finishBookingHttpEntity = new HttpEntity<>(finishBookingInfoDto,headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: finish booking with id: " + bookingId)
                .url(gwMobileBaseUrl + finishBooking)
                .httpMethod(HttpMethod.POST)
                .uriVariables(bookingId)
                .requestBody(finishBookingHttpEntity)
                .response(BookingDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public ResponseEntity<FeedbackDto> feedbackBooking(String xAuthToken, UUID bookingId,
    		FeedbackDto feedbackDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity<FeedbackDto> finishBookingHttpEntity = new HttpEntity<>(feedbackDto,headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: feedback for booking with id: " + bookingId)
                .url(gwMobileBaseUrl + feedbackBooking)
                .httpMethod(HttpMethod.POST)
                .uriVariables(bookingId)
                .requestBody(finishBookingHttpEntity)
                .response(FeedbackDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public ResponseEntity<BookingResultDto> memberBooking(String xAuthToken, String memberId, 
    		int page,int size, String asc) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity finishBookingHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: get the booking of the member with Id: " + memberId)
                .url(gwMobileBaseUrl + memberBookingSearch)
                .httpMethod(HttpMethod.GET)
                .uriVariables(memberId,page,size,asc)
                .requestBody(finishBookingHttpEntity)
                .sleepTimeAfterRequestInSec(5)
                .response(BookingResultDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public ResponseEntity<StartBookingDto> startBooking(String xAuthToken, String bookingId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("Content-Type", " application/json");
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity startBookingHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Start booking with id: " + bookingId)
                .url(gwMobileBaseUrl + startBooking)
                .httpMethod(HttpMethod.POST)
                .uriVariables(bookingId)
                .requestBody(startBookingHttpEntity)
                .response(StartBookingDto.class)
                .build();
       return  restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public int lockDoorBooking(String xAuthToken, String bookingId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("Content-Type", " application/json");
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity startBookingHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Locking vehicle of having with id: " + bookingId)
                .url(gwMobileBaseUrl + lockDoorVehicleBooking)
                .httpMethod(HttpMethod.POST)
                .uriVariables(bookingId)
                .requestBody(startBookingHttpEntity)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class).getStatusCodeValue();
    }
    
    public int unlockDoorBooking(String xAuthToken, String bookingId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("Content-Type", " application/json");
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity startBookingHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Locking vehicle of having with id: " + bookingId)
                .url(gwMobileBaseUrl + unlockDoorVehicleBooking)
                .httpMethod(HttpMethod.POST)
                .uriVariables(bookingId)
                .requestBody(startBookingHttpEntity)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class).getStatusCodeValue();
    }
    
    public void startBooking(String xAuthToken, UUID bookingId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("Content-Type", " application/json");
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity startBookingHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Start booking with id: " + bookingId)
                .url(gwMobileBaseUrl + startBooking)
                .httpMethod(HttpMethod.POST)
                .uriVariables(bookingId)
                .requestBody(startBookingHttpEntity)
                .build();
        restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public ResponseEntity<BookingResultDto> memberFilterBooking(String xAuthToken, String memberId,
    		BookingQueryDto bookingQueryDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("Content-Type", " application/json");
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity<BookingQueryDto>filterHttpEntity = new HttpEntity<>(bookingQueryDto,headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: filter the booking of the member: " + memberId)
                .url(gwMobileBaseUrl + memberFilterBooking)
                .httpMethod(HttpMethod.POST)
                .uriVariables(memberId)
                .requestBody(filterHttpEntity)
                .response(BookingResultDto.class)
                .build();
       return  restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
   /* public ResponseEntity<BookingDto[]> searchBookings(String xAuthToken, BookingQueryDto bookingQueryDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity<BookingQueryDto> searchBookingHttpEntity = new HttpEntity<>(bookingQueryDto, headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Search Bookings")
                .url(gwMobileBaseUrl + searchBookings)
                .httpMethod(HttpMethod.POST)
                .requestBody(searchBookingHttpEntity)
                .response(BookingDto[].class)
                .sleepTimeAfterRequestInSec(5)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }*/

    
}
