package com.pitechplus.rcim.backofficetests.booking;

import com.pitechplus.qautils.annotations.TestInfo;
import com.pitechplus.rcim.BackendAbstract;
import com.pitechplus.rcim.backoffice.dto.booking.FilteredSearchDto;
import com.pitechplus.rcim.backoffice.dto.booking.filteredsearch.SearchBookingResponseDto;
import com.pitechplus.rcim.backoffice.dto.booking.search.BookingResultDto;
import com.pitechplus.rcim.backoffice.dto.booking.search.SearchBookingDto;
import com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders;

import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dgliga on 21.08.2017.
 */
public class SearchBookingTests extends BackendAbstract {

	/*  @Test(description = "This test verifies that valid filtered search for a booking works accordingly.")
    @TestInfo(expectedResult = "Search filtered booking returns correct results with vehicle available for the search " +
            "which was performed in order to create a booking.")
    public void filteredSearchTest() {
        SearchBookingDto filteredSearchDto = DtoBuilders.buildFilteredSearch(rcimTestData.getMemberAutomationParking());
        SearchBookingResponseDto searchBookingResponseDto = bookingService.searchFilteredBookings(rcimTestData.getMemberToken(),
                filteredSearchDto).getBody();
        assertThat("There is no search result for vehicle",searchBookingResponseDto.getResults().size(),greaterThan(0));
        assertThat("The vehicle is not search in the booking vehicle search",
        		searchBookingResponseDto.getResults().get(0).getVehicle().getId().toString(),
        		is(rcimTestData.getMemberVehicleId()));
    }
    
    
	@Test(description = "This test verifies that search bookings request by fields: " +
			"booking id, booking functional id, start date and end date works accordingly.")
	@TestInfo(expectedResult = "Search results contain the booking of the member for which teh search was performed.")
	public void searchByFieldsTest() {        
		SearchBookingDto filteredSearchDto = DtoBuilders.buildFilteredSearch(rcimTestData.getMemberAutomationParking());

		BookingResultDto[] bookingResultDto = bookingService.searchBookings(rcimTestData.getMemberToken(),
				filteredSearchDto).getBody();
		List<BookingResultDto> result=Arrays.asList(bookingResultDto);
		//assertThat("Booking returned not correct Corodinates!", result.get(0).getStart().getCoordinates(), 
		//		is(bookingQueryDto.getStart().getAddress().getCoordinates()));
		//assertThat("Booking returned not correct vehicle!", result.get(0).getVehicle().getId().toString(), 
		//		is(rcimTestData.getMemberVehicleId()));
	}*/

