package com.pitechplus.rcim.backoffice.utils.builders;

import com.pitechplus.qautils.randomgenerators.NumberGenerator;
import com.pitechplus.qautils.randomgenerators.PersonalInfoGenerator;
import com.pitechplus.rcim.backoffice.data.enums.*;

import com.pitechplus.rcim.backoffice.dto.booking.BookingCreateDto;
import com.pitechplus.rcim.backoffice.dto.booking.BookingCustomValues;
import com.pitechplus.rcim.backoffice.dto.booking.BookingDto;
import com.pitechplus.rcim.backoffice.dto.booking.FilteredSearchDto;
import com.pitechplus.rcim.backoffice.dto.booking.feedback.FeedbackDto;
import com.pitechplus.rcim.backoffice.dto.booking.feedback.FeedbackReportDto;
import com.pitechplus.rcim.backoffice.dto.booking.filteredsearch.CarSharingInfoDto;
import com.pitechplus.rcim.backoffice.dto.booking.filteredsearch.DateLocationDto;
import com.pitechplus.rcim.backoffice.dto.booking.filteredsearch.SearchBookingResultDto;
import com.pitechplus.rcim.backoffice.dto.booking.finish.BluetoothInfoDto;
import com.pitechplus.rcim.backoffice.dto.booking.finish.FinishBookingInfoDto;
import com.pitechplus.rcim.backoffice.dto.booking.search.BookingQueryDto;
import com.pitechplus.rcim.backoffice.dto.booking.search.BookingSorterDto;
import com.pitechplus.rcim.backoffice.dto.booking.search.QueryAddress;
import com.pitechplus.rcim.backoffice.dto.booking.search.SearchBookingDto;
import com.pitechplus.rcim.backoffice.dto.booking.search.SearchDateLocationDto;
import com.pitechplus.rcim.backoffice.dto.common.Address;
import com.pitechplus.rcim.backoffice.dto.common.Coordinates;
import com.pitechplus.rcim.backoffice.dto.common.FileDto;
import com.pitechplus.rcim.backoffice.dto.common.PhoneNumber;
import com.pitechplus.rcim.backoffice.dto.company.*;
import com.pitechplus.rcim.backoffice.dto.invoice.MemberInvoicePagedSearchDto;
import com.pitechplus.rcim.backoffice.dto.invoice.MemberInvoiceQueryDto;
import com.pitechplus.rcim.backoffice.dto.invoice.MemberInvoiceSortDto;
import com.pitechplus.rcim.backoffice.dto.member.CustomMemberDto;
import com.pitechplus.rcim.backoffice.dto.member.DrivingLicenceDto;
import com.pitechplus.rcim.backoffice.dto.member.MemberCustomValues;
import com.pitechplus.rcim.backoffice.dto.pagination.Page;
import com.pitechplus.rcim.backoffice.dto.report.*;

import com.pitechplus.rcim.backoffice.dto.vehicle.VehicleDto;

import org.apache.commons.lang.RandomStringUtils;


import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.pitechplus.qautils.randomgenerators.NumberGenerator.randInt;


/**
 * Created by dgliga on 15.02.2017.
 */
public class DtoBuilders {

	/**
	 * This method builds random France phone number
	 *
	 * @return Valid Fr Phone number
	 */
	public static PhoneNumber buildRandomFrPhoneNumber() {
		return PhoneNumber.builder().countryCode("+33").nationalNumber("590" + RandomStringUtils.random(6, false, true))
				.phoneNumberName(PersonalInfoGenerator.generateName(5)).build();
	}

	/**
	 * This method builds a random Address
	 *
	 * @return Address object
	 */
	public static Address buildRandomAddress() {
		String randomCountry = Locale.getISOCountries()[randInt(0, Locale.getISOCountries().length - 1)];
		String postalCode = String.valueOf(randInt(10000, 99999));
		String streetName = PersonalInfoGenerator.generateOrcName() + " Street";
		String streetNumber = String.valueOf(randInt(1, 500));
		String city = PersonalInfoGenerator.generateOrcName() + " City";
		return Address.builder().city(city).country(randomCountry).postalCode(postalCode).streetName(streetName)
				.streetNumber(streetNumber).formattedAddress(streetNumber + " " + streetName + ", " + postalCode + " "
						+ city + ", " + new Locale("", randomCountry).getDisplayCountry())
				.build();
	}

