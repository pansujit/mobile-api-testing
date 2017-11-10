package com.pitechplus.rcim.backofficetests.smartcard;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static com.pitechplus.rcim.backoffice.data.enums.ValidationError.PROTOCOL_MAY_NOT_BE_NULL;
import static com.pitechplus.rcim.backoffice.data.enums.ValidationError.CARDID_MAY_NOT_BE_EMPTY;

import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.pitechplus.qautils.annotations.TestInfo;
import com.pitechplus.rcim.BackendAbstract;
import com.pitechplus.rcim.backoffice.constants.ErrorMessages;
import com.pitechplus.rcim.backoffice.data.enums.ServiceCalled;
import com.pitechplus.rcim.backoffice.dto.smartcard.SmartCardCreateDto;
import com.pitechplus.rcim.backoffice.dto.smartcard.SmartCardDto;
import com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders;
import com.pitechplus.rcim.backoffice.utils.builders.ValidationErrorsBuilder;
import com.pitechplus.rcim.backoffice.utils.custommatchers.ExceptionMatcher;
import com.pitechplus.rcim.backoffice.utils.exceptions.BackOfficeException;
import com.pitechplus.rcim.backoffice.utils.mappers.ExceptionMapper;
import com.pitechplus.rcim.nissan.be.nissandto.members.MemberCreateDto;
import com.pitechplus.rcim.nissan.be.nissandto.members.MemberDto;
import com.pitechplus.rcim.nissan.be.nissanutils.nissanbuilders.NissanDtoBuilders;
import com.rits.cloning.Cloner;

public class CreateSmartCardTest extends BackendAbstract {
	private String login;
	private String superCompanyId;
	private final String expiredAuthToken = "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1MDgxMzg4OTEsImV4cCI6MTUw"
			+ "ODIyNTI5MSwibG9naW4iOiJzdWppdC5wYW5kZXlAZ2xpZGVtb2JpbGl0eS5jb20iLCJyb2xlIjoiUk9MRV9TV"
			+ "VBFUl9BRE1JTiIsInN1c3BlbmRlZCI6ZmFsc2UsInByb2ZpbGVJZCI6IjY2NWQ4Mzk5LWNlYjItNDY1Ny04NT"
			+ "VhLTE2ZDhiZWM2MTIwZCIsImFwcE9yaWdpbiI6ImdsaWRlX2JvIn0.ATZ9Qku-azUEMzn3ofx6Qmk1OBcYqWk"
			+ "Zlc-OUw1ih4ysQD8iuBXHG0K2Zran0pSDhgXZOVJkVJxQWotfUw_9vw";
	private SmartCardDto smartcardDtoWithMember;
	private com.pitechplus.rcim.backoffice.dto.smartcard.MemberDto user;

	@BeforeClass
	public void creatememberForSmartCard() {
		MemberCreateDto memberCreateDto = NissanDtoBuilders.buildMemberCreateDto(
				rcimTestData.getAdminSuperCompanyId().toString(), (nissanUtils.createArrayOfValidFiles(1)).get(0));
		MemberDto responseMemberDto = nissanBeServices.createMember(rcimTestData.getSuperAdminToken(), memberCreateDto)
				.getBody();
		login = responseMemberDto.getLogin();
		superCompanyId = responseMemberDto.getCompany().getId().toString();

	}

	@Test(priority = 8, description = "This test method verifies creation of a smartcard")
	@TestInfo(expectedResult = "A smartcard will be created with given information")
	public void addSmartCardTest() {
		SmartCardCreateDto smartCardCreateDto = DtoBuilders.buildSmartCardCreate(superCompanyId, login);
		smartcardDtoWithMember = smartCardService
				.createANewSmartCard(rcimTestData.getSuperAdminToken(), smartCardCreateDto).getBody();
		user = smartcardDtoWithMember.getUser();
		Cloner cloner = new Cloner();
		SmartCardDto x = cloner.deepClone(smartcardDtoWithMember);
		assertThat("The build created smartcard has different response than what we created !", smartcardDtoWithMember,
				is((DtoBuilders.buildExpectedCreateSmartDto(x, superCompanyId, login))));
	}

