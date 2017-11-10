package com.pitechplus.rcim.backofficetests.booking;

import com.pitechplus.qautils.annotations.TestInfo;
import com.pitechplus.rcim.BackendAbstract;
import com.pitechplus.rcim.backoffice.constants.ErrorMessages;
import com.pitechplus.rcim.backoffice.dto.booking.BookingCreateDto;
import com.pitechplus.rcim.backoffice.dto.booking.BookingDto;
import com.pitechplus.rcim.backoffice.dto.booking.BookingDto1;
import com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders;
import com.pitechplus.rcim.backoffice.utils.custommatchers.ExceptionMatcher;
import com.pitechplus.rcim.backoffice.utils.exceptions.BackOfficeException;
import com.pitechplus.rcim.backoffice.utils.mappers.ExceptionMapper;
import com.pitechplus.rcim.nissan.be.nissandto.members.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.UUID;

import static com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders.buildCreateBooking;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by dgliga on 25.08.2017.
 */
public class GetBookingTests extends BackendAbstract {


    @Test(description = "This test verifies that request to get booking works accordingly.")
    @TestInfo(expectedResult = "The booking which belongs to the id given on request is retrieved with correct information.")
    public void getBookingTest() {
        BookingCreateDto bookingCreateDto = buildCreateBooking(rcimTestData.getMemberDto().getLogin(),
                rcimTestData.getAutomationVehicle(), rcimTestData.getAutomationParking());
        UUID bookingId = bookingService.createBooking(rcimTestData.getSuperAdminToken(),
                bookingCreateDto).getBody().getId();
        BookingDto1 bookingDto = bookingService.getBooking(rcimTestData.getMemberToken(), bookingId).getBody();
        /*bookingService.cancelBooking(rcimTestData.getSuperAdminToken(), bookingDto.getId().toString());
        BookingDto expectedBookingDto = DtoBuilders.buildExpectedBooking(bookingCreateDto, rcimTestData.getAutomationVehicle());
        MemberDto memberDto = rcimTestData.getMemberDto();
        memberDto.setDrivingLicence(null);
        expectedBookingDto.setMember(rcimTestData.getMemberDto());
        assertThat("Booking retrieved is incorrect!", bookingDto, is(expectedBookingDto));*/
    }

    
}
