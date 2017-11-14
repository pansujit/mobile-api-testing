package com.pitechplus.rcim.backofficetests.members;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.pitechplus.rcim.BackendAbstract;
import com.pitechplus.rcim.backoffice.dto.member.CustomMemberDto;
import com.pitechplus.rcim.backoffice.dto.member.MemberCustomValues;
import com.pitechplus.rcim.backoffice.dto.member.MemberDto;
import com.pitechplus.rcim.backoffice.dto.member.MemberPaymentURLResponseDto;
import com.pitechplus.rcim.backoffice.dto.member.MemberSettingsDto;
import com.pitechplus.rcim.backoffice.dto.member.PaymentCardDto;
import com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders;
import com.pitechplus.rcim.backoffice.utils.mappers.Mapper;

public class MemberTest extends BackendAbstract {
	
	private String fileId;
	private String textId;
	private List<MemberCustomValues> customData= new ArrayList<MemberCustomValues>();
	private CustomMemberDto customMemberDto;
	private MemberSettingsDto memberSettingsDto;
	String memberId;
	@BeforeClass
	public void CustomFileData() {
		fileId="f477614f-cf47-4557-9147-6d157592c767";
		textId="466c430e-a68b-4474-af34-0a7725f70792";	
		//List<UUID> y=fileService.createArrayOfValidFiles(2);
		//configsService.createFile(rcimTestData.getMemberToken(), DtoBuilders.buildFile()).getBody().getId().toString()
		customData.add(MemberCustomValues.builder().companyCustomFieldId(fileId).value(configsService.mobileCreateFile(rcimTestData.getMemberToken(), DtoBuilders.buildFile()).getBody().getId().toString()
).build());
		customData.add(MemberCustomValues.builder().companyCustomFieldId(textId).value("hello").build());
		customMemberDto=DtoBuilders.customBuildMemberCreateDto(rcimTestData.getCustomSuperCompanyId(),
				configsService.mobileCreateFile(rcimTestData.getMemberToken(), 
						DtoBuilders.buildFile()).getBody().getId(),customData);
	 
		 
		memberSettingsDto=MemberSettingsDto.builder()
		.sendMailForCallCenterChange(true)
		.sendMailForPersonalChange(true)
		.sendNotificationsByEmail(true)
		.sendNotificationsBySms(true)
		.smsBeforeArrivalTime(0)
		.smsBeforeDepartureTime(0)
		.build();
		
	}
	
	@Test(description="This test will create a member with custom field")
	public void createMemberWithCustomFieldTest() {
		
		MemberDto responseMemberDto=memberService.createMemberWithCustomField(rcimTestData.getMemberToken(), customMemberDto).getBody();
		memberId=responseMemberDto.getId().toString();
		MemberDto expectedMemberDto=Mapper.mapMemberCreateDtoToMemberDto(customMemberDto, companyService
                .getSuperCompany(rcimTestData.getSuperAdminToken(), 
                		UUID.fromString(rcimTestData.getCustomSuperCompanyId())).getBody());
		expectedMemberDto.getPhoneNumber().setPhoneNumberName(null);
		expectedMemberDto.getCompany().setContract(null);
		 assertThat("Information given on response to add member does not reflect request !",
	                responseMemberDto, is(expectedMemberDto));	
	}
	
	@Test(description="This test will get the payment url of the given member")
	public void getMemberPaymentUrlTest() {
		String memberId=rcimTestData.getMemberId();;
		MemberPaymentURLResponseDto urlPayment=memberService.getPaymentURL(rcimTestData.getMemberToken(), memberId).getBody();
		assertThat("The url of the url payment of the member is null",urlPayment.getUrl(),is(notNullValue()));
		
	}
	
	
	@Test(description="This test will get the payment url of the given member")
	public void getSingleMemberInformationTest() {
		String memberId=rcimTestData.getMemberId();;
		MemberDto getmember=memberService.getSingleMember(rcimTestData.getMemberToken(), memberId).getBody();
		 assertThat("Information given on response doesnot contains the login information",
				 getmember.getLogin(), is(rcimTestData.getMemberLoginEmail()));		
	}
	
	@Test(description="This test will get the payment url of the given member")
	public void putMemberSettingsTest() {
		String memberId=rcimTestData.getMemberId();;
		MemberSettingsDto putSettings=memberService.putMemberSetting(rcimTestData.getMemberToken(), memberId,
				memberSettingsDto).getBody();
		 assertThat("Member setting has not been updated",
				 putSettings, is(memberSettingsDto));		
	}
	
	@Test(dependsOnMethods="putMemberSettingsTest",description="This test will get the payment url of the given member")
	public void getMemberSettingsTest() {
		String memberId=rcimTestData.getMemberId();;
		MemberSettingsDto getSettings=memberService.getMemberSetting(rcimTestData.getMemberToken(), memberId).getBody();
		 assertThat("The member setttings information desnot contains given information",
				 getSettings, is(memberSettingsDto));		
	}
	
	@Test(description="This test will get the payment url of the given member")
	public void getMemberPaymentCardInfo() {
		String memberId=rcimTestData.getMemberId();
		PaymentCardDto getPayment=memberService.getmemberCardInfo(rcimTestData.getMemberToken(), memberId).getBody();
		 assertThat("The card info doesnot have the brand field",
				 getPayment.getBrand(), is(notNullValue()));	
		 assertThat("The card info doesnot have the Expiry  month field",
				 getPayment.getExpiryMonth(), is(notNullValue()));	
		 assertThat("The card info doesnot have the Expiry year field",
				 getPayment.getExpiryYear(), is(notNullValue()));	
		 assertThat("The card info doesnot have the card number field",
				 getPayment.getNumber(), is(notNullValue()));	
	}	

}