	@Test(priority = 9, description = "This test method verifies creation of a smartcard without login and company null")
	@TestInfo(expectedResult = "A unlink smartcard is created with the given information")
	public void addBareSmartCardTest() {
		SmartCardCreateDto smartCardCreateDto = DtoBuilders.buildSmartCardCreate(null, null);
		SmartCardDto smartcardDto = smartCardService
				.createANewSmartCard(rcimTestData.getSuperAdminToken(), smartCardCreateDto).getBody();
		assertThat(smartcardDto.getCardId(), is(notNullValue()));
		assertThat(smartcardDto.getCompany(), is(nullValue()));
		assertThat(smartcardDto.getUser(), is(nullValue()));
	}

	@Test(priority = 10, description = "This test method verifies unlinking of the smartcard with user")
	@TestInfo(expectedResult = "The given smartcard should be unlink with given user")
	public void unlinkSmartCardTest() {
		SmartCardDto unlinkSmartcard = smartCardService
				.unlinkSmartCardFromUser(rcimTestData.getSuperAdminToken(), smartcardDtoWithMember.getId()).getBody();
		smartcardDtoWithMember.setUser(null);
		assertThat("The given smartcard is not unlink with user", unlinkSmartcard, is(smartcardDtoWithMember));
	}

	@Test(priority = 11, dependsOnMethods = { "unlinkSmartCardTest",
			"addSmartCardTest" }, description = "This test method verifies unlinking of the smartcard with user")
	@TestInfo(expectedResult = "The given smartcard should be link with given user")
	public void linkSmartCardTest() {
		SmartCardDto linkSmartcard = smartCardService
				.linkSmartCardToUser(rcimTestData.getSuperAdminToken(), smartcardDtoWithMember.getId(), login)
				.getBody();
		smartcardDtoWithMember.setUser(user);
		assertThat("The smartcard is may not linked with given login", linkSmartcard, is(smartcardDtoWithMember));
	}