   /* @Test(description = "This test verifies that request filtered search bookings with invalid X-AUTH-TOKEN triggers correct error " +
            "response from server.")
    @TestInfo(expectedResult = "Server responds with 401 Unauthorized with message: " + ErrorMessages.INVALID_AUTHENTICATION_TOKEN)
    public void invalidTokenTest() throws IOException {
        try {
            bookingService.searchFilteredBookings(rcimTestData.getSuperAdminToken() + "Invalid",
                    DtoBuilders.buildFilteredSearch(rcimTestData.getMemberDto().getLogin(), rcimTestData.getAutomationSiteId()));
            Assert.fail("Filtered search worked with invalid X-AUTH-TOKEN!");
        } catch (HttpStatusCodeException exception) {
            //verify that error received from server is the correct one
            assertThat("Server did not throw correct error!", ExceptionMapper.mapException(exception, BackOfficeException.class),
                    ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.UNAUTHORIZED, ErrorMessages.INVALID_AUTHENTICATION_TOKEN,
                            null, null));
        }
    }

    @Test(dataProvider = "searchFilteredInvalidFields", description = "This test verifies that call with invalid field values " +
            "returns no search results.")
    @TestInfo(expectedResult = "Search returns no results for invalid value parameters.")
    public void invalidFieldTest(FilteredSearchDto filteredSearchDto) throws IOException {
        SearchBookingResponseDto searchBookingResponseDto = bookingService.searchFilteredBookings(rcimTestData.getSuperAdminToken(),
                filteredSearchDto).getBody();
        assertThat("Search returned results for invalid member login!", searchBookingResponseDto.getResults().size(), is(0));
    }

    @Test(description = "This test verifies that performing search with invalid number of passengers ( o or negative ) triggers " +
            "correct error from server.")
    @TestInfo(expectedResult = "Server responds with 400 Bad Request with developerMessage: At least one passenger is required")
    public void invalidPassengerNumberTest() throws IOException {
        FilteredSearchDto filteredSearchDto = DtoBuilders.buildFilteredSearch(rcimTestData.getMemberDto().getLogin(), rcimTestData.getAutomationSiteId());
        filteredSearchDto.setPassengers(-randInt(0, 5));
        try {
            bookingService.searchFilteredBookings(rcimTestData.getSuperAdminToken(), filteredSearchDto);
            Assert.fail("Filtered search worked with less than 1 passenger!!");
        } catch (HttpStatusCodeException exception) {
            //verify that error received from server is the correct one
            assertThat("Server did not throw correct error!", ExceptionMapper.mapException(exception, BackOfficeException.class),
                    ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            "At least one passenger is required", null));
        }
    }

    @Test(dataProvider = "missingMandatoryField", description = "This test verifies that calling  filtered search for bookings " +
            "with missing mandatory field triggers correct error from server.")
    @TestInfo(expectedResult = "Server responds with 400 Bad Request with developerMessage depending on which field is missing out " +
            "of the following: start date, start site id or passengers.")
    public void missingMandatoryFieldTest(FilteredSearchDto filteredSearchDto, String message) throws IOException {
        try {
            bookingService.searchFilteredBookings(rcimTestData.getSuperAdminToken(), filteredSearchDto);
            Assert.fail("Filtered search worked with one missing mandatory field!!");
        } catch (HttpStatusCodeException exception) {
            //verify that error received from server is the correct one
            assertThat("Server did not throw correct error!", ExceptionMapper.mapException(exception, BackOfficeException.class),
                    ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            message, null));
        }
    }

    @Test(description = "This test verifies that calling filtered search with no member login triggers correct error from server.")
    @TestInfo(expectedResult = "Server responds with 400 Bad Request with validationError: memberLogin in searchBookingDto may not be empty.")
    public void noMemberLoginTest() throws IOException {
        FilteredSearchDto filteredSearchDto = DtoBuilders.buildFilteredSearch(rcimTestData.getMemberDto().getLogin(), rcimTestData.getAutomationSiteId());
        filteredSearchDto.setMemberLogin(null);
        try {
            bookingService.searchFilteredBookings(rcimTestData.getSuperAdminToken(), filteredSearchDto);
            Assert.fail("Filtered search worked with less than 1 passenger!!");
        } catch (HttpStatusCodeException exception) {
            //verify that error received from server is the correct one
            assertThat("Server did not throw correct error!", ExceptionMapper.mapException(exception, BackOfficeException.class),
                    ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            null, ValidationErrorsBuilder.buildValidationErrors(ServiceCalled.SEARCH_BOOKING, MEMBER_LOGIN_MAY_NOT_BE_EMPTY)));
        }
    }

    @Test(description = "This test verifies that calling filtered search with duration which exceeds booking max duration " +
            "of the site works accordingly")
    @TestInfo(expectedResult = "Search returns no result since such a booking is not possible.")
    public void searchExceedsMaxBookingDurationTest() {
        FilteredSearchDto filteredSearchDto = DtoBuilders.buildFilteredSearch(rcimTestData.getMemberDto().getLogin(), rcimTestData.getAutomationSiteId());
        filteredSearchDto.setEndDate(LocalDateTime.now().plusMinutes(rcimTestData.getAutomationParking().getSite().getMaxDurationOfBooking()).plusMonths(1).toString());
        SearchBookingResponseDto searchBookingResponseDto = bookingService.searchFilteredBookings(rcimTestData.getSuperAdminToken(),
                filteredSearchDto).getBody();
        assertThat("Search returned results for period which exceeds max booking duration!",
                searchBookingResponseDto.getResults().size(), is(0));
    }

    @DataProvider
    private Object[][] searchFilteredInvalidFields() {
        Cloner cloningMachine = new Cloner();

        //create object with all fields
        FilteredSearchDto filteredSearchDto = DtoBuilders.buildFilteredSearch(rcimTestData.getMemberDto().getLogin(), rcimTestData.getAutomationSiteId());
        FilteredSearchDto invalidMember = cloningMachine.deepClone(filteredSearchDto);
        invalidMember.setMemberLogin(rcimTestData.getMemberDto().getLogin() + "INVALID");
        FilteredSearchDto invalidStartSiteId = cloningMachine.deepClone(filteredSearchDto);
        invalidStartSiteId.setStartSiteId(UUID.randomUUID().toString());
        FilteredSearchDto invalidEndSiteId = cloningMachine.deepClone(filteredSearchDto);
        invalidEndSiteId.setEndSiteId(UUID.randomUUID().toString());
        FilteredSearchDto invalidPassengers = cloningMachine.deepClone(filteredSearchDto);
        invalidPassengers.setPassengers(randInt(5, 100));
        FilteredSearchDto invalidDates = cloningMachine.deepClone(filteredSearchDto);
        invalidDates.setEndDate(LocalDateTime.now().minusMinutes(10).withSecond(0).withNano(0).toString());
        return new Object[][]{
                {invalidMember}, {invalidStartSiteId}, {invalidEndSiteId}, {invalidPassengers}, {invalidDates}
        };
    }

    @DataProvider
    private Object[][] missingMandatoryField() {
        Cloner cloningMachine = new Cloner();

        //create object with all fields
        FilteredSearchDto filteredSearchDto = DtoBuilders.buildFilteredSearch(rcimTestData.getMemberDto().getLogin(), rcimTestData.getAutomationSiteId());
        FilteredSearchDto noStartDate = cloningMachine.deepClone(filteredSearchDto);
        noStartDate.setStartDate(null);
        FilteredSearchDto noStartSiteId = cloningMachine.deepClone(filteredSearchDto);
        noStartSiteId.setStartSiteId(null);
        FilteredSearchDto noPassengers = cloningMachine.deepClone(filteredSearchDto);
        noPassengers.setPassengers(null);

        return new Object[][]{
                {noStartDate, "Start date is required"},
                {noStartSiteId, "Start location is required"},
                {noPassengers, "At least one passenger is required"}
        };
    }*/


}