	private static Coordinates buildRandomCoordinates() {
		return Coordinates.builder().latitude(NumberGenerator.randDouble(0, 20))
				.longitude(NumberGenerator.randDouble(0, 20)).build();
	}

	public static FileDto buildFile() {
		return FileDto.builder().name(PersonalInfoGenerator.generateOrcName() + ".jpeg").mimeType("image/jpeg")
				.content("ZGFkYQ==").build();
	}



	public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
		SecureRandom random = new SecureRandom();
		int x = random.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}


	public static SearchBookingDto buildFilteredSearch(ParkingDto memberAutomationParking) {
		return SearchBookingDto.builder().passengers(NumberGenerator.randInt(1, 4))
				.start(SearchDateLocationDto.builder()
						.date(LocalDateTime.now().plusMinutes(10).withSecond(0).withNano(0).toString())
						.address(QueryAddress.builder()
								.formattedAddress(memberAutomationParking.getSite().getAddress().getFormattedAddress())
								.coordinates(memberAutomationParking.getCoordinates())
								.build())
						.build())
				.end(SearchDateLocationDto.builder()
						.date(LocalDateTime.now().plusMinutes(70).withSecond(0).withNano(0).toString())
						.address(QueryAddress.builder()
								.formattedAddress(memberAutomationParking.getSite().getAddress().getFormattedAddress())
								.coordinates(memberAutomationParking.getCoordinates())
								.build())
						.build())
				.startParkingId(memberAutomationParking.getId().toString()).build();

	}




	public static SearchBookingResultDto buildExpectedSearchBookingResult(FilteredSearchDto filteredSearchDto,
			ParkingDto parkingDto, VehicleDto vehicleDto, UsageType usageType) {
		return SearchBookingResultDto.builder().type(BookingType.CAR_SHARING).start(DateLocationDto.builder()
				.date(filteredSearchDto.getStartDate()
						+ ZoneId.of(parkingDto.getSite().getZoneId()).getRules().getOffset(Instant.now()).getId())
				.address(parkingDto.getSite().getAddress()).coordinates(parkingDto.getCoordinates()).parking(parkingDto)
				.build())
				.end(DateLocationDto.builder()
						.date(filteredSearchDto.getEndDate()
								+ ZoneId.of(parkingDto.getSite().getZoneId()).getRules().getOffset(Instant.now())
								.getId())
						.address(parkingDto.getSite().getAddress()).coordinates(parkingDto.getCoordinates())
						.parking(parkingDto).build())
				.vehicle(vehicleDto).reservedSeats(filteredSearchDto.getPassengers())
				.carSharingInfo(CarSharingInfoDto.builder().usageType(usageType).build()).build();
	}

	public static SearchBookingResultDto buildExpectedSearchBookingResult1(FilteredSearchDto filteredSearchDto,
			ParkingDto parkingDto, VehicleDto vehicleDto, UsageType usageType) {
		return SearchBookingResultDto.builder().type(BookingType.CAR_SHARING).start(DateLocationDto.builder()
				.date(filteredSearchDto.getStartDate() + ":00"
						+ ZoneId.of(parkingDto.getSite().getZoneId()).getRules().getOffset(Instant.now()).getId())
				.address(parkingDto.getSite().getAddress()).coordinates(parkingDto.getCoordinates()).parking(parkingDto)
				.build())
				.end(DateLocationDto.builder()
						.date(filteredSearchDto.getEndDate() + ":00"
								+ ZoneId.of(parkingDto.getSite().getZoneId()).getRules().getOffset(Instant.now())
								.getId())
						.address(parkingDto.getSite().getAddress()).coordinates(parkingDto.getCoordinates())
						.parking(parkingDto).build())
				.vehicle(vehicleDto).reservedSeats(filteredSearchDto.getPassengers())
				.carSharingInfo(CarSharingInfoDto.builder().usageType(usageType).build()).build();
	}



	public static BookingCreateDto buildCreateBooking(String memberLogin, String vehicleId,
			ParkingDto parkingDto) {
		return BookingCreateDto.builder()
				.carSharingInfo(CarSharingInfoDto.builder().usageType(UsageType.BUSINESS).freeOfCharges(false).delayed(false).build())
				.memberLogin(memberLogin).reservedSeats(randInt(1, 4)).type(BookingType.CAR_SHARING)
				.vehicle(VehicleDto.builder().id(UUID.fromString(vehicleId)).build())
				.start(DateLocationDto.builder()
						.date(LocalDateTime.now().plusMinutes(25).withNano(0).toString() + ZoneId
								.of(parkingDto.getSite().getZoneId()).getRules().getOffset(Instant.now()).getId())
						.address(parkingDto.getSite().getAddress()).coordinates(parkingDto.getCoordinates())
						.parking(parkingDto).build())
				.end(DateLocationDto.builder()
						.date(LocalDateTime.now().plusHours(1).plusMinutes(25).withNano(0) + ZoneId
								.of(parkingDto.getSite().getZoneId()).getRules().getOffset(Instant.now()).getId())
						.address(parkingDto.getSite().getAddress()).coordinates(parkingDto.getCoordinates())
						.parking(parkingDto).build())
				.build();
	}

	public static BookingCreateDto buildCreateBooking(String memberLogin, VehicleDto vehicleDto,
			ParkingDto parkingDto) {
		return BookingCreateDto.builder()
				.carSharingInfo(CarSharingInfoDto.builder().usageType(UsageType.BUSINESS).freeOfCharges(false).delayed(false).build())
				.memberLogin(memberLogin).reservedSeats(randInt(1, 4)).type(BookingType.CAR_SHARING)
				.vehicle(VehicleDto.builder().id(vehicleDto.getId()).build())
				.start(DateLocationDto.builder()
						.date(LocalDateTime.now().plusMinutes(10).withSecond(0).withNano(0).toString() + ZoneId
								.of(parkingDto.getSite().getZoneId()).getRules().getOffset(Instant.now()).getId())
						.address(parkingDto.getSite().getAddress()).coordinates(parkingDto.getCoordinates())
						.parking(parkingDto).build())
				.end(DateLocationDto.builder()
						.date(LocalDateTime.now().minusHours(1).plusMinutes(10).plusHours(3).withSecond(0).withNano(0) + ZoneId
								.of(parkingDto.getSite().getZoneId()).getRules().getOffset(Instant.now()).getId())
						.address(parkingDto.getSite().getAddress()).coordinates(parkingDto.getCoordinates())
						.parking(parkingDto).build())
				.build();
	}

	public static BookingCreateDto buildCreateBooking1(String memberLogin, VehicleDto vehicleDto,
			ParkingDto parkingDto,BookingCustomValues bookingCustomValues) {
		return BookingCreateDto.builder()
				.carSharingInfo(CarSharingInfoDto.builder().usageType(UsageType.BUSINESS).freeOfCharges(false).delayed(false).build())
				.memberLogin(memberLogin).reservedSeats(randInt(1, 4)).type(BookingType.CAR_SHARING)
				.vehicle(VehicleDto.builder().id(vehicleDto.getId()).build())
				.start(DateLocationDto.builder()
						.date(LocalDateTime.now().plusMinutes(10).withNano(0).toString() + ZoneId
								.of(parkingDto.getSite().getZoneId()).getRules().getOffset(Instant.now()).getId())
						.address(parkingDto.getSite().getAddress()).coordinates(parkingDto.getCoordinates())
						.parking(parkingDto).build())
				.end(DateLocationDto.builder()
						.date(LocalDateTime.now().minusHours(1).plusMinutes(10).plusHours(3).withNano(0) + ZoneId
								.of(parkingDto.getSite().getZoneId()).getRules().getOffset(Instant.now()).getId())
						.address(parkingDto.getSite().getAddress()).coordinates(parkingDto.getCoordinates())
						.parking(parkingDto).build())
				.bookingCustomValues(Arrays.asList(bookingCustomValues))
				.build();
	}


	public static BookingDto buildExpectedBooking(BookingCreateDto bookingCreateDto, VehicleDto vehicleDto) {
		return BookingDto.builder().type(bookingCreateDto.getType()).start(bookingCreateDto.getStart())
				.end(bookingCreateDto.getEnd()).vehicle(vehicleDto).status(BookingStatusType.SCHEDULED)// when creating
				// booking
				// SCHEDULED is
				// default
				.state(BookingState.UPCOMING)// when creating booking UPCOMING is default
				.reservedSeats(bookingCreateDto.getReservedSeats()).paymentSettlingAllowed(false)
				.carSharingInfo(CarSharingInfoDto.builder().statusPayment(StatusPayment.NA)// When creating car sharing
						// booking status payment
						// N/A is default
						.delayed(false).failed(false).freeOfCharges(false).openToRideSharing(false)
						.usageType(bookingCreateDto.getCarSharingInfo().getUsageType())
						.cost(null)
						.build())
				.bookingCustomValues(bookingCreateDto.getBookingCustomValues())
				.build();
	}


	public static DamageReportCreateDto buildDamageReportCreate(DamageReportType damageReportType, List<UUID> fileIds) {
		Set<DamageDto> damageDtos = new HashSet<>();
		DamageReportCreateDto damageReportCreateDto = DamageReportCreateDto.builder().type(damageReportType)
				.externalCleanliness(randInt(1, 5)).internalCleanliness(randInt(1, 5))
				.cleanlinessComment(PersonalInfoGenerator.generateFullName() + " believes it is not acceptable.")
				.reports(damageDtos).build();

		for (UUID fileId : fileIds) {
			DamageDto damageDto = DamageDto.builder().area(randomEnum(DamageArea.class))
					.type(randomEnum(DamageType.class))
					.comment(PersonalInfoGenerator.generateFullName() + " omg omg omg!!!").fileId(fileId).build();
			damageReportCreateDto.getReports().add(damageDto);
		}
		damageReportCreateDto.setReports(damageDtos);
		return damageReportCreateDto;
	}



	public static CustomMemberDto customBuildMemberCreateDto(String superCompanyId,UUID fileId, List<MemberCustomValues> customData) {
		return CustomMemberDto.builder()
				.login("rcimtesting+" + randInt(0, 999999) + "_" + randInt(0, 999999) + "@gmail.com")
				.firstName(PersonalInfoGenerator.generateOrcName() + PersonalInfoGenerator.generateOrcName())
				.lastName(PersonalInfoGenerator.generateOrcName() + PersonalInfoGenerator.generateOrcName())
				.phoneNumber(DtoBuilders.buildRandomFrPhoneNumber())
				.address(DtoBuilders.buildRandomAddress())
				.birthDate(LocalDate.now().minusYears(19).toString())
				.password("1Aaaaaaa")
				.locale(Locale.FRANCE)
				.companyId(UUID.fromString(superCompanyId))
				.memberCustomValues(customData)
				.drivingLicence(buildDrivingLicenceDto(fileId))
				.vip(false)
				.build();
	}

	/**
	 * This method is used to create a randomDrivingLicence object with valid data
	 *
	 * @return drivingLicenceDto object with all fields having valid values
	 */
	public static DrivingLicenceDto buildDrivingLicenceDto(UUID fileId) {

		return DrivingLicenceDto.builder()
				.fileId(fileId)
				.licenceNumber(PersonalInfoGenerator.generateOrcName() + randInt(1, 9000) + PersonalInfoGenerator.generateName(5))
				.expirationDate(LocalDate.now().plusMonths(10).toString())
				.cityDeliverance(PersonalInfoGenerator.generateOrcName() + " City")
				.deliveranceDate(LocalDate.now().plusMonths(6).toString())
				.status(ReviewStatus.TO_REVIEW)
				.validated(false)
				.build();
	}


	public static FinishBookingInfoDto finishBooking() {
		return  	FinishBookingInfoDto.builder()
				.endBluetoothInfo(BluetoothInfoDto.builder()
						.engineOn(false)
						.lockedDoors(true)
						.lockedImmobilizer(true)
						.build())
				.startBluetoothInfo(BluetoothInfoDto.builder()
						.engineOn(true)
						.lockedDoors(false)
						.lockedImmobilizer(true)
						.build())
				.build();
	}

	public static FeedbackDto bookingFeedback(String fileId) {

		return  	FeedbackDto.builder()
				.externalCleanliness(1)
				.internalCleanliness(1)
				.comment("testme")
				.report(FeedbackReportDto.builder()
						.comment("hello")
						.fileId(fileId)
						.type(VehicleStatusType.MINOR_SCRAPES)
						.build())
				.build();

	}


	public static MemberInvoicePagedSearchDto invoiceSearchBuilder() {
		return  	MemberInvoicePagedSearchDto.builder()
				.page(Page.builder().number(1).size(50).build())
				.sort(MemberInvoiceSortDto.builder().direction(SortDirection.ASC).property(InvoiceProperty.CREATED_DATE).build())
				.query(MemberInvoiceQueryDto.builder()
						.build())
				.build();
	}
	public static BookingQueryDto MemberBookingSearchFilter(String memberId) {
		return BookingQueryDto.builder()
		.memberId(memberId)
		.page(Page.builder().number(1).size(200).build())
		.sort(BookingSorterDto.builder()
				.direction(SortDirection.DESC)
				.property(BookingProperty.START_DATE)
				.build())
		.build();
	}

}
