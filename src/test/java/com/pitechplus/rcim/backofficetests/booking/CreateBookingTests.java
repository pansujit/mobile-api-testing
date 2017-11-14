package com.pitechplus.rcim.backofficetests.booking;

import com.pitechplus.rcim.BackendAbstract;
import com.pitechplus.rcim.backoffice.data.enums.BookingState;
import com.pitechplus.rcim.backoffice.data.enums.BookingStatusType;
import com.pitechplus.rcim.backoffice.dto.booking.BookingCreateDto;
import com.pitechplus.rcim.backoffice.dto.booking.BookingCustomValues;
import com.pitechplus.rcim.backoffice.dto.booking.BookingDto;
import com.pitechplus.rcim.backoffice.dto.booking.StartBookingDto;
import com.pitechplus.rcim.backoffice.dto.booking.feedback.FeedbackDto;
import com.pitechplus.rcim.backoffice.dto.booking.search.BookingResultDto;
import com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders;

import com.rits.cloning.Cloner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.UUID;

import static com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders.buildCreateBooking;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

/**
 * Created by dgliga on 24.08.2017.
 */
public class CreateBookingTests extends BackendAbstract {

	private  BookingCreateDto bookingCreateDto;
	private BookingDto bookingDto;
	//private BookingCustomValues bookingCustomValues;
	public static String bookingId = new String();

	@BeforeClass
	public void prepareBookingCreate() {
		// the start time of booking has been modified to work
		bookingCreateDto = buildCreateBooking(rcimTestData.getMemberLoginEmail(), rcimTestData.getMemberVehicleId(),
				rcimTestData.getMemberAutomationParking());

	}

	@Test(description = "This test verifies the creation of booking using mobile")
	public void createBookingTest() {
		bookingDto = bookingService.createBooking1(rcimTestData.getMemberToken(), bookingCreateDto).getBody();
		bookingId = bookingDto.getId().toString();
		Cloner cloner = new Cloner();
		BookingDto cloningBookingDto = cloner.deepClone(bookingDto);
		cloningBookingDto.getVehicle().setId(bookingCreateDto.getVehicle().getId());
		cloningBookingDto.getStart().setDate(bookingCreateDto.getStart().getDate());
		cloningBookingDto.getEnd().setDate(bookingCreateDto.getEnd().getDate());
		cloningBookingDto.setState(BookingState.UPCOMING);
		cloningBookingDto.setStatus(BookingStatusType.SCHEDULED);
		assertThat("Booking was not created correctly!", bookingDto, is(cloningBookingDto));
	}

	@Test(dependsOnMethods = "createBookingTest", description = "This test verifies that start the booking using mobile ")
	public void startBookingTest() {
		StartBookingDto bookingDto = bookingService
				.startBooking(rcimTestData.getMemberToken(), CreateBookingTests.bookingId).getBody();
		Cloner cloner = new Cloner();
		StartBookingDto cloningBookingDto = cloner.deepClone(bookingDto);
		cloningBookingDto.getVehicle().setId(bookingCreateDto.getVehicle().getId());
		cloningBookingDto.getStart().setDate(bookingCreateDto.getStart().getDate());
		cloningBookingDto.getEnd().setDate(bookingCreateDto.getEnd().getDate());
		cloningBookingDto.setStatus(BookingStatusType.IN_PROGRESS);
		cloningBookingDto.setState(BookingState.UPCOMING);
		assertThat("Booking was not created correctly!", bookingDto, is(cloningBookingDto));
	}

	@Test(dependsOnMethods = "startBookingTest", description = "This test verifies the lock the door of the "
			+ "vehicle in in-progress booking")
	public void lockDoorBookingTest() {
		int lockDoor = bookingService.lockDoorBooking(rcimTestData.getMemberToken(), CreateBookingTests.bookingId);
		assertThat("The response code for locking door is not in the range 200", lockDoor, is(204));
	}

	@Test(dependsOnMethods = "lockDoorBookingTest", description = "This test verifies the unlock the door of the "
			+ "vehicle in in-progress booking")
	public void unlockDoorBookingTest() {
		int unlockDoor = bookingService.unlockDoorBooking(rcimTestData.getMemberToken(), CreateBookingTests.bookingId);
		assertThat("The response code for locking door is not in the range 200", unlockDoor, is(204));
	}

	@Test(dependsOnMethods = { "startBookingTest", "unlockDoorBookingTest" }, description = "This test verifies"
			+ "adding feedback in the current in-progress booking")
	public void feedbackBookingTest() {
		String fileId = configsService.mobileCreateFile(rcimTestData.getMemberToken(), DtoBuilders.buildFile())
				.getBody().getId().toString();
		FeedbackDto x = DtoBuilders.bookingFeedback(fileId);
		FeedbackDto ResultFeedback = bookingService
				.feedbackBooking(rcimTestData.getMemberToken(), UUID.fromString(bookingId), x).getBody();
		assertThat("The booking doesnot registered the correct feedback", ResultFeedback, is(x));
	}

	@Test(dependsOnMethods = "feedbackBookingTest", description = "This test verifies the finishe booking using mobile")
	public void FinishBookingTest() {
		BookingDto finishingBookingResult = bookingService
				.finishBooking(rcimTestData.getMemberToken(), UUID.fromString(bookingId), DtoBuilders.finishBooking())
				.getBody();
		assertThat("The state of the finished booking is not changed", finishingBookingResult.getState(),
				is(BookingState.PAST));
		assertThat("The status of the finished booking is not changed", finishingBookingResult.getStatus(),
				is(BookingStatusType.COMPLETED));
	}

	@Test(dependsOnMethods = "createBookingTest", description = "This test verifies that thelist of member's booking")
	public void GetAllBookingsOfMemberTest() throws InterruptedException {

		BookingResultDto y = bookingService
				.memberBooking(rcimTestData.getMemberToken(), rcimTestData.getMemberId(), 1, 200, "ASC").getBody();
		Thread.sleep(5000);
		assertThat("The booking is not in the list", y.getResults(), hasItem(bookingDto));
	}

	@Test(dependsOnMethods = "createBookingTest", description = "This test verifies that thelist of member's booking")
	public void memberFilterBooking() {
		BookingResultDto x = bookingService.memberFilterBooking(rcimTestData.getMemberToken(),
				rcimTestData.getMemberId(), DtoBuilders.MemberBookingSearchFilter(rcimTestData.getMemberId()))
				.getBody();

		assertThat("The booking newly created booking is not listed", x.getResults(), hasItem(bookingDto));

	}

}