	@Test(priority = 1, description = "This test method verifies invalid wuthentication Response")
	@TestInfo(expectedResult = "An exception should be thrown correctly for invalid Token")
	public void invalidAuthTokenSmartCardTest() throws IOException {
		try {
			SmartCardCreateDto smartCardCreateDto = DtoBuilders.buildSmartCardCreate(superCompanyId, login);
			smartCardService.createANewSmartCard(rcimTestData.getSuperAdminToken() + "Invalid", smartCardCreateDto)
					.getBody();
			Assert.fail("The invalid X-auth-token works on creating smartcard");

		} catch (HttpStatusCodeException exception) {
			// verify that error received from server is the correct one
			assertThat("Server did not throw correct error!",
					ExceptionMapper.mapException(exception, BackOfficeException.class),
					ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.UNAUTHORIZED,
							ErrorMessages.INVALID_AUTHENTICATION_TOKEN, null, null));
		}

	}

	@Test(priority = 2, description = "This test method verifies invalid Company Response for  wrong company UUID")
	@TestInfo(expectedResult = "An exception should be thrown correctly for Wrong company Id")
	public void wrongCompanyIdSmartCardTest() throws IOException {
		try {
			String data = UUID.randomUUID().toString();
			SmartCardCreateDto smartCardCreateDto = DtoBuilders.buildSmartCardCreate(data, login);
			smartCardService.createANewSmartCard(rcimTestData.getSuperAdminToken(), smartCardCreateDto).getBody();
			Assert.fail("The creation of smartcard works with wrong UUID for the company");
		} catch (HttpStatusCodeException exception) {
			// verify that error received from server is the correct one
			assertThat("Server did not throw correct error!",
					ExceptionMapper.mapException(exception, BackOfficeException.class),
					ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.BAD_REQUEST,
							HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null));

		}

	}

	@Test(priority = 3, description = "This test method verifies Unprocessable entity response When super company Id is different the users")
	@TestInfo(expectedResult = "An exception should be thrown correctly for mismatch between super company and user")
	public void anotherSuperCompanyIDSmartCardTest() throws IOException {
		try {
			System.out.println("tttest" + rcimTestData.getBookingSuperCompanyId());
			SmartCardCreateDto smartCardCreateDto = DtoBuilders
					.buildSmartCardCreate(rcimTestData.getBookingSuperCompanyId(), login);
			smartCardService.createANewSmartCard(rcimTestData.getSuperAdminToken(), smartCardCreateDto).getBody();
			Assert.fail("Creation of the smartcard with mismatch company and user");
		} catch (HttpStatusCodeException exception) {
			// verify that error received from server is the correct one
			assertThat("Server did not throw correct error!",
					ExceptionMapper.mapException(exception, BackOfficeException.class),
					ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.UNPROCESSABLE_ENTITY,
							HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), null, null));

		}

	}

	@Test(priority = 4, description = "This test method verifies invalid Company Response for  wrong email format")
	@TestInfo(expectedResult = "An exception should be thrown correctly for Wrong email format")
	public void wrongEmailLoginIdSmartCardTest() throws IOException {
		try {
			SmartCardCreateDto smartCardCreateDto = DtoBuilders.buildSmartCardCreate(superCompanyId, "TestMe");
			smartCardService.createANewSmartCard(rcimTestData.getSuperAdminToken(), smartCardCreateDto).getBody();
			Assert.fail("The creation of smartcard works with wrong UUID for the company");
		} catch (HttpStatusCodeException exception) {
			// verify that error received from server is the correct one
			assertThat("Server did not throw correct error!",
					ExceptionMapper.mapException(exception, BackOfficeException.class),
					ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.BAD_REQUEST,
							HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null));

		}

	}

	@Test(priority = 5, description = "This test method verifies invalid Company Response for  non exist member email")
	@TestInfo(expectedResult = "An exception should be thrown correctly for non exist email")
	public void wrongLoginIdSmartCardTest() throws IOException {
		try {
			SmartCardCreateDto smartCardCreateDto = DtoBuilders.buildSmartCardCreate(superCompanyId,
					"xyz@devilseyes.com");
			smartCardService.createANewSmartCard(rcimTestData.getSuperAdminToken(), smartCardCreateDto).getBody();
			Assert.fail("The creation of smartcard works with wrong UUID for the company");
		} catch (HttpStatusCodeException exception) {
			// verify that error received from server is the correct one
			assertThat("Server did not throw correct error!",
					ExceptionMapper.mapException(exception, BackOfficeException.class),
					ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.BAD_REQUEST,
							HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null));

		}

	}

	@Test(priority = 6, description = "This test method verifies that expired authetication test in smartcard")
	@TestInfo(expectedResult = "An exception should be thrown correctly for expired authentication token")
	public void ExpiredAuthTokenSmartCardTest() throws IOException {
		try {
			SmartCardCreateDto smartCardCreateDto = DtoBuilders.buildSmartCardCreate(superCompanyId, login);
			smartCardService.createANewSmartCard(expiredAuthToken, smartCardCreateDto).getBody();
			Assert.fail("The creation of smartcard works with wrong UUID for the company");
		} catch (HttpStatusCodeException exception) {
			// verify that error received from server is the correct one
			assertThat("Server did not throw correct error!",
					ExceptionMapper.mapException(exception, BackOfficeException.class),
					ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.UNAUTHORIZED,
							ErrorMessages.EXPIRED_AUTHENTICATION_TOKEN, null, null));

		}

	}

	@Test(priority = 7, dataProvider = "missingFields", description = "This test method verifies creation of a smartcard "
			+ "Api error when manadatory field is null")
	@TestInfo(expectedResult = "A error should be thrown when missing mandatory field is missing")
	public void missingmandatoryFieldsSmartCardTest(String protocol, String cardId, Set<String> validationError)
			throws IOException {
		try {
			SmartCardCreateDto smartCardCreateDto = DtoBuilders.buildSmartCardCreateMissingParameter(superCompanyId,
					login, protocol, cardId);
			smartCardService.createANewSmartCard(rcimTestData.getSuperAdminToken(), smartCardCreateDto).getBody();
		} catch (HttpStatusCodeException exception) {
			// verify that error received from server is the correct one
			assertThat("Server did not throw correct error!",
					ExceptionMapper.mapException(exception, BackOfficeException.class),
					ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.BAD_REQUEST,
							HttpStatus.BAD_REQUEST.getReasonPhrase(), null, validationError));
		}

	}

	@DataProvider
	public static Object[][] missingFields() {
		return new Object[][] {

				{ null, "abc",
						ValidationErrorsBuilder.buildValidationErrors(ServiceCalled.SMART_CARD_CREATE_DTO,
								PROTOCOL_MAY_NOT_BE_NULL) },
				{ "abc", null, ValidationErrorsBuilder.buildValidationErrors(ServiceCalled.SMART_CARD_CREATE_DTO,
						CARDID_MAY_NOT_BE_EMPTY) } };

	}

}
