package com.pitechplus.rcim.backofficetests.voucher;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.pitechplus.qautils.restutils.ExceptionMapper;
import com.pitechplus.rcim.BackendAbstract;
import com.pitechplus.rcim.backoffice.constants.ErrorMessages;
import com.pitechplus.rcim.backoffice.data.enums.ServiceCalled;
import com.pitechplus.rcim.backoffice.data.enums.ValidationError;
import com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders;
import com.pitechplus.rcim.backoffice.utils.builders.ValidationErrorsBuilder;
import com.pitechplus.rcim.backoffice.utils.custommatchers.ExceptionMatcher;
import com.pitechplus.rcim.backoffice.utils.exceptions.BackOfficeException;
import com.pitectplus.rcim.backoffice.dto.voucher.VoucherGroupEditDto;
import com.rits.cloning.Cloner;

public class VoucherNegativeTest extends BackendAbstract {
	
	private VoucherGroupEditDto voucherGroupEditDto;
	
	@BeforeClass
	public void beforeClassCreateVoucher() throws InterruptedException {
		
		voucherGroupEditDto= DtoBuilders.createVoucherGroupDto(rcimTestData.getAdminCompanyId().toString());
	}
	
	@Test(dataProvider="createVoucherMissingMandatoryField", description="This negative test will test the missing "
			+ "manadatory field error")
	public void createVoucherMissingMandatoryFieldTest(VoucherGroupEditDto testData,
			Set<String> validationErrors) throws IOException {
		try {
			voucherService.createVoucherGroup(rcimTestData.getSuperAdminToken(), testData);
			Assert.fail("The voucher group creation works without mandatory field");
		}catch(HttpStatusCodeException exception) {
			assertThat("",ExceptionMapper.mapHttpStatusCodeException(exception, BackOfficeException.class),
					ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.BAD_REQUEST, 
							HttpStatus.BAD_REQUEST.getReasonPhrase(), null, validationErrors));	
		}	
	}
	
	@Test(description="This negative test will test the invalid authentication error while creating voucher group")
	public void createVoucherInvalidAuthToken() throws IOException {
		try {
			voucherService.createVoucherGroup(rcimTestData.getSuperAdminToken()+"eiruey", 
					voucherGroupEditDto);
			Assert.fail("The voucher group creation works without mandatory field");
		}catch(HttpStatusCodeException exception) {
			assertThat("",ExceptionMapper.mapHttpStatusCodeException(exception, BackOfficeException.class),
					ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.UNAUTHORIZED, 
							ErrorMessages.INVALID_AUTHENTICATION_TOKEN, null, null));	
		}
		
	}
	//this has is working on dev
	/*@Test
	public void createVoucherUnknownSuperCompanyId() throws IOException {
		Cloner cloner= new Cloner();
		VoucherGroupEditDto y=cloner.deepClone(voucherGroupEditDto);
		y.setSuperCompanyId("d376d9e7-c9e6-4876-a2f4-111111111111");
		try {
			voucherService.createVoucherGroup(rcimTestData.getSuperAdminToken(), y
					);
			Assert.fail("The voucher group creation works without mandatory field");
		}catch(HttpStatusCodeException exception) {
			assertThat("",ExceptionMapper.mapHttpStatusCodeException(exception, BackOfficeException.class),
					ExceptionMatcher.isExpectedBackOfficeException(HttpStatus.UNPROCESSABLE_ENTITY, 
							HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), null, null));	
		}
		
	}*/

	@DataProvider
	public Object[][] createVoucherMissingMandatoryField() {
		Cloner cloner= new Cloner();
		
		VoucherGroupEditDto companyIdIsNull=cloner.deepClone(voucherGroupEditDto);
		companyIdIsNull.setSuperCompanyId(null);

		VoucherGroupEditDto discountIsNegative=cloner.deepClone(voucherGroupEditDto);
		discountIsNegative.setDiscount(-10);
		
		VoucherGroupEditDto nameIsNull=cloner.deepClone(voucherGroupEditDto);
		nameIsNull.setName(null);
		
		VoucherGroupEditDto typeIsNull=cloner.deepClone(voucherGroupEditDto);
		typeIsNull.setType(null);
		
		VoucherGroupEditDto AllMissingField=cloner.deepClone(voucherGroupEditDto);
		AllMissingField.setType(null);
		AllMissingField.setSuperCompanyId(null);
		AllMissingField.setName(null);
		AllMissingField.setDiscount(-10);
		AllMissingField.getRules()[0].setRule(null);
		
		VoucherGroupEditDto ruleIsNull=cloner.deepClone(voucherGroupEditDto);
		ruleIsNull.getRules()[0].setRule(null);
		
		
		VoucherGroupEditDto unknownSupercompanyId=cloner.deepClone(voucherGroupEditDto);
		unknownSupercompanyId.setSuperCompanyId("d376d9e7-c9e6-4876-a2f4-111111111111");
		
		
		return new Object[][] {
			{companyIdIsNull,ValidationErrorsBuilder.buildValidationErrors(ServiceCalled.VOUCHER_GROUP_EDIT_DTO, ValidationError.SUPERCOMPANYID_MAY_NOT_BE_NULL)},
			{discountIsNegative,
				ValidationErrorsBuilder.buildValidationErrors(
						ServiceCalled.VOUCHER_GROUP_EDIT_DTO, ValidationError.DISCOUNT_IS_INVALID)},
			{nameIsNull,
							ValidationErrorsBuilder.buildValidationErrors(
									ServiceCalled.VOUCHER_GROUP_EDIT_DTO, ValidationError.NAME_MAY_NOT_BE_EMPTY)},
			{typeIsNull,ValidationErrorsBuilder.buildValidationErrors(
					ServiceCalled.VOUCHER_GROUP_EDIT_DTO, ValidationError.TYPE_MAY_NOT_BE_NULL)},
			{AllMissingField,ValidationErrorsBuilder.buildValidationErrors(
					ServiceCalled.VOUCHER_GROUP_EDIT_DTO, ValidationError.SUPERCOMPANYID_MAY_NOT_BE_NULL,
					ValidationError.DISCOUNT_IS_INVALID,ValidationError.TYPE_MAY_NOT_BE_NULL,
					ValidationError.NAME_MAY_NOT_BE_EMPTY)},
			{ruleIsNull,ValidationErrorsBuilder.buildValidationErrors(
					ServiceCalled.VOUCHER_GROUP_EDIT_DTO, ValidationError.RULE_MAY_NOT_NULL)},
			


		};
	}

}
