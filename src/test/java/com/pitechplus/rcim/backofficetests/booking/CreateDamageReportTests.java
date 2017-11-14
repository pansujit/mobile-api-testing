package com.pitechplus.rcim.backofficetests.booking;

import com.pitechplus.qautils.annotations.TestInfo;
import com.pitechplus.rcim.BackendAbstract;
import com.pitechplus.rcim.backoffice.constants.ErrorMessages;
import com.pitechplus.rcim.backoffice.data.enums.DamageReportType;
import com.pitechplus.rcim.backoffice.data.enums.ServiceCalled;
import com.pitechplus.rcim.backoffice.dto.backuser.Login;
import com.pitechplus.rcim.backoffice.dto.booking.BookingDto;
import com.pitechplus.rcim.backoffice.dto.booking.search.BookingResultDto;
import com.pitechplus.rcim.backoffice.dto.report.DamageReportCreateDto;
import com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders;
import com.pitechplus.rcim.backoffice.utils.builders.ValidationErrorsBuilder;
import com.pitechplus.rcim.backoffice.utils.custommatchers.ExceptionMatcher;
import com.pitechplus.rcim.backoffice.utils.exceptions.BackOfficeException;
import com.pitechplus.rcim.backoffice.utils.mappers.ExceptionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.pitechplus.qautils.randomgenerators.NumberGenerator.randInt;
import static com.pitechplus.rcim.backoffice.data.enums.ValidationError.*;
import static com.pitechplus.rcim.backoffice.utils.DataExtractors.extractXAuthTokenFromResponse;
import static com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders.buildCreateBooking;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by dgliga on 04.09.2017.
 */
public class CreateDamageReportTests extends BackendAbstract {

    private String bookingId1;
    private String memberToken;
    private UUID bookingId;

    @BeforeClass
    public void startBooking() {

    	bookingId = bookingService.createBooking1(rcimTestData.getMemberToken(),
                buildCreateBooking(rcimTestData.getMemberLoginEmail(), rcimTestData.getMemberVehicleId(),
                        rcimTestData.getMemberAutomationParking())).getBody().getId();
        //bookingId=UUID.fromString(bookingId1);
        memberToken=rcimTestData.getMemberToken();
        bookingService.startBooking(memberToken, bookingId);
        
    }

    @Test(description = "This test verifies that creating Start Damage report works accordingly.")
    @TestInfo(expectedResult = "Start damage report is created with correct details.")
    public void createStartDamageReportTest() {
        List<UUID> fileIds = new ArrayList<>();
        for (int i = 0; i < randInt(1, 4); i++) {
            fileIds.add(configsService.mobileCreateFile(rcimTestData.getMemberToken(), DtoBuilders.buildFile()).getBody().getId());
        }
        DamageReportCreateDto damageReportCreateDto = DtoBuilders.buildDamageReportCreate(DamageReportType.START_BOOKING, fileIds);
        Boolean serviceResponse = mobileService.createDamageReport(memberToken, bookingId, damageReportCreateDto).getBody();
        assertThat("Damage report not created!", serviceResponse, is(true));
    }

    @Test(description = "This test verifies that creating End Damage report works accordingly.")
    @TestInfo(expectedResult = "End damage report is created with correct details.")
    public void createEndDamageReportTest() {
        List<UUID> fileIds = new ArrayList<>();
        for (int i = 0; i < randInt(1, 4); i++) {
            fileIds.add(configsService.mobileCreateFile(rcimTestData.getMemberToken(), DtoBuilders.buildFile()).getBody().getId());
        }
        DamageReportCreateDto damageReportCreateDto = DtoBuilders.buildDamageReportCreate(DamageReportType.END_BOOKING, fileIds);
        Boolean serviceResponse = mobileService.createDamageReport(memberToken, bookingId, damageReportCreateDto).getBody();
        assertThat("Damage report not created!", serviceResponse, is(true));
    }

    /*@Test(description = "This test verifies that create damage report call with invalid X-AUTH-TOKEN triggers correct error.")
    @TestInfo(expectedResult = "Server responds with 401 Unauthorized with message: " + ErrorMessages.INVALID_AUTHENTICATION_TOKEN)
    public void invalidTokenTest() throws IOException {
        try {
            mobileService.createDamageReport(memberToken + "INVALID", bookingId, new DamageReportCreateDto());
            Assert.fail("Create Damage Report worked with invalid X-AUTH-TOKEN!");
        } catch (HttpStatusCodeException exception) {
            //verify that error received from server is the correct one
            assertThat("Server did not throw correct error!", ExceptionMapper.mapException(exception, BackOfficeException.class),
                    ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.UNAUTHORIZED, ErrorMessages.INVALID_AUTHENTICATION_TOKEN,
                            null, null));
        }
    }

    @Test(description = "This test verifies that create damage report call with invalid booking id triggers correct error.")
    @TestInfo(expectedResult = "Server responds with 404 Not Found with developer message: No booking found for id {invalidId}")
    public void invalidBookingIdTest() throws IOException {
        DamageReportCreateDto damageReportCreateDto = DtoBuilders.buildDamageReportCreate(DamageReportType.START_BOOKING,
                Collections.singletonList(configsService.createFile(rcimTestData.getSuperAdminToken(),
                        DtoBuilders.buildFile()).getBody().getId()));
        UUID invalidId = UUID.randomUUID();
        try {
            mobileService.createDamageReport(memberToken, invalidId, damageReportCreateDto);
            Assert.fail("Cancel Booking worked with invalid booking id!");
        } catch (HttpStatusCodeException exception) {
            //verify that error received from server is the correct one
            assertThat("Server did not throw correct error!", ExceptionMapper.mapException(exception, BackOfficeException.class),
                    ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(),
                            "No booking found for id " + invalidId, null));
        }
    }

    @Test(description = "This test verifies that create damage report call with missing mandatory fields triggers correct error.")
    @TestInfo(expectedResult = "Server responds with 400 Bad Request with validation errors for: type, internal and external " +
            "cleanliness")
    public void missingMandatoryFieldsTest() throws IOException {
        try {
            mobileService.createDamageReport(memberToken, bookingId, new DamageReportCreateDto());
            Assert.fail("Create Damage Report worked with missing mandatory fields!");
        } catch (HttpStatusCodeException exception) {
            //verify that error received from server is the correct one
            assertThat("Server did not throw correct error!", ExceptionMapper.mapException(exception, BackOfficeException.class),
                    ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            null, ValidationErrorsBuilder.buildValidationErrors(ServiceCalled.DAMAGE_CREATE, TYPE_MAY_NOT_BE_NULL,
                                    INTERNAL_CLEANLINESS_MAY_NOT_BE_NULL, EXTERNAL_CLEANLINESS_MAY_NOT_BE_NULL)));
        }
    }*/
    

    

    @AfterClass
    public void finishBooking() {
        bookingService.finishBooking(memberToken, bookingId,DtoBuilders.finishBooking());
    }
}
