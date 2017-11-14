package com.pitechplus.rcim.backofficetests.booking;

import com.pitechplus.qautils.annotations.TestInfo;
import com.pitechplus.rcim.BackendAbstract;
import com.pitechplus.rcim.backoffice.data.enums.BookingState;
import com.pitechplus.rcim.backoffice.data.enums.BookingStatusType;
import com.pitechplus.rcim.backoffice.dto.booking.BookingCreateDto;
import com.pitechplus.rcim.backoffice.dto.booking.BookingDto;
import com.pitechplus.rcim.backoffice.dto.booking.StartBookingDto;
import com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders;
import com.rits.cloning.Cloner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders.buildCreateBooking;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by dgliga on 28.08.2017.
 */
public class ReplaceBookingTests extends BackendAbstract {


	private String newBookingId;

	protected  BookingCreateDto bookingCreateDto;
	private BookingDto bookingDto;
	private String bookingId;
	@BeforeClass
	public void CreateBooking() {
		bookingCreateDto = buildCreateBooking(rcimTestData.getMemberLoginEmail(),
				rcimTestData.getMemberVehicleId(),  rcimTestData.getMemberAutomationParking());
		bookingDto = bookingService.createBooking1(rcimTestData.getMemberToken(), bookingCreateDto).getBody();
		bookingId=bookingDto.getId().toString();
		newBookingId=bookingId;
	}

	@Test(dataProvider = "replaceBookings", description = "This test verifies that request to replace booking works accordingly."
			,dependsOnMethods="updateBookingTest")
	@TestInfo(expectedResult = "Initial Booking is Canceled and new Booking is created with the information provided in " +
			"the replace booking request.")
	public void replaceBookingTest(BookingCreateDto bookingCreateDto) {

		BookingDto bookingAfterReplace = bookingService.replaceBooking(rcimTestData.getMemberToken(), bookingId,
				bookingCreateDto).getBody();
		newBookingId = bookingAfterReplace.getId().toString();

		bookingAfterReplace.getCarSharingInfo().setDelayed(false);        
		assertThat("Initial booking was not cancelled!", bookingService.getBooking(rcimTestData.getMemberToken(),
				UUID.fromString(bookingId)).getBody().getStatus(), is(BookingStatusType.CANCELED));
		bookingId=newBookingId;

	}

	@Test(dataProvider = "updateBookings", description = "This test verifies that request to replace booking works accordingly.")
	@TestInfo(expectedResult = "Initial Booking is Canceled and new Booking is created with the information provided in " +
			"the replace booking request.")
	public void updateBookingTest(BookingCreateDto bookingCreateDto) {
		BookingDto bookingAfterReplace = bookingService.updateBooking(rcimTestData.getMemberToken(), bookingId,
				bookingCreateDto).getBody();
		newBookingId = bookingAfterReplace.getId().toString();
		assertThat("The UUID has been changed",bookingId,is(newBookingId));

	}

	@Test(dependsOnMethods="replaceBookingTest",
			description = "This test verifies that cancel booking request works accordingly")
	@TestInfo(expectedResult = "Booking is cancelled and status changes to CANCELLED and state to PAST.")
	public void cancelBookingTest() {


		 BookingDto cancelledBooking = bookingService.cancelBooking(rcimTestData.getMemberToken(),
				 newBookingId).getBody();
		assertThat("Booking status not changed to CANCELLED!", cancelledBooking.getStatus(), is(BookingStatusType.CANCELED));
		assertThat("Booking state not changed to PAST!", cancelledBooking.getState(), is(BookingState.PAST));
	}

	/*@AfterClass
	public void cancelBooking() {
		bookingService.cancelBooking(rcimTestData.getMemberToken(), newBookingId);
	}*/

	@DataProvider
	private Object[][] replaceBookings() {

		Cloner cloningMachine = new Cloner();
		BookingCreateDto earlierBooking = buildCreateBooking(rcimTestData.getMemberLoginEmail(), rcimTestData.getMemberVehicleId(),
				rcimTestData.getMemberAutomationParking());
		earlierBooking.getStart().setDate(LocalDateTime.now().plusMinutes(5).withSecond(0).withNano(0).toString()+":00Z");
		earlierBooking.getEnd().setDate(LocalDateTime.now().plusMinutes(65).withSecond(0).withNano(0).toString()+":00Z");
		BookingCreateDto laterBooking = cloningMachine.deepClone(earlierBooking);
		laterBooking.getStart().setDate(LocalDateTime.now().plusMinutes(20).withSecond(0).withNano(0).toString()+":00Z");
		laterBooking.getEnd().setDate(LocalDateTime.now().plusMinutes(80).withSecond(0).withNano(0).toString()+":00Z");

		return new Object[][]{
			{earlierBooking},
			{laterBooking}
		};
	}

	@DataProvider
	private Object[][] updateBookings() {

		Cloner cloningMachine = new Cloner();
		BookingCreateDto earlierBooking = buildCreateBooking(rcimTestData.getMemberLoginEmail(), rcimTestData.getMemberVehicleId(),
				rcimTestData.getMemberAutomationParking());
		earlierBooking.getStart().setDate(LocalDateTime.now().plusMinutes(25).withSecond(0).withNano(0).toString()+":00Z");
		earlierBooking.getEnd().setDate(LocalDateTime.now().plusMinutes(90).withSecond(0).withNano(0).toString()+":00Z");

		return new Object[][]{
			{earlierBooking},
			
		};
	}


}
