package com.pitechplus.rcim.backofficetests.booking;

import com.pitechplus.qautils.annotations.TestInfo;
import com.pitechplus.rcim.BackendAbstract;
import com.pitechplus.rcim.backoffice.constants.ErrorMessages;
import com.pitechplus.rcim.backoffice.data.enums.BookingState;
import com.pitechplus.rcim.backoffice.data.enums.BookingStatusType;
import com.pitechplus.rcim.backoffice.dto.booking.BookingCreateDto;
import com.pitechplus.rcim.backoffice.dto.booking.BookingDto;
import com.pitechplus.rcim.backoffice.dto.booking.BookingDto;
import com.pitechplus.rcim.backoffice.dto.booking.StartBookingDto;
import com.pitechplus.rcim.backoffice.utils.custommatchers.ExceptionMatcher;
import com.pitechplus.rcim.backoffice.utils.exceptions.BackOfficeException;
import com.pitechplus.rcim.backoffice.utils.mappers.ExceptionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.UUID;

import static com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders.buildCreateBooking;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by dgliga on 28.08.2017.
 */
public class CancelBookingTests extends BackendAbstract {
	protected  BookingCreateDto bookingCreateDto;
	private BookingDto bookingDto;
	private String bookingId;
	@BeforeClass
	public void CreateBooking() {
		bookingCreateDto = buildCreateBooking(rcimTestData.getMemberLoginEmail(),
				rcimTestData.getMemberVehicleId(),  rcimTestData.getMemberAutomationParking());
		bookingDto = bookingService.createBooking1(rcimTestData.getMemberToken(), bookingCreateDto).getBody();

	}

	@Test(description = "This test verifies that cancel booking request works accordingly")
	@TestInfo(expectedResult = "Booking is cancelled and status changes to CANCELLED and state to PAST.")
	public void cancelBookingTest() {


		 BookingDto cancelledBooking = bookingService.cancelBooking(rcimTestData.getMemberToken(),
				 bookingDto.getId().toString()).getBody();
		assertThat("Booking status not changed to CANCELLED!", cancelledBooking.getStatus(), is(BookingStatusType.CANCELED));
		assertThat("Booking state not changed to PAST!", cancelledBooking.getState(), is(BookingState.PAST));
	}


}
