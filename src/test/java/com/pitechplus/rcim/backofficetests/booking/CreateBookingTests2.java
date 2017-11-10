package com.pitechplus.rcim.backofficetests.booking;

import com.pitechplus.rcim.BackendAbstract;
import com.pitechplus.rcim.backoffice.data.enums.BookingState;
import com.pitechplus.rcim.backoffice.data.enums.BookingStatusType;
import com.pitechplus.rcim.backoffice.dto.booking.BookingCreateDto;
import com.pitechplus.rcim.backoffice.dto.booking.BookingCustomValues;
import com.pitechplus.rcim.backoffice.dto.booking.BookingDto;
import com.pitechplus.rcim.backoffice.dto.booking.BookingDto1;
import com.pitechplus.rcim.backoffice.dto.booking.StartBookingDto;
import com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders;
import com.pitechplus.rcim.backoffice.utils.custommatchers.ExceptionMatcher;
import com.pitechplus.rcim.backoffice.utils.exceptions.BackOfficeException;
import com.pitechplus.rcim.backoffice.utils.mappers.ExceptionMapper;
import com.rits.cloning.Cloner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static com.pitechplus.rcim.backoffice.data.enums.ValidationError.*;
import static com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders.buildCreateBooking;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by dgliga on 24.08.2017.
 */
public class CreateBookingTests2 extends BackendAbstract {

	
	@Test(dependsOnGroups="Booking")
	public void startBookingTest() {
		StartBookingDto bookingDto = mobileService.startBooking(rcimTestData.getMemberToken(), CreateBookingTests.bookingId).getBody();
		Cloner cloner= new Cloner();
		StartBookingDto cloningBookingDto=cloner.deepClone(bookingDto);
		cloningBookingDto.getVehicle().setId(CreateBookingTests.bookingCreateDto.getVehicle().getId());
		cloningBookingDto.getStart().setDate(CreateBookingTests.bookingCreateDto.getStart().getDate());
		cloningBookingDto.getEnd().setDate(CreateBookingTests.bookingCreateDto.getEnd().getDate());
		cloningBookingDto.setStatus(BookingStatusType.IN_PROGRESS);
		cloningBookingDto.setState(BookingState.UPCOMING);
		assertThat("Booking was not created correctly!", bookingDto,
				is(cloningBookingDto));
	}
	
	@Test(dependsOnMethods="startBookingTest")
	public void  lockDoorBookingTest() {
		int lockDoor= mobileService.lockDoorBooking(rcimTestData.getMemberToken(), CreateBookingTests.bookingId);
		assertThat("The response code for locking door is not in the range 200",lockDoor,is(204));
	}
	
	@Test(dependsOnMethods="lockDoorBookingTest")
	public void  unlockDoorBookingTest() {
		int unlockDoor= mobileService.unlockDoorBooking(rcimTestData.getMemberToken(), CreateBookingTests.bookingId);
		assertThat("The response code for locking door is not in the range 200",unlockDoor,is(204));
	}
	

	/*  @Test
    public void createCustomBookingTest() {
        BookingDto bookingDto = bookingService.createBooking(rcimTestData.getSuperAdminToken(), bookingCreateDto1).getBody();
        bookingService.cancelBooking(rcimTestData.getSuperAdminToken(), bookingDto.getId());
        bookingDto.getCarSharingInfo().setDelayed(false);
        bookingCreateDto1.setBookingCustomValues(null);
        assertThat("Booking was not created correctly!", bookingDto,
                is(DtoBuilders.buildExpectedBooking(bookingCreateDto1, rcimTestData.getAutomationVehicle())));
    }




   @Test(description = "This test verifies that request create booking with invalid X-AUTH-TOKEN triggers correct error " +
            "response from server.")
    @TestInfo(expectedResult = "Server responds with 401 Unauthorized with message: " + ErrorMessages.INVALID_AUTHENTICATION_TOKEN)
    public void invalidTokenTest() throws IOException {
        try {
            bookingService.createBooking(rcimTestData.getSuperAdminToken() + "INVALID", bookingCreateDto);
            Assert.fail("Create Booking worked with invalid X-AUTH-TOKEN!");
        } catch (HttpStatusCodeException exception) {
            //verify that error received from server is the correct one
            assertThat("Server did not throw correct error!", ExceptionMapper.mapException(exception, BackOfficeException.class),
                    ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.UNAUTHORIZED, ErrorMessages.INVALID_AUTHENTICATION_TOKEN,
                            null, null));
        }
    }

    @Test(description = "This test verifies that request to create booking with missing mandatory fields triggers correct error " +
            "response from server.")
    @TestInfo(expectedResult = "Server responds with 400 Bad Request with validationErrors for fields type, start and reserved seats.")
    public void missingMandatoryFieldsTest() throws IOException {
        try {
            bookingService.createBooking(rcimTestData.getSuperAdminToken(), new BookingCreateDto());
            Assert.fail("Create Booking worked with missing mandatory fields!");
        } catch (HttpStatusCodeException exception) {
            //verify that error received from server is the correct one
            assertThat("Server did not throw correct error!", ExceptionMapper.mapException(exception, BackOfficeException.class),
                    ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            null, ValidationErrorsBuilder.buildValidationErrors(ServiceCalled.BOOKING_CREATE, TYPE_MAY_NOT_BE_NULL,
                                    START_MAY_NOT_BE_NULL, RESERVED_SEATS_MAY_NOT_BE_NULL)));
        }
    }

    @Test(description = "This test verifies that request to create booking with invalid member login triggers correct error " +
            "response from server.")
    @TestInfo(expectedResult = "Server responds with 400 Bad Request with developerMessage: No member found for email: {invalidEmail}")
    public void invalidMemberLoginTest() throws IOException {
        BookingCreateDto invalidMemberLogin = cloningMachine.deepClone(bookingCreateDto);
        invalidMemberLogin.setMemberLogin(bookingCreateDto.getMemberLogin() + "INVALID");
        try {
            bookingService.createBooking(rcimTestData.getSuperAdminToken(), invalidMemberLogin);
            Assert.fail("Create Booking worked with invalid X-AUTH-TOKEN!");
        } catch (HttpStatusCodeException exception) {
            //verify that error received from server is the correct one
            assertThat("Server did not throw correct error!", ExceptionMapper.mapException(exception, BackOfficeException.class),
                    ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(),
                            "No member found for email: " + invalidMemberLogin.getMemberLogin(), null));
        }
    }*/


}
